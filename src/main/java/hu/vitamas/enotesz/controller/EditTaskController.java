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
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.vitamas.enotesz.dao.TasksDao;
import hu.vitamas.enotesz.dao.TasksGroupsDao;
import hu.vitamas.enotesz.dao.TasksPriorityDao;
import hu.vitamas.enotesz.model.ListRecord;
import hu.vitamas.enotesz.model.Tasks;
import hu.vitamas.enotesz.model.TasksGroups;
import hu.vitamas.enotesz.model.TasksPriority;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.view.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class of the task/edit.fxml.
 * 
 * @author vitozy
 *
 */
public class EditTaskController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(EditTaskController.class);
	private Integer taskID = 0;
	ArrayList<ListRecord> groupList = new ArrayList<>();
	ArrayList<ListRecord> priorityList = new ArrayList<>();

	@FXML
	private CheckBox noDeadline;

	@FXML
	private ChoiceBox<ListRecord> taskgroup;

	@FXML
	private TextField taskname;

	@FXML
	private ChoiceBox<ListRecord> taskpriority;

	@FXML
	private DatePicker deadline;

	@FXML
	private AnchorPane AnchorPane;

	@FXML
	private Button saveBtn;

	@FXML
	private TextArea info;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		saveBtn.setOnMouseClicked(this::save);
	}

	/**
	 * Sets up the ID of the task we need to edit.
	 * 
	 * @param id ID of task
	 */
	public void setID(Integer id) {
		this.taskID = id;
	}

	private void save(MouseEvent e) {

		Boolean error = false;

		// group validation
		ListRecord selectedGroupRecord = taskgroup.getValue();
		TasksGroups tg = null;
		if (selectedGroupRecord == null) {
			error = true;
		} else {
			Integer group = selectedGroupRecord.getId();
			TasksGroupsDao tgdao = new TasksGroupsDao();
			tg = tgdao.findById(group, TasksGroups.class);
			if (tg == null) {
				error = true;
			}
		}

		// priority validation
		ListRecord selectedPriorityRecord = taskpriority.getValue();
		TasksPriority tp = null;
		if (selectedPriorityRecord == null) {
			error = true;
		} else {
			Integer priority = selectedPriorityRecord.getId();
			TasksPriorityDao tpdao = new TasksPriorityDao();
			tp = tpdao.findById(priority, TasksPriority.class);
			if (tp == null) {
				error = true;
			}
		}

		// title validation
		if (taskname.getText().length() == 0) {
			error = true;
		}

		// deadline validation
		LocalDate deadlineDate = null;
		if (noDeadline.isSelected()) {
			deadlineDate = null;
		} else {
			deadlineDate = deadline.getValue();
			if (deadlineDate == null) {
				error = true;
			}
		}

		// save
		if (!error) {
			TasksDao taskdao = new TasksDao();
			Tasks task = taskdao.findById(taskID, Tasks.class);
			if (task != null) {
				task.setTitle(taskname.getText());
				task.setTasksGroups(tg);
				task.setTasksPriority(tp);
				task.setCompleted(0);
				task.setText(info.getText());
				task.setDeadline(deadlineDate);
				taskdao.update(task);

				Alert alert = Alerts.success("Sikeresen mentve!");
				alert.setOnCloseRequest(creq -> closeWindow(e));
				alert.showAndWait().ifPresent(response -> closeWindow(e));
			} else {
				Alert alert = Alerts.warning("Nem létező tartalmat próbáltál szerkeszteni!");
				alert.setOnCloseRequest(creq -> closeWindow(e));
				alert.showAndWait().ifPresent(response -> closeWindow(e));
			}
		} else

		{
			Alerts.error("Hibás vagy hiányzó adatok!").show();
		}

	}

	private void closeWindow(MouseEvent e) {
		Node n = (Node) (e.getSource());
		Stage stg = (Stage) n.getScene().getWindow();
		stg.fireEvent(new WindowEvent(stg, WindowEvent.WINDOW_CLOSE_REQUEST));
		stg.close();
	}

	/**
	 * Initializes the data of the scene.
	 * 
	 * <p>Sets up the group and priority lists and puts current data of task into the fields. Selects the first element of lists.
	 */
	public void initData() {
		groupList.clear();

		TasksGroupsDao dao = new TasksGroupsDao();
		List<TasksGroups> groups = dao.list(Auth.getUserID());
		groups.stream().forEach(item -> groupList.add(new ListRecord(item.getName(), item.getId())));
		ObservableList<ListRecord> list = FXCollections.observableArrayList(groupList);
		taskgroup.setItems(list);
		if (groups.size() > 0)
			taskgroup.getSelectionModel().select(0);

		priorityList.clear();

		TasksPriorityDao pdao = new TasksPriorityDao();
		List<TasksPriority> priorities = pdao.findAll(TasksPriority.class);
		priorities.stream().forEach(item -> priorityList.add(new ListRecord(item.getName(), item.getId())));
		ObservableList<ListRecord> plist = FXCollections.observableArrayList(priorityList);
		taskpriority.setItems(plist);
		if (priorities.size() > 0)
			taskpriority.getSelectionModel().select(0);

		TasksDao taskdao = new TasksDao();
		Tasks task = taskdao.findById(taskID, Tasks.class);
		if (task != null) {
			taskname.setText(task.getTitle());
			info.setText(task.getText());
			LocalDate dl = task.getDeadline();
			if (dl == null)
				noDeadline.setSelected(true);
			else
				deadline.setValue(dl);

			if (task.getTasksPriority() != null)
				taskpriority
						.setValue(new ListRecord(task.getTasksPriority().getName(), task.getTasksPriority().getId()));
			if (task.getTasksGroups() != null)
				taskgroup.setValue(new ListRecord(task.getTasksGroups().getName(), task.getTasksGroups().getId()));
		} else {
			Alert alert = Alerts.warning("Nem található a teendő!");
			alert.setOnCloseRequest(creq -> {
				Stage stg = (Stage) saveBtn.getScene().getWindow();
				stg.fireEvent(new WindowEvent(stg, WindowEvent.WINDOW_CLOSE_REQUEST));
				stg.close();
			});
			alert.showAndWait().ifPresent(response -> {
				Stage stg = (Stage) saveBtn.getScene().getWindow();
				stg.fireEvent(new WindowEvent(stg, WindowEvent.WINDOW_CLOSE_REQUEST));
				stg.close();
			});
		}
	}

}
