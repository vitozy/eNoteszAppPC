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

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model class for "users" table in database.
 * 
 * @author vitozy
 */
@Entity
@Table(name = "users", catalog = "enotesz")
public class Users {

	private Integer userid;
	private String name;
	private String email;
	private String password;
	private Set<Events> events = new HashSet<Events>(0);
	private Set<TasksGroups> tasksGroups = new HashSet<TasksGroups>(0);
	private Integer dailyreport;

	/**
	 * Initializes new Users instance.
	 */
	public Users() {
	}

	/**
	 * Initializes new Users instance with parameters.
	 * 
	 * @param name name of user
	 * @param email email of user
	 * @param password password of user
	 * @param events events of user
	 * @param tasksGroups Set of task groups of user
	 */
	public Users(String name, String email, String password, Set<Events> events, Set<TasksGroups> tasksGroups) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.events = events;
		this.tasksGroups = tasksGroups;
	}

	/**
	 * Returns with user id.
	 * 
	 * @return user id
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userid", unique = true, nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	/**
	 * Sets user id.
	 * 
	 * @param userid user id
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	/**
	 * Returns with user name.
	 * 
	 * @return user name
	 */
	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	/**
	 * Set user name.
	 * 
	 * @param name user name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns with user's email.
	 * 
	 * @return email of user
	 */
	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set user's email.
	 * 
	 * @param email email of user
	 */ 
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns with user's encrypted password by server's program.
	 * 
	 * @return password
	 */
	@Column(name = "password", length = 75)
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets user's password with encryption by server's program.
	 * 
	 * <p>Please do not use without the server encryption method.
	 * 
	 * @param password encrypted password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the set of user's events.
	 * 
	 * @return set of events
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "users")
	public Set<Events> getEvents() {
		return this.events;
	}

	/**
	 * Sets user's events set.
	 * 
	 * @param events set of events
	 */
	public void setEvents(Set<Events> events) {
		this.events = events;
	}

	/**
	 * Returns the set of user's task groups.
	 * 
	 * @return set of task groups
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "users")
	public Set<TasksGroups> getTasksGroups() {
		return this.tasksGroups;
	}

	/**
	 * Sets the set of user's task groups.
	 * 
	 * @param tasksGroups set of task groups
	 */
	public void setTasksGroups(Set<TasksGroups> tasksGroups) {
		this.tasksGroups = tasksGroups;
	}

	/**
	 * Returns with daily report flag: 0 or 1. If 1 then the user sets up it.
	 * 
	 * @return daily report flag
	 */
	@Column(name = "dailyreport")
	public Integer getDailyReport() {
		return this.dailyreport;
	}

	/**
	 * Sets daily report flag: 0 or 1.
	 * 
	 * @param flag ask the daily report service
	 */
	public void setDailyReport(Integer flag) {
		this.dailyreport = flag;
	}
}
