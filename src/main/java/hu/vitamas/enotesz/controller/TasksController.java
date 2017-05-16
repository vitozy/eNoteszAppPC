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

import hu.vitamas.enotesz.dao.TasksDao;
import hu.vitamas.enotesz.dao.TasksGroupsDao;
import hu.vitamas.enotesz.dao.UsersDao;
import hu.vitamas.enotesz.model.ListRecord;
import hu.vitamas.enotesz.model.Tasks;
import hu.vitamas.enotesz.model.TasksGroups;
import hu.vitamas.enotesz.model.Users;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.view.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class of the Main windows' Tasks tab (include/tasks.fxml).
 * 
 * @author vitozy
 *
 */
public class TasksController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(TasksController.class);

	@FXML
	AnchorPane deadlines;
	@FXML
	AnchorPane importants;
	@FXML
	AnchorPane taskgroups;
	@FXML
	Button createListBtn;
	@FXML
	TextField createListName;
	@FXML
	Button refreshBtn;

	ArrayList<ListRecord> groupList = new ArrayList<>();
	ArrayList<ListRecord> importantsList = new ArrayList<>();
	ArrayList<ListRecord> deadlinesList = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		refreshBtn.setOnMouseClicked(this::refresh);
		createListBtn.setOnMouseClicked(this::addTasklist);
	}

	/**
	 * Initializes data of this scene.
	 * 
	 * <p>Sets up lists of important tasks, tasks with deadline and groups.
	 */
	public void initData() {
		setImportantsList();
		setDeadlinesList();
		setGroupList();
	}

	private void setGroupList() {
		groupList.clear();

		TasksGroupsDao dao = new TasksGroupsDao();
		List<TasksGroups> events = dao.list(Auth.getUserID());
		events.stream().forEach(item -> groupList.add(new ListRecord(item.getName(), item.getId())));

		final ListView<ListRecord> listView = new ListView<>();
		ObservableList<ListRecord> list = FXCollections.observableArrayList(groupList);
		listView.setItems(list);

		listView.setOnMouseClicked(event -> {

			Integer id = listView.getSelectionModel().getSelectedIndex();
			listView.getSelectionModel().clearSelection();

			if (id != -1) {
				ListRecord eventRec = groupList.get(id);
				Integer gId = eventRec.getId();

				if (eventRec != null) {
					TasksGroups tg = dao.findById(gId, TasksGroups.class);
					if (tg != null) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/task/listview.fxml"));
							Parent root = (Parent) fxmlLoader.load();

							TasksGroupController controller = fxmlLoader.<TasksGroupController>getController();
							controller.setID(gId);

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
				}
			}
		});

		taskgroups.getChildren().clear();
		taskgroups.getChildren().add(listView);
		listView.prefWidthProperty().bind(taskgroups.widthProperty());
		listView.prefHeightProperty().bind(taskgroups.heightProperty());

	}

	private void setDeadlinesList() {
		deadlinesList.clear();
		
		TasksDao dao = new TasksDao();
		List<Tasks> tasks = dao.deadlineTasks(Auth.getUserID());
		tasks.stream().forEach(task -> {
			String title = "[" + task.getTasksGroups().getName() + "] " + task.getDeadline() + " - " + task.getTitle();
			deadlinesList.add(new ListRecord(title, task.getId()));
		});
		
		final ListView<ListRecord> listView = new ListView<>();
		ObservableList<ListRecord> list = FXCollections.observableArrayList(deadlinesList);
		listView.setItems(list);

		listView.setOnMouseClicked(event -> {

			Integer id = listView.getSelectionModel().getSelectedIndex();
			listView.getSelectionModel().clearSelection();
			
			if (id != -1) {
				ListRecord rec = deadlinesList.get(id);
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
								stage.setTitle("eNotesz :: Teendőlista megtekint�se");
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

		deadlines.getChildren().clear();
		deadlines.getChildren().add(listView);
		listView.prefWidthProperty().bind(deadlines.widthProperty());
		listView.prefHeightProperty().bind(deadlines.heightProperty());
	}

	private void setImportantsList() {
		importantsList.clear();
		
		TasksDao dao = new TasksDao();
		List<Tasks> tasks = dao.importantTasks(Auth.getUserID());
		tasks.stream().forEach(task -> {
			String title = "[" + task.getTasksGroups().getName() + "] " + task.getTasksPriority().getName() + " - " + task.getTitle();
			importantsList.add(new ListRecord(title, task.getId()));
		});
		
		final ListView<ListRecord> listView = new ListView<>();
		ObservableList<ListRecord> list = FXCollections.observableArrayList(importantsList);
		listView.setItems(list);

		listView.setOnMouseClicked(event -> {

			Integer id = listView.getSelectionModel().getSelectedIndex();
			listView.getSelectionModel().clearSelection();
			
			if (id != -1) {
				ListRecord rec = importantsList.get(id);
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
								stage.setTitle("eNotesz :: Teendőlista megtekint�se");
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

		importants.getChildren().clear();
		importants.getChildren().add(listView);
		listView.prefWidthProperty().bind(importants.widthProperty());
		listView.prefHeightProperty().bind(importants.heightProperty());
	}

	private void refresh(MouseEvent e) {
		setImportantsList();
		setDeadlinesList();
		setGroupList();
	}

	private void addTasklist(MouseEvent e) {
		Users u = (new UsersDao()).getSessionUser();
		if (u != null) {
			String name = createListName.getText();
			TasksGroups tg = new TasksGroups();
			tg.setName(name);
			tg.setUsers(u);
			TasksGroupsDao dao = new TasksGroupsDao();
			try {
				dao.create(tg);
				Alerts.info("Új lista létrehozva!").showAndWait();
				setGroupList();
			} catch (Exception ex) {
				Alerts.error("Hiba történt!").showAndWait();
			}
		} else {
			logger.error("Authenticated user not found...");
			Alerts.error("Azonosítási hiba történt!").showAndWait();
		}
	}
}
