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

import hu.vitamas.enotesz.view.SimpleWindow;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class of the main.fxml.
 * 
 * @author vitozy
 *
 */
public class AppController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@FXML
	AnchorPane anchorPane;
	@FXML
	SplitPane split;
	@FXML
	ProgressIndicator progress;
	@FXML
	Button helpBtn;
	@FXML
	Button exitBtn;
	@FXML
	Button settingsBtn;
	@FXML
	AnchorPane eventsTab;
	@FXML
	EventsController eventsTabController;
	@FXML
	AnchorPane overviewTab;
	@FXML
	OverviewController overviewTabController;
	@FXML
	AnchorPane tasksTab;
	@FXML
	TasksController tasksTabController;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		helpBtn.setOnMouseClicked(e -> {
			SimpleWindow.createOf("Információk", "help").open();
		});
		settingsBtn.setOnMouseClicked(e -> {
			SimpleWindow.createOf("Beállítások", "user/settings").open();
		});
		exitBtn.setOnMouseClicked(e -> {
			try {
				Platform.exit();
			} catch (Exception ex) {
				logger.error("Exit failed", ex);
			}
		});
	}

	/**
	 * Initializes the data of the scene.
	 * 
	 * <p>Set up a progress indicator while the data setting up.</p>
	 * <p>Initializes the lists of tabs.</p>
	 */
	public void initData() {
		split.setDisable(true);
		progress.setVisible(true);

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				overviewTabController.initData();
				eventsTabController.initData();
				tasksTabController.initData();

				split.setDisable(false);
				progress.setVisible(false);
				return null;
			}
		};
		
		Platform.runLater(() -> {
			task.run();
		});
	}
}
