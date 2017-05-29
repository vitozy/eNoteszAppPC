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

import java.time.LocalDate;
import java.time.YearMonth;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Generates Calendar into the Events page.
 * 
 * @author vitozy
 */
public class CalendarView {

	/* Month of Calendar */
	private YearMonth month;
	/* The parent view component of the calendar is a GridPane. */
	private GridPane calendar;
	/* Height and Width of view component. */
	private Double gridHeight, gridWidth;

	/**
	 * Set the parent component of calendar.
	 * 
	 * @param calendar GridPane of the new calendar
	 */
	public CalendarView(GridPane calendar) {
		this.calendar = calendar;
	}

	/**
	 * Set the month of the calendar.
	 * 
	 * @param month YearMonth of the calendar
	 * @return CalendarView
	 */
	public CalendarView setMonth(YearMonth month) {
		this.month = month;
		return this;
	}

	/**
	 * Sets the size of grids in calendar view component.
	 * 
	 * @param width width of the grids
	 * @param height height of the grids
	 * @return the CalendarView instance
	 */
	public CalendarView setGridSize(Double width, Double height) {
		this.gridWidth = width;
		this.gridHeight = height;

		return this;
	}

	/**
	 * Creates the component.
	 * 
	 * @return CalendarView
	 */
	public CalendarView create() {
		LocalDate calendarDate = LocalDate.of(month.getYear(), month.getMonthValue(), 1);
		while (!calendarDate.getDayOfWeek().toString().equals("MONDAY")) {
			calendarDate = calendarDate.minusDays(1);
		}

		// Create rows and columns with anchor panes for the calendar
		boolean currentMonthDay = false;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				AnchorPane day = new AnchorPane();

				if (!day.getChildren().isEmpty())
					day.getChildren().remove(0);
				Integer dayOfMonth = calendarDate.getDayOfMonth();
				String labelTxt = String.valueOf(dayOfMonth);

				if (dayOfMonth == 1)
					currentMonthDay = true;

				if (calendarDate.getMonthValue() > month.getMonthValue())
					currentMonthDay = false;

				day.setPrefSize(gridWidth, gridHeight);

				Label label = new Label(labelTxt);
				label.setPrefWidth(gridWidth);
				label.setPrefHeight(gridHeight);
				label.setAlignment(Pos.CENTER);
				GridPane.setHalignment(label, HPos.CENTER);
				if (currentMonthDay){
					label.setStyle("-fx-font-weight: bold");
				}

				day.getChildren().add(label);
				label.getStyleClass().add("calendar-day");

				String date = calendarDate.toString();

				day.getProperties().put("date", date);

				calendarDate = calendarDate.plusDays(1);

				calendar.add(day, j, i);
			}
		}

		return this;
	}

	/**
	 * Gets the view component.
	 * 
	 * @return GridPane
	 */
	public GridPane getView() {
		return calendar;
	}
}
