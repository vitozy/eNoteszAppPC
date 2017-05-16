/*
 * Copyright (C) 2017 Vincze Tamas Zoltan (www.vitamas.hu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.vitamas.enotesz.util;
import org.hibernate.cfg.Configuration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author vitozy
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

	static {
		try {
			Properties prop = new Properties();
			InputStream input = null;

			String DB_USERNAME = "";
			String DB_PASSWORD = "";
			String DB_HOST = "";
			String DB_DATABASE = "";

			try {
				input = Auth.class.getClassLoader().getResourceAsStream("config.properties");
				prop.load(input);

				DB_USERNAME = prop.getProperty("database.username");
				DB_PASSWORD = prop.getProperty("database.password");
				DB_HOST = prop.getProperty("database.host");
				DB_DATABASE = prop.getProperty("database.dbname");

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
			enc.setPassword("C2sLUeHd8hm6quZAJp2wRq");
			enc.setAlgorithm("PBEWITHMD5ANDDES");
			//System.out.println(enc.encrypt(DB_PASSWORD));
			
			DB_PASSWORD = enc.decrypt(DB_PASSWORD);
			
			Configuration config = new Configuration();
			config.configure(); //default
			config.setProperty("hibernate.connection.username", DB_USERNAME );
			config.setProperty("hibernate.connection.password", DB_PASSWORD ); 
			config.setProperty("hibernate.connection.url", "jdbc:mysql://" + DB_HOST + ":3306/" + DB_DATABASE + "?characterEncoding=UTF-8"); 
			sessionFactory = config.buildSessionFactory();
		} catch (Throwable ex) {
			logger.error("Hibernate config error", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Returns with session factory.
	 * 
	 * @return session factory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
