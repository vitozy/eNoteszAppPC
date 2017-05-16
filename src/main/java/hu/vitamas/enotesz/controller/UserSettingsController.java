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
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.vitamas.enotesz.dao.UsersDao;
import hu.vitamas.enotesz.model.Users;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.view.Alerts;
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
		if(user != null)
		{
			Boolean daily = user.getDailyReport() == 1;
			dailyreport.setSelected(daily);
		}
		
		saveBtn.setOnMouseClicked(this::save);

	}

	private void save(MouseEvent ev) {

		Boolean needDailyReport = dailyreport.isSelected();

		Users user = Auth.getUser();
		if(user != null)
		{
			Integer dailyflag = needDailyReport ? 1 : 0;
			
			user.setDailyReport(dailyflag);
			UsersDao dao = new UsersDao();
			dao.update(user);
			
			Alerts.success("Sikeres módosításra került!").show();
		}
		else {
			Alerts.error("Hiba történt!").show();
		}

	}


}
