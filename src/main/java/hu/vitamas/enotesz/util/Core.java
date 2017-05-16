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

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * System core class for accessing easier other utilities and important system methods.
 * 
 * @author vitozy
 *
 */
public class Core {
	static Logger logger = LoggerFactory.getLogger(Core.class);
	
	/**
	 * Returns with configuration by the given key.
	 * 
	 * @param key key of config
	 * @return value of config key or null
	 */
	public static String getConfigOf(String key){
		Properties prop = new Properties();
		InputStream input = null;
		String value = null;

		try {
			input = Core.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
			value = prop.getProperty(key);
		} catch (IOException ex) {
			logger.error("IO property getting", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Input close error - finally", e);
				}
			}
		}
		
		return value;
	}
	
	/**
	 * Opens a web site by URL address.
	 * 
	 * @param url website's URL to open
	 */
	public static void openWebsite(String url){
		if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e1) {
    			logger.error("Website " + url + " opening error", e1);
            }
        }
	}
	
	/**
	 * Returns with new ApiBuilder instance.
	 * 
	 * @return builder instance
	 */
	public static ApiBuilder createApi(){
		return new ApiBuilder();
	}

	/**
	 * Returns with the API key from property file.
	 * 
	 * @return api key
	 */
	public static String getApiKey() {
		return Core.getConfigOf("security.apiKey");
	}

	/**
	 * Returns with API base URL from property file.
	 * 
	 * @return api base url
	 */
	public static String getApiBaseUrl() {
		return Core.getConfigOf("security.apiLink");
	}
}
