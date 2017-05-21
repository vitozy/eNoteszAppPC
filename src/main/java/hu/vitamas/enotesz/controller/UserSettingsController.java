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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of the user settings window (user/settings.fxml).
 * 
 * @author vitozy
 *
 */
public class UserSettingsController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(UserSettingsController.class);

	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private CheckBox dailyreport;
	@FXML
	private PasswordField deletePasswordField;

	@FXML
	private Button saveBtn;
	@FXML
	private Button deleteAccountBtn;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		Users user = Auth.getUser();
		if (user != null) {
			Boolean daily = user.getDailyReport() == 1;
			dailyreport.setSelected(daily);
		}

		saveBtn.setOnMouseClicked(this::save);
		deleteAccountBtn.setOnMouseClicked(this::deleteAccount);
	}

	private void save(MouseEvent ev) {

		Boolean needDailyReport = dailyreport.isSelected();

		Users user = Auth.getUser();
		if (user != null) {
			Integer dailyflag = needDailyReport ? 1 : 0;

			user.setDailyReport(dailyflag);
			UsersDao dao = new UsersDao();
			dao.update(user);

			Alerts.success("Sikeres módosításra került!").show();
		} else {
			Alerts.error("Hiba történt!").show();
		}

	}

	private void deleteAccount(MouseEvent ev) {
		SimpleApiResponse response = sendDeleteRequest();
		if (response != null) {
			if (response.getSuccess()) {
				Core.openWebsite("https://enotesz.vitamas.hu/goodbye");
				Platform.exit();
			} else {
				Alerts.warning(response.getErrors().replaceAll("<br>", " ")).show();
				logger.error("api delete acc - Status: " + response.getStatus());
			}
		} else {
			Alerts.warning("Hiba történt, kérlek próbáld újra később!");
		}
	}

	private SimpleApiResponse sendDeleteRequest() {

		String apiKey = Core.getApiKey();
		String apiLink = Core.getApiBaseUrl();
		String pass = deletePasswordField.getText();
		Integer id = Auth.getUserID();

		if (apiKey == null || apiLink == null)
			return null;

		HashMap<String, String> params = MapBuilder.<String, String>newHashMap()
				.set("userid", id.toString())
				.set("password", pass)
				.set("apiKey", apiKey)
				.build();

		try {
			String response = Core.createApi()
					.setMethod("POST")
					.setUrl(apiLink + "user/deleteAccount")
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
