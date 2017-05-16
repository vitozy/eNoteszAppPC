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

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.vitamas.enotesz.dao.UsersDao;
import hu.vitamas.enotesz.json.AuthResponse;
import hu.vitamas.enotesz.model.Users;

/**
 * Authentication/session handler class.
 *
 * @author vitozy
 */
public class Auth {

	static Logger logger = LoggerFactory.getLogger(Auth.class);

	private static Integer userID = 0;
	private static boolean loggedIn = false;

	/**
	 * Returns with the logged in user's id.
	 * 
	 * @return the logged in user's id
	 */
	public static Integer getUserID() {
		return userID;
	}
	
	/**
	 * Returns with the logged in user instance.
	 * 
	 * @return logged in user instance
	 */
	public static Users getUser(){
		UsersDao udao = new UsersDao();
		return udao.findById(Auth.getUserID(), Users.class);
	}

	/**
	 * Sets the logged in user's id.
	 * 
	 * @param userID user id of current session
	 */
	public static void setUserID(Integer userID) {
		Auth.userID = userID;
	}

	/**
	 * Gets the status of logged in.
	 * 
	 * @return logged in status
	 */
	public static boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Sets the status of logged in.
	 * 
	 * @param loggedIn status of logged in
	 */
	public static void setLoggedIn(boolean loggedIn) {
		Auth.loggedIn = loggedIn;
	}

	/**
	 * Checks the user is logged in or not.
	 * 
	 * <p>This method communicate with the eNotesz server.
	 * <p><b>Must to send API request because of encryption.</b>
	 * 
	 * @param name name of user
	 * @param password password of user
	 * @return AuthResponse
	 */
	public static AuthResponse check(String name, String password) {
		String apiKey = Core.getApiKey();
		String apiLink = Core.getApiBaseUrl();

		if (apiKey == null || apiLink == null) return null;

		HashMap<String,String> params = MapBuilder.<String, String>newHashMap()
				.set("pass", password)
				.set("name", name)
				.set("apiKey", apiKey)
				.build();

		try {
			String response = Core.createApi()
					.setMethod("POST")
					.setUrl(apiLink + "user/checkUser")
					.setParams(params)
					.sendRequest();
			if (response == null)
				return null;
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			return gson.fromJson(response, AuthResponse.class);
		} catch (Exception e) {
			logger.error("api or gson error", e);
		}

		return null;
	}

}
