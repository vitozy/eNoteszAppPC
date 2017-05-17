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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.vitamas.enotesz.dao.TasksDao;
import hu.vitamas.enotesz.dao.TasksGroupsDao;
import hu.vitamas.enotesz.model.ListRecord;
import hu.vitamas.enotesz.model.Tasks;
import hu.vitamas.enotesz.model.TasksGroups;
import hu.vitamas.enotesz.model.TasksPriority;
import hu.vitamas.enotesz.view.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class of task group scene (task/listview.fxml).
 *
 * @author vitozy
 */
public class TasksGroupController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(TasksGroupController.class);

	private Integer groupID = 0;
	private Tasks openedTask = null;
	private TasksGroups tasksGroup = null;
	private ArrayList<ListRecord> tasksList = new ArrayList<>();

	@FXML
	private Label groupViewTitle;

	@FXML
	private Button taskDeleteBtn;

	@FXML
	private Label taskDeadline;

	@FXML
	private Label taskPriority;

	@FXML
	private Button taskEditBtn;

	@FXML
	private Button taskgroupDeleteBtn;

	@FXML
	private Button refreshBtn;

	@FXML
	private Button createTaskBtn;

	@FXML
	private TitledPane eventInfo;

	@FXML
	private TitledPane eventText;

	@FXML
	private Label taskTitle;

	@FXML
	private Button taskgroupEditBtn;

	@FXML
	private AnchorPane AnchorPane;

	@FXML
	private AnchorPane tasks;

	@FXML
	private TextFlow taskText;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		refreshBtn.setOnMouseClicked(this::refresh);
		taskgroupEditBtn.setOnMouseClicked(this::rename);
		taskgroupDeleteBtn.setOnMouseClicked(this::delete);
		createTaskBtn.setOnMouseClicked(this::createTask);
		taskEditBtn.setOnMouseClicked(this::editOpenedTask);
		taskDeleteBtn.setOnMouseClicked(this::deleteOpenedTask);
	}

	/**
	 * Sets up the group ID.
	 * 
	 * @param id group ID
	 */
	public void setID(Integer id) {
		this.groupID = id;
	}

	private void refresh(MouseEvent e) {
		initData();
	}

	private void rename(MouseEvent e) {
		if (tasksGroup != null) {
			TextInputDialog dialog = new TextInputDialog(tasksGroup.getName());
			dialog.setTitle("eNotesz :: Teendőlista átnevezése");
			dialog.setHeaderText("Mi legyen az új név?");

			Optional<String> result = dialog.showAndWait();
			String entered = "";

			if (result.isPresent()) {
				entered = result.get();
			}
			
			if(entered.length() == 0) return;

			if (!tasksGroup.getName().equals(entered)) {
				tasksGroup.setName(entered);
				TasksGroupsDao dao = new TasksGroupsDao();
				dao.update(tasksGroup);
				groupViewTitle.setText("Teendőlista: " + entered);
			}

		} else {
			Alerts.error("Hiba történt").show();
		}

	}

	private void delete(MouseEvent e) {
		TasksGroupsDao dao = new TasksGroupsDao();
		TasksGroups tg = tasksGroup;
		if (tg != null) {
			try {
				tg.getTasks().clear();
				dao.delete(tg);

				Alert alert = Alerts.success("Sikeresen törlésre került!");
				alert.setOnCloseRequest(creq -> ((Node) (e.getSource())).getScene().getWindow().hide());
				alert.showAndWait().ifPresent(response -> {
					((Node) (e.getSource())).getScene().getWindow().hide();
				});

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			Alerts.error("Sikertelen adatlekérés!").showAndWait();
		}
	}

	private void createTask(MouseEvent e) {
		if (tasksGroup != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/task/add.fxml"));
				Parent root = (Parent) fxmlLoader.load();

				AddTaskController controller = fxmlLoader.<AddTaskController>getController();

				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setTitle("eNotesz :: Új feladat létrehozása");
				stage.getIcons().add(new Image("/images/logo_icon.png"));
				stage.setScene(scene);
				stage.setOnCloseRequest(closeEvent -> initData());
				stage.addEventHandler(WindowEvent.WINDOW_SHOWING, winEvent -> controller.initData());
				stage.setResizable(false);
				stage.sizeToScene();
				stage.show();

			} catch (Exception ex) {
				logger.error("Add task window opening failed", ex);
			}
		} else {
			Alerts.error("Sikertelen adatlekérés!").showAndWait();
		}
	}

	private void editOpenedTask(MouseEvent e) {
		if (openedTask != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/task/edit.fxml"));
				Parent root = (Parent) fxmlLoader.load();

				EditTaskController controller = fxmlLoader.<EditTaskController>getController();
				controller.setID(openedTask.getId());

				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setTitle("eNotesz :: Feladat szerkesztése");
				stage.getIcons().add(new Image("/images/logo_icon.png"));
				stage.setScene(scene);
				stage.setOnCloseRequest(closeEvent -> initData());
				stage.addEventHandler(WindowEvent.WINDOW_SHOWING, winEvent -> controller.initData());
				stage.setResizable(false);
				stage.sizeToScene();
				stage.show();

			} catch (Exception ex) {
				logger.error("Edit task window opening failed", ex);
			}
		} else {
			Alerts.error("Nincs kijelölve teendő!").showAndWait();
		}
	}

	private void deleteOpenedTask(MouseEvent e) {
		if (openedTask != null) {
			TasksDao taskdao = new TasksDao();
			taskdao.deleteById(openedTask.getId(), Tasks.class);
			setDefaultTask();
			initData();
		} else {
			Alerts.error("Nincs kijelölve teendő!").showAndWait();
		}
	}

	/**
	 * Initializes data of this scene.
	 * 
	 * <p>Sets up group information and list of tasks.
	 */
	public void initData() {
		TasksGroupsDao dao = new TasksGroupsDao();
		TasksGroups tg = dao.findById(groupID, TasksGroups.class);
		if (tg != null) {
			groupViewTitle.setText("Teendőlista: " + tg.getName());
			tasksGroup = tg;
			initList();
		}
	}

	private void initList() {
		tasksList.clear();
		tasksGroup.getTasks().stream().sorted((e1, e2) -> e1.getTitle().compareTo(e2.getTitle()))
				.forEach(item -> tasksList.add(new ListRecord(item.getTitle(), item.getId())));

		final ListView<ListRecord> listView = new ListView<>();
		ObservableList<ListRecord> list = FXCollections.observableArrayList(tasksList);
		listView.setItems(list);

		listView.setOnMouseClicked(event -> {

			Integer id = listView.getSelectionModel().getSelectedIndex();
			listView.getSelectionModel().clearSelection();

			if (id != -1) {
				ListRecord rec = tasksList.get(id);

				if (rec != null) {
					Integer taskId = rec.getId();

					TasksDao tdao = new TasksDao();
					Tasks task = tdao.findById(taskId, Tasks.class);
					if (task != null) {
						openedTask = task;
						refreshTask();
					}
				}
			}
		});

		tasks.getChildren().clear();
		tasks.getChildren().add(listView);
		listView.prefWidthProperty().bind(tasks.widthProperty());
		listView.prefHeightProperty().bind(tasks.heightProperty());

		if(openedTask != null)
		{
			TasksDao tdao = new TasksDao();
			Tasks task = tdao.findById(openedTask.getId(), Tasks.class);
			openedTask = task;
			refreshTask();
		}
		
		if (tasksList.size() > 0) {
			if (openedTask == null) {
				ListRecord rec = tasksList.get(0);

				if (rec != null) {
					Integer taskId = rec.getId();

					TasksDao tdao = new TasksDao();
					Tasks task = tdao.findById(taskId, Tasks.class);
					if (task != null) {
						openedTask = task;
						refreshTask();
					} else
						setDefaultTask();
				} else
					setDefaultTask();
			}
		} else
			setDefaultTask();
	}

	private void refreshTask() {
		if (openedTask != null) {
			taskTitle.setText(openedTask.getTitle());
			LocalDate deadline = openedTask.getDeadline();
			String deadlineStr = deadline != null ? deadline.toString() : "nincs megadva";
			taskDeadline.setText(deadlineStr);
			TasksPriority tp = openedTask.getTasksPriority();
			String prio = tp != null ? tp.getName() : "nincs megadva";
			taskPriority.setText(prio);
			taskText.getChildren().clear();
			taskText.getChildren().add(new Text(openedTask.getText()));

			taskEditBtn.setVisible(true);
			taskDeleteBtn.setVisible(true);
		} else
			logger.warn("Refresh task null");
	}

	private void setDefaultTask() {
		openedTask = null;

		taskEditBtn.setVisible(false);
		taskDeleteBtn.setVisible(false);

		taskTitle.setText("nincs kiválasztva");
		taskDeadline.setText("nincs kiválasztva");
		taskPriority.setText("nincs kiválasztva");
		taskText.getChildren().clear();
		taskText.getChildren().add(new Text("nincs kiválasztva"));
	}
}
