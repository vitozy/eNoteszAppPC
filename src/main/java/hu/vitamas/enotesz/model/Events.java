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

package hu.vitamas.enotesz.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model class for "events" table in database.
 * 
 * @author vitozy
 */
@Entity
@Table(name = "events", catalog = "enotesz")
public class Events {
	
	private Integer id;
	private Users users;
	private String title;
	private String text;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private LocalTime timeFrom;
	private LocalTime timeTo;
	private String place;

	/**
	 * Initialize a new instance.
	 */
	public Events() {
	}

	/**
	 * Initialize a new instance with parameters.
	 * 
	 * @param users users object
	 * @param title title
	 * @param text text information
	 * @param dateFrom starting date
	 * @param dateTo ending date
	 * @param timeFrom starting time
	 * @param timeTo ending time
	 * @param place place
	 */
	public Events(Users users, String title, String text, LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo,
			String place) {
		this.users = users;
		this.title = title;
		this.text = text;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.place = place;
	}

	/**
	 * Returns with id.
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id id of event
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns with owner user.
	 * 
	 * @return owner user
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	
	/**
	 * Sets the owner user.
	 * 
	 * @param users owner
	 */
	public void setUsers(Users users) {
		this.users = users;
	}

	/**
	 * Returns with title.
	 * 
	 * @return title
	 */
	@Column(name = "title", length = 100)
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title of event.
	 * 
	 * @param title event title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns with the text of HTML information.
	 * 
	 * @return html information
	 */
	@Column(name = "text", length = 65535)
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text of HTML information.
	 * 
	 * @param text the text of HTML information
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns with starting date.
	 * 
	 * @return starting date
	 */
	@Column(name = "date_from", length = 10)
	public LocalDate getDateFrom() {
		return this.dateFrom;
	}

	/**
	 * Sets the date of start.
	 * 
	 * @param dateFrom starting date
	 */
	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * Returns with the end date.
	 * 
	 * @return end date
	 */
	@Column(name = "date_to", length = 10)
	public LocalDate getDateTo() {
		return this.dateTo;
	}

	/**
	 * Sets the end date.
	 * 
	 * @param dateTo end date
	 */
	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * Returns with the start time.
	 * 
	 * @return time of starting
	 */
	@Column(name = "time_from", length = 8)
	public LocalTime getTimeFrom() {
		return this.timeFrom;
	}

	/**
	 * Sets the start time.
	 * 
	 * @param timeFrom starting time
	 */
	public void setTimeFrom(LocalTime timeFrom) {
		this.timeFrom = timeFrom;
	}

	/**
	 * Returns with the time of ending.
	 * 
	 * @return end time
	 */
	@Column(name = "time_to", length = 8)
	public LocalTime getTimeTo() {
		return this.timeTo;
	}

	/**
	 * Sets the time of ending.
	 * 
	 * @param timeTo time of ending
	 */
	public void setTimeTo(LocalTime timeTo) {
		this.timeTo = timeTo;
	}

	/**
	 * Returns with place.
	 * 
	 * @return place
	 */
	@Column(name = "place", length = 100)
	public String getPlace() {
		return this.place;
	}

	/**
	 * Sets the place.
	 * 
	 * @param place place of event
	 */
	public void setPlace(String place) {
		this.place = place;
	}

}
