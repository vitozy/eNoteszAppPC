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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.vitamas.enotesz.dao.EventsDao;
import hu.vitamas.enotesz.dao.TasksDao;
import hu.vitamas.enotesz.model.Events;
import hu.vitamas.enotesz.model.ListRecord;
import hu.vitamas.enotesz.model.Tasks;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.util.Core;
import hu.vitamas.enotesz.view.Alerts;
import hu.vitamas.enotesz.view.SimpleWindow;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class of the Main windows' Overview tab (include/overview.fxml).
 * 
 * @author vitozy
 *
 */
public class OverviewController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(OverviewController.class);

	@FXML
	private AnchorPane currentEventsList;

	@FXML
	private AnchorPane importantTasksList;

	@FXML
	private Button webBtn;

	@FXML
	private Button exitBtn;

	@FXML
	private Button userBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private Button settingsBtn;

	@FXML
	private Button contactBtn;

	@FXML
	private Button addEventBtn;

	@FXML
	private Button addTaskBtn;

	@FXML
	private Button refreshBtn;

	ArrayList<ListRecord> eventList = new ArrayList<>();
	ArrayList<ListRecord> taskList = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		webBtn.setOnMouseClicked(e -> Core.openWebsite("https://enotesz.vitamas.hu"));
		contactBtn.setOnMouseClicked(e -> Core.openWebsite("https://enotesz.vitamas.hu/welcome#contact"));
		refreshBtn.setOnMouseClicked(e -> initData());
		addEventBtn.setOnMouseClicked(e -> {
			SimpleWindow.createOf("Esemény létrehozása", "event/add").open();
		});
		settingsBtn.setOnMouseClicked(e -> {
			SimpleWindow.createOf("Beállítások", "user/settings").open();
		});
		userBtn.setOnMouseClicked(e -> {
			SimpleWindow.createOf("Fiók", "user/user").open();
		});
		helpBtn.setOnMouseClicked(e -> {
			SimpleWindow.createOf("Információk", "help").open();
		});
		exitBtn.setOnMouseClicked(e -> {
			try {
				Platform.exit();
			} catch (Exception e1) {
				logger.error("Exit error", e1);
			}
		});
		
		addTaskBtn.setOnMouseClicked(this::createTask);
	}

	/**
	 * Initializes the data of scene.
	 * 
	 * <p>Sets up current event and important task lists.
	 */
	public void initData() {
		initCurrentEvents();
		initImportantTasks();
	}

	private void createTask(MouseEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/task/add.fxml"));
			Parent root = (Parent) fxmlLoader.load();

			AddTaskController controller = fxmlLoader.<AddTaskController>getController();

			Stage stage = new Stage();
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/styles/Styles.css");
			stage.setTitle("eNotesz :: Új feladat létrehozása");
			stage.getIcons().add(new Image("/images/logo_icon.png"));
			stage.setScene(scene);
			stage.setOnCloseRequest(closeEvent -> initData());
			stage.addEventHandler(WindowEvent.WINDOW_SHOWING, winEvent -> controller.initData());
			stage.show();

		} catch (Exception ex) {
			logger.error("Add task window opening failed", ex);
		}
	}

	private void initCurrentEvents() {
		eventList.clear();
		
		EventsDao dao = new EventsDao();
		List<Events> events = dao.upcomingEvents(Auth.getUserID());
		events.stream().forEach(event -> {
			String title = event.getDateFrom() + " - " + event.getTitle();
			eventList.add(new ListRecord(title, event.getId()));
		});

		final ListView<ListRecord> eventsListView = new ListView<>();
		ObservableList<ListRecord> list = FXCollections.observableArrayList(eventList);
		eventsListView.setItems(list);

		eventsListView.setOnMouseClicked(event -> {

			Integer id = eventsListView.getSelectionModel().getSelectedIndex();
			eventsListView.getSelectionModel().clearSelection();

			if (id != -1) {

				ListRecord eventRec = eventList.get(id);
				Integer evId = eventRec.getId();

				if (eventRec != null) {
					Events ev = dao.findById(evId, Events.class);
					if (ev != null) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/event/view.fxml"));
							Parent root = (Parent) fxmlLoader.load();

							EventController controller = fxmlLoader.<EventController>getController();
							controller.setID(evId);

							Stage stage = new Stage();
							Scene scene = new Scene(root);
							stage.setTitle("eNotesz :: Esemény megtekintése");
							stage.getIcons().add(new Image("/images/logo_icon.png"));
							stage.setScene(scene);

							stage.addEventHandler(WindowEvent.WINDOW_SHOWING, e -> controller.initData());
							stage.show();
						} catch (Exception ex) {
							logger.error("Window opening erro @currentEvent", ex);
						}
					} else {
						Alerts.error("Sikertelen adatlekérés!").showAndWait();
					}
				}
			}
		});

		currentEventsList.getChildren().add(eventsListView);
		eventsListView.prefWidthProperty().bind(currentEventsList.widthProperty());
		eventsListView.prefHeightProperty().bind(currentEventsList.heightProperty());
	}

	private void initImportantTasks() {
		taskList.clear();
		
		TasksDao dao = new TasksDao();
		List<Tasks> tasks = dao.importantTasks(Auth.getUserID());
		tasks.stream().forEach(task -> {
			String title = "[" + task.getTasksGroups().getName() + "] " + task.getTasksPriority().getName() + " - " + task.getTitle();
			taskList.add(new ListRecord(title, task.getId()));
		});
		
		final ListView<ListRecord> listView = new ListView<>();
		ObservableList<ListRecord> list = FXCollections.observableArrayList(taskList);
		listView.setItems(list);

		listView.setOnMouseClicked(event -> {

			Integer id = listView.getSelectionModel().getSelectedIndex();
			listView.getSelectionModel().clearSelection();
			
			if (id != -1) {
				ListRecord rec = taskList.get(id);
				Integer taskId = rec.getId();

				if (rec != null) {
					Tasks task = dao.findById(taskId, Tasks.class);
					if (task != null) {
						if (task.getTasksGroups() != null) {
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/task/listview.fxml"));
								Parent root = (Parent) fxmlLoader.load();

								TasksGroupController controller = fxmlLoader.<TasksGroupController>getController();
								controller.setID(task.getTasksGroups().getId());

								Scene scene = new Scene(root);
								Stage stage = new Stage();
								stage.setTitle("eNotesz :: Teendőlista megtekintése");
								stage.getIcons().add(new Image("/images/logo_icon.png"));
								stage.setScene(scene);

								stage.addEventHandler(WindowEvent.WINDOW_SHOWING, e -> controller.initData());
								stage.show();
							} catch (Exception ex) {
								logger.error("Taskgroup view window open failed", ex);
							}
						} else {
							Alerts.error("Sikertelen adatlekérés!").showAndWait();
						}
					} else {
						Alerts.error("Sikertelen adatlekérés!").showAndWait();
					}
				}
			}
		});

		importantTasksList.getChildren().clear();
		importantTasksList.getChildren().add(listView);
		listView.prefWidthProperty().bind(importantTasksList.widthProperty());
		listView.prefHeightProperty().bind(importantTasksList.heightProperty());
	}
}
