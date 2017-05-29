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

import hu.vitamas.enotesz.json.AuthResponse;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.util.Core;
import hu.vitamas.enotesz.view.Alerts;
import hu.vitamas.enotesz.view.SimpleWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Node;

/**
 * Controller of the login window (login.fxml).
 * 
 * @author vitozy
 *
 */
public class LoginController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@FXML
	Button registerBtn;

	@FXML
	PasswordField userPassword;

	@FXML
	TextField userName;
	
	@FXML
    private Button smallwebBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Button smallcopyBtn;

    @FXML
    private Button openwebBtn;

    @FXML
    private Button smallcodeBtn;

    @FXML
    private Button smallcontactBtn;


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loginBtn.setOnMouseClicked(this::login);
		openwebBtn.setOnMouseClicked(e -> Core.openWebsite("https://enotesz.vitamas.hu"));
		smallwebBtn.setOnMouseClicked(e -> Core.openWebsite("https://enotesz.vitamas.hu"));
		smallcodeBtn.setOnMouseClicked(e -> Core.openWebsite("https://github.com/vitozy/enotesz-desktop"));
		smallcontactBtn.setOnMouseClicked(e -> Core.openWebsite("https://enotesz.vitamas.hu/welcome#contact"));
		smallcopyBtn.setOnMouseClicked(e -> Core.openWebsite("https://github.com/vitozy/enotesz-desktop/wiki/Credits"));
		registerBtn.setOnMouseClicked(e -> {
			SimpleWindow.createOf("Regisztráció", "registration").open();
		});
	}

	private void login(MouseEvent e) {
		String upass = userPassword.getText();
		String uname = userName.getText();

		if (upass.length() > 0 && uname.length() > 0) {

			try {

				AuthResponse response = Auth.check(uname, upass);

				if (response != null) {

					if (response.getStatus().equals("ok")) {

						try {
							Auth.setLoggedIn(true);
							Auth.setUserID(response.getUserid());

							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
							Parent root = (Parent) fxmlLoader.load();
							Stage stage = new Stage();
							Scene scene = new Scene(root);
							stage.setTitle("eNotesz - Legyél mindig naprakész!");
							stage.getIcons().add(new Image(this.getClass().getResource("/images/logo_icon.png").toString()));
							stage.setOnCloseRequest(closeEvent -> {
								try {
									Platform.exit();
								} catch (Exception e1) {
									logger.error("Exit error", e1);
								}
							});
							stage.setScene(scene);
							
							AppController controller = fxmlLoader.<AppController>getController();
							stage.addEventHandler(WindowEvent.WINDOW_SHOWN, init -> controller.initData());
							stage.setResizable(false);
							stage.sizeToScene();
							stage.show();
							
							((Node) (e.getSource())).getScene().getWindow().hide();
							
						} catch (Exception ex) {
							Alerts.error("Sikertelen bejelentkezés").show();

							logger.error("Login failed", ex);
						}

					} else if (response.getStatus().equals("bad password")) {

						String content = "De az is előfordulhat, hogy nincs internetkapcsolat.";
						Alerts.warning("Rossz jelszó vagy név", content).show();

					} else if (response.getStatus().equals("not isset")) {
						
						String content = "De az is előfordulhat, hogy nincs internetkapcsolat.";
						Alerts.warning("Nem létező felhasználó!", content).show();
					}
				}
				else
				{
					String content = "Előfordulhat, hogy nincs internetkapcsolat.";
					Alerts.warning("Sikertelen bejelentkezés!", content).show();
				}
			} catch (Exception ex) {
				String content = "Előfordulhat, hogy nincs internetkapcsolat.";
				Alerts.warning("Sikertelen bejelentkezés!", content).show();
				
				logger.error("Login failed", ex);
			}
		} else {
			Alerts.warning("Hiányzó adatok!", "Kérlek töltsd ki az összes mezőt!").show();
		}

	}

}
