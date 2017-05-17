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

package hu.vitamas.enotesz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main class of the eNotesz application.
 * 
 * @author vitozy
 *
 */
public class MainApp extends Application {

	static Logger logger = LoggerFactory.getLogger(MainApp.class);
	
	/**
	 * Starts the first scene (login screen) of the program.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("eNotesz :: Bejelentkezés");
		stage.getIcons().add(new Image("/images/logo_icon.png"));
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
	}

	/**
	 * Entry method of the application.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
