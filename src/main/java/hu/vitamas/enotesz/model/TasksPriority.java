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

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model class for "tasks_priority" table in database.
 * 
 * @author vitozy
 */
@Entity
@Table(name = "tasks_priority", catalog = "enotesz")
public class TasksPriority {

	private int id;
	private String name;
	private Set<Tasks> tasks = new HashSet<Tasks>(0);

	/**
	 * Initialize a new instance.
	 */
	public TasksPriority() {
	}

	/**
	 * Initialize a new instance by id.
	 * 
	 * @param id id of task priority
	 */
	public TasksPriority(int id) {
		this.id = id;
	}

	/**
	 * Initialize a new instance with parameters.
	 * 
	 * @param id id of task priority
	 * @param name name of priory
	 * @param tasks set of tasks
	 */
	public TasksPriority(int id, String name, Set<Tasks> tasks) {
		this.id = id;
		this.name = name;
		this.tasks = tasks;
	}

	/**
	 * Returns with id of priority.
	 * 
	 * @return id of priority
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the id of priority.
	 * 
	 * @param id id of priority
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns with the name of priority.
	 * 
	 * @return name of priority
	 */
	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the priority.
	 * 
	 * @param name name of priority
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the set of tasks.
	 * 
	 * @return set of tasks
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tasksPriority")
	public Set<Tasks> getTasks() {
		return this.tasks;
	}

	/**
	 * Sets the set of tasks.
	 * 
	 * @param tasks tasks' set by priority
	 */
	public void setTasks(Set<Tasks> tasks) {
		this.tasks = tasks;
	}

}
