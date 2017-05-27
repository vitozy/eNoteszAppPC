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

package hu.vitamas.enotesz.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Class for using easier the Alert class.
 * 
 * <p>
 * <b>Usage (with just title)</b><br>
 * 
 * <pre>
 * Alerts.error("It is a new error alert.").show();
 * </pre>
 * 
 * <p>
 * <b>Usage (with title and more information)</b><br>
 * 
 * <pre>
 * Alerts.error("It is a new error alert.", "This is an information about this alert.").show();
 * </pre>
 * 
 * <p>
 * <b>Parameter information:</b><br>
 * - title: equal with header text<br>
 * - text: equal with context text (optional)
 * </p>
 * 
 * @author vitozy
 *
 */
public class Alerts {

	/**
	 * Creates a new error alert with title and text.
	 * 
	 * @param title the title of alert
	 * @param text the information of alert
	 * @return new alert instance
	 */
	public static Alert error(String title, String text) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Hiba történt");
		alert.setHeaderText(title);
		alert.setContentText(text);
		alert.setGraphic(new ImageView(Alerts.class.getClass().getResource("/images/alerts/error.png").toString()));
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Alerts.class.getResource("/images/logo_icon.png").toString()));
		return alert;
	}

	/**
	 * Creates a new error alert with title.
	 * 
	 * @param title the title of alert
	 * @return new alert instance
	 */
	public static Alert error(String title) {
		return error(title, null);
	}

	/**
	 * Creates a new info alert with title and text.
	 * 
	 * @param title the title of alert
	 * @param text the information of alert
	 * @return new alert instance
	 */
	public static Alert info(String title, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Információ");
		alert.setHeaderText(title);
		alert.setContentText(text);
		alert.setGraphic(new ImageView(Alerts.class.getClass().getResource("/images/alerts/info.png").toString()));
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Alerts.class.getResource("/images/logo_icon.png").toString()));
		return alert;
	}

	/**
	 * Creates a new info alert with title.
	 * 
	 * @param title the title of alert
	 * @return new alert instance
	 */
	public static Alert info(String title) {
		return info(title, null);
	}

	/**
	 * Creates a new success alert with title and text.
	 * 
	 * @param title the title of alert
	 * @param text the information of alert
	 * @return new alert instance
	 */
	public static Alert success(String title, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sikeres művelet");
		alert.setHeaderText(title);
		alert.setContentText(text);
		alert.setGraphic(new ImageView(Alerts.class.getClass().getResource("/images/alerts/success.png").toString()));
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Alerts.class.getResource("/images/logo_icon.png").toString()));
		return alert;
	}

	/**
	 * Creates a new success alert with title.
	 * 
	 * @param title the title of alert
	 * @return new alert instance
	 */
	public static Alert success(String title) {
		return success(title, null);
	}

	/**
	 * Creates a new warning alert with title and text.
	 * 
	 * @param title the title of alert
	 * @param text the information of alert
	 * @return new alert instance
	 */
	public static Alert warning(String title, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Sikertelen művelet");
		alert.setHeaderText(title);
		alert.setContentText(text);
		alert.setGraphic(new ImageView(Alerts.class.getClass().getResource("/images/alerts/warning.png").toString()));
		alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/styles/dialog.css").toExternalForm());
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Alerts.class.getResource("/images/logo_icon.png").toString()));
		return alert;
	}

	/**
	 * Creates a new warning alert with title.
	 * 
	 * @param title the title of alert
	 * @return new alert instance
	 */
	public static Alert warning(String title) {
		return warning(title, null);
	}
}
