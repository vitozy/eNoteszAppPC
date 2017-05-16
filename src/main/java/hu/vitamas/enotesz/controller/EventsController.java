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
import java.time.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.vitamas.enotesz.dao.EventsDao;
import hu.vitamas.enotesz.model.Events;
import hu.vitamas.enotesz.model.ListRecord;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.view.Alerts;
import hu.vitamas.enotesz.view.CalendarView;
import hu.vitamas.enotesz.view.SimpleWindow;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Controller class of the Main windows' Events tab (include/events.fxml).
 * 
 * @author vitozy
 *
 */
public class EventsController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(EventsController.class);
	
	@FXML
	private GridPane calendar;
	@FXML
	private TitledPane jumpToDayBox;
	@FXML
	private Button jumpToDayButton;
	@FXML
	private DatePicker jumpToDayInput;
	@FXML
	private TitledPane calendarBox;
	@FXML
	private AnchorPane eventsList;

	static LocalDate currentDate;
	ArrayList<Integer> eventIDs = new ArrayList<>();
	ArrayList<String> eventTitles = new ArrayList<>();

	ArrayList<ListRecord> eventList = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// calendar init
		CalendarView cv = new CalendarView(this.calendar);
		GridPane cal = cv.setMonth(YearMonth.now()).setGridSize(50.0, 50.0).create().getView();
		cal.getChildren().stream().forEach((node) -> {
			node.setOnMouseClicked(this::calendarOnClick);
		});

		currentDate = LocalDate.now();

		setEventsPageTitles();

		this.calendar = cal;
		
		jumpToDayButton.setOnMouseClicked(this::jumpToDay);
	}
	
	/**
	 * Initializes the data of scene.
	 * 
	 * <p>Sets up event list.
	 */
	public void initData()
	{
		setEventsList();
	}
	
	private void jumpToDay(MouseEvent e)
	{
		LocalDate ld = jumpToDayInput.getValue();
		if(ld != null) {
			currentDate = ld;
			setEventsList();
			setEventsPageTitles();
		}
	}

	private void setEventsPageTitles() {
		Integer year = currentDate.getYear();
		Integer day = currentDate.getDayOfMonth();
		String month = currentDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("hu"));

		calendarBox.setText("Napt�r - " + year + ". " + month);

		String calboxStr = (new StringBuilder()).append("Nap kiválasztása - aktuális: ").append(year).append(". ")
				.append(month).append(" ").append(day).append(".").toString();

		jumpToDayBox.setText(calboxStr);
	}

	/**
	 * Jumps to the previous month. Button click event.
	 * 
	 * @param e event of mouse click
	 */
	public void prevMonthClick(MouseEvent e) {
		LocalDate prevMonth = currentDate.minusMonths(1).withDayOfMonth(1);
		YearMonth ym = YearMonth.of(prevMonth.getYear(), prevMonth.getMonth());
		currentDate = prevMonth;
		setEventsList();
		setEventsPageTitles();

		calendar.getChildren().clear();

		CalendarView cv = new CalendarView(this.calendar);
		GridPane cal = cv.setMonth(ym).setGridSize(50.0, 50.0).create().getView();
		cal.getChildren().stream().forEach((node) -> {
			node.setOnMouseClicked(this::calendarOnClick);
		});
	}

	/**
	 * Jumps to the next month. Button click event.
	 * 
	 * @param e event of mouse click
	 */
	public void nextMonthClick(MouseEvent e) {
		LocalDate nextMonth = currentDate.plusMonths(1).withDayOfMonth(1);
		YearMonth ym = YearMonth.of(nextMonth.getYear(), nextMonth.getMonth());
		currentDate = nextMonth;
		setEventsList();
		setEventsPageTitles();

		calendar.getChildren().clear();

		CalendarView cv = new CalendarView(this.calendar);
		GridPane cal = cv.setMonth(ym).setGridSize(50.0, 50.0).create().getView();
		cal.getChildren().stream().forEach((node) -> {
			node.setOnMouseClicked(this::calendarOnClick);
		});
	}

	/**
	 * Jumps to the current month. Button click event.
	 * 
	 * @param e event of mouse click
	 */
	public void currentMonthClick(MouseEvent e) {
		if (!currentDate.isEqual(LocalDate.now())) {
			LocalDate currMonth = LocalDate.now();
			YearMonth ym = YearMonth.of(currMonth.getYear(), currMonth.getMonth());
			currentDate = currMonth;
			setEventsList();
			setEventsPageTitles();

			calendar.getChildren().clear();

			CalendarView cv = new CalendarView(this.calendar);
			GridPane cal = cv.setMonth(ym).setGridSize(50.0, 50.0).create().getView();
			cal.getChildren().stream().forEach((node) -> {
				node.setOnMouseClicked(this::calendarOnClick);
			});
		}
	}
	
	/**
	 * Refresh the scene's data.
	 * 
	 * @param e event of mouse click
	 */
	public void refresh(MouseEvent e) {
		setEventsList();
	}
	
	/**
	 * Opens the event/add window.
	 * 
	 * @param e event of mouse click
	 */
	public void addEvent(MouseEvent e) {
		SimpleWindow.createOf("Esemény létrehozása", "event/add").open();
	}

	/**
	 * Jumps to a day in the month. Button click event.
	 * 
	 * @param e event of mouse click
	 */
	public void calendarOnClick(MouseEvent e) {
		Node node = (Node) e.getSource();
		String dateStr = (String) node.getProperties().get("date");

		currentDate = LocalDate.parse(dateStr);
		
		setEventsList();
		setEventsPageTitles();
	}
	
	private void setEventsList()
	{
		eventList.clear();
		
		EventsDao dao = new EventsDao();
		Date day = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<Events> events = dao.eventsByDay(day, Auth.getUserID());
		events.stream().forEach(event -> {
			String title = event.getDateFrom() + " - " + event.getTitle();
			eventList.add(new ListRecord(title, event.getId()));
		});
		
		final ListView<ListRecord> listView = new ListView<>();
		ObservableList<ListRecord> list = FXCollections.observableArrayList(eventList);
		listView.setItems(list);

		listView.setOnMouseClicked(event -> {

			Integer id = listView.getSelectionModel().getSelectedIndex();
			listView.getSelectionModel().clearSelection();
			
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
							
							Scene scene = new Scene(root);
							Stage stage = new Stage();
							stage.setTitle("eNotesz :: Esemény megtekintése");
							stage.getIcons().add(new Image("/images/logo_icon.png"));
							stage.setScene(scene);
							
							stage.addEventHandler(WindowEvent.WINDOW_SHOWING, e -> controller.initData());
							stage.show();
						} catch (Exception ex) {
							logger.error("Event view window open failed", ex);
						}
					} else {
						Alerts.error("Sikertelen adatlekérés!").showAndWait();
					}
				}
			}
		});

		eventsList.getChildren().clear();
		eventsList.getChildren().add(listView);
		listView.prefWidthProperty().bind(eventsList.widthProperty());
		listView.prefHeightProperty().bind(eventsList.heightProperty());
	}
}
