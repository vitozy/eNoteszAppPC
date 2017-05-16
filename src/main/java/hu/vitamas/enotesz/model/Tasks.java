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

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model class for "tasks" table in database.
 * 
 * @author vitozy
 */
@Entity
@Table(name = "tasks", catalog = "enotesz")
public class Tasks {

	private int id;
	private TasksGroups tasksGroups;
	private TasksPriority tasksPriority;
	private String title;
	private String text;
	private LocalDate deadline;
	private Integer completed;

	/**
	 * Initialize a new instance.
	 */
	public Tasks() {
	}

	/**
	 * Initialize a new instance with parameters.
	 * 
	 * @param id id of task
	 * @param tasksGroups group
	 * @param tasksPriority priority
	 * @param title title
	 * @param deadline deadline date
	 * @param checked checked 0 or 1
	 */
	public Tasks(int id, TasksGroups tasksGroups, TasksPriority tasksPriority, String title, LocalDate deadline,
			Integer checked) {
		this.id = id;
		this.tasksGroups = tasksGroups;
		this.tasksPriority = tasksPriority;
		this.title = title;
		this.deadline = deadline;
		this.completed = checked;
	}

	/**
	 * Returns with id.
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the id of task.
	 * 
	 * @param id task's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns with task group instance.
	 * 
	 * @return group
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "groupid")
	public TasksGroups getTasksGroups() {
		return this.tasksGroups;
	}

	/**
	 * Sets the group instance.
	 * 
	 * @param tasksGroups group
	 */
	public void setTasksGroups(TasksGroups tasksGroups) {
		this.tasksGroups = tasksGroups;
	}

	/**
	 * Returns with priority instance.
	 * 
	 * @return priority
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "priority")
	public TasksPriority getTasksPriority() {
		return this.tasksPriority;
	}

	/**
	 * Sets the priority instance.
	 * 
	 * @param tasksPriority priority
	 */
	public void setTasksPriority(TasksPriority tasksPriority) {
		this.tasksPriority = tasksPriority;
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
	 * Sets the title.
	 * 
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns with the text/information.
	 * 
	 * @return text
	 */
	@Column(name = "text")
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text/information.
	 * 
	 * @param text text information
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns with the deadline date.
	 * 
	 * @return deadline date
	 */
	@Column(name = "deadline")
	public LocalDate getDeadline() {
		return this.deadline;
	}

	/**
	 * Sets the deadline date.
	 * 
	 * @param deadline deadline date
	 */
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	/**
	 * Returns with completed flag value.
	 * 
	 * @return completed flag 0 or 1
	 */
	@Column(name = "completed")
	public Integer getCompleted() {
		return this.completed;
	}

	/**
	 * Sets the completed flag value.
	 * 
	 * @param completed flag with value 0 or 1
	 */
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

}
