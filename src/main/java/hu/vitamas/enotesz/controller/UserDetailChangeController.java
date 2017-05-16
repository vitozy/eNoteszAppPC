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

package hu.vitamas.enotesz.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.vitamas.enotesz.dao.UsersDao;
import hu.vitamas.enotesz.json.SimpleApiResponse;
import hu.vitamas.enotesz.model.Users;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.util.Core;
import hu.vitamas.enotesz.util.MapBuilder;
import hu.vitamas.enotesz.view.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of the Account/User information change window (user/detailchange.fxml).
 * 
 * @author vitozy
 *
 */
public class UserDetailChangeController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(UserDetailChangeController.class);

	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private TextField username;
	@FXML
	private TextField email;
	@FXML
	private PasswordField checkpass;
	@FXML
	private Button saveBtn;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		try {
			UsersDao usersDao = new UsersDao();
			Users user = usersDao.findById(Auth.getUserID(), Users.class);

			username.setText(user.getName());
			email.setText(user.getEmail());
			saveBtn.setOnMouseClicked(this::save);
		} catch (Exception e) {
			logger.error("User data error -- session", e);

			Alerts.error("Sikertelen adatlekérés!").showAndWait();
		}
	}

	private void save(MouseEvent ev) {

		Integer userid = Auth.getUserID();
		String chpass = checkpass.getText();
		String uname = username.getText();
		String uemail = email.getText();

		SimpleApiResponse response = sendChangeRequest(chpass, uname, uemail, userid);
		if (response != null) {
			if (response.getSuccess()) {
				Alerts.success("Sikeres művelet!").show();
			} else {
				Alerts.warning(response.getErrors().replaceAll("<br>"," ")).show();
				logger.error("api change user detail - Status: " + response.getStatus());
			}
		} else {
			Alerts.warning("Hiba történt, kérlek próbáld újra később!");
		}

	}

	private SimpleApiResponse sendChangeRequest(String chpass, String uname, String uemail, Integer userid) {
		
		String apiKey = Core.getApiKey();
		String apiLink = Core.getApiBaseUrl();

		if (apiKey == null || apiLink == null) return null;

		HashMap<String,String> params = MapBuilder.<String, String>newHashMap()
				.set("userid", userid.toString())
				.set("pass", chpass)
				.set("name", uname)
				.set("email", uemail)
				.set("apiKey", apiKey)
				.build();
		
		try {
			String response = Core.createApi()
					.setMethod("POST")
					.setUrl(apiLink + "user/changeDetail")
					.setParams(params)
					.sendRequest();
			if (response == null)
				return null;
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			return gson.fromJson(response, SimpleApiResponse.class);
		} catch (Exception e) {
			logger.error("api or gson error", e);
		}

		return null;
	}
}
