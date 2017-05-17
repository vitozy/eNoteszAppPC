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

import hu.vitamas.enotesz.dao.EventsDao;
import hu.vitamas.enotesz.model.Events;
import hu.vitamas.enotesz.view.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class of the event/view.fxml.
 * 
 * @author vitozy
 *
 */
public class EventController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(EventController.class);
	
	private Integer eventID = 0;

	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private Label eventViewTitle;
	@FXML
	private TitledPane eventText;
	@FXML
	private WebView eventTextBody;
	@FXML
	private TitledPane eventInfo;
	@FXML
	private Label eventInfoPlace;
	@FXML
	private Label eventInfoFrom;
	@FXML
	private Label eventInfoTo;
	@FXML
	private Button eventEdit;
	@FXML
	private Button eventDelete;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		eventEdit.setOnMouseClicked(this::openEdit);
		eventDelete.setOnMouseClicked(this::delete);
	}

	private void openEdit(MouseEvent e) {
		EventsDao dao = new EventsDao();
		Events ev = dao.findById(this.eventID, Events.class);
		if (ev != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/event/edit.fxml"));
				Parent root = (Parent) fxmlLoader.load();

				EditEventController controller = fxmlLoader.<EditEventController>getController();
				controller.setID(this.eventID);

				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setTitle("eNotesz :: Esemény megtekintése");
				stage.getIcons().add(new Image("/images/logo_icon.png"));
				stage.setScene(scene);
				stage.setOnCloseRequest(closeEvent -> initData());
				stage.addEventHandler(WindowEvent.WINDOW_SHOWING, winEvent -> controller.initData());
				stage.setResizable(false);
				stage.sizeToScene();
				stage.show();

			} catch (Exception ex) {
				logger.error("Event opening failed", ex);
			}
		} else {
			Alerts.error("Sikertelen adatlek?r?s!").showAndWait();
		}
	}

	private void delete(MouseEvent e) {
		
		EventsDao dao = new EventsDao();
		Events ev = dao.findById(this.eventID, Events.class);
		if (ev != null) {
			try {
				dao.delete(ev);
				
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
	
	/**
	 * Sets up the ID of the event we need to view.
	 * 
	 * @param id ID of event
	 */
	public void setID(Integer id) {
		this.eventID = id;
	}

	/**
	 * Initializes the data of the scene.
	 * 
	 * <p>Puts event data.
	 */
	public void initData() {
		EventsDao dao = new EventsDao();
		Events ev = dao.findById(eventID, Events.class);
		if (ev != null) {
			eventViewTitle.setText("Esemény: " + ev.getTitle());

			eventInfoPlace.setText(ev.getPlace());
			eventInfoFrom.setText(ev.getDateFrom().toString() + " " + ev.getTimeFrom().toString());
			eventInfoTo.setText(ev.getDateTo().toString() + " " + ev.getTimeTo().toString());

			final WebEngine webEngine = eventTextBody.getEngine();
			webEngine.loadContent(ev.getText());
		}
	}
}
