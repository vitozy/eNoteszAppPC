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

import hu.vitamas.enotesz.json.SimpleApiResponse;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.util.Core;
import hu.vitamas.enotesz.util.MapBuilder;
import hu.vitamas.enotesz.view.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of the Account/User password edit window (user/passchange.fxml).
 * 
 * @author vitozy
 *
 */
public class UserPasswordChangeController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(UserPasswordChangeController.class);

	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private PasswordField pass;
	@FXML
	private PasswordField repass;
	@FXML
	private PasswordField checkpass;
	@FXML
	private Button saveBtn;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		saveBtn.setOnMouseClicked(this::save);

	}

	private void save(MouseEvent ev) {

		Integer userid = Auth.getUserID();
		String chpass = checkpass.getText();
		String newpass = pass.getText();
		String renewpass = repass.getText();

		if (!newpass.equals(renewpass)) {
			Alerts.warning("Az új jelszó nem egyezik!");
		} else {
			SimpleApiResponse response = sendChangeRequest(newpass, chpass, userid);
			if (response != null) {
				if (response.getSuccess()) {
					Alerts.success("Sikeres művelet!").show();
				} else {
					Alerts.warning(response.getErrors().replaceAll("<br>"," ")).show();
					logger.error("api change user pass - Status: " + response.getStatus());
				}
			} else {
				Alerts.warning("Hiba történt, kérlek próbáld újra később!");
			}
		}

	}

	private SimpleApiResponse sendChangeRequest(String newpass, String chpass, Integer userid) {
		
		String apiKey = Core.getApiKey();
		String apiLink = Core.getApiBaseUrl();

		if (apiKey == null || apiLink == null) return null;

		HashMap<String,String> params = MapBuilder.<String, String>newHashMap()
				.set("userid", userid.toString())
				.set("pass", chpass)
				.set("newpass", newpass)
				.set("apiKey", apiKey)
				.build();

		try {
			String response = Core.createApi()
					.setMethod("POST")
					.setUrl(apiLink + "user/changePassword")
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
