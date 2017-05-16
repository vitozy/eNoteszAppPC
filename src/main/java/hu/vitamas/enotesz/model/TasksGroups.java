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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model class for "tasks_groups" table in database.
 * 
 * @author vitozy
 */
@Entity
@Table(name = "tasks_groups", catalog = "enotesz")
public class TasksGroups {
	
	private int id;
	private Users users;
	private String name;
	private Set<Tasks> tasks = new HashSet<Tasks>(0);

	/**
	 * Initialize a new instance.
	 */
	public TasksGroups() {
	}

	/**
	 * Initialize a new instance by id.
	 * 
	 * @param id id of group
	 */
	public TasksGroups(int id) {
		this.id = id;
	}

	/**
	 * Initialize a new instance with parameters.
	 * 
	 * @param id id of group
	 * @param users owner user instance
	 * @param name name of group
	 * @param tasks set of tasks
	 */
	public TasksGroups(int id, Users users, String name, Set<Tasks> tasks) {
		this.id = id;
		this.users = users;
		this.name = name;
		this.tasks = tasks;
	}

	/**
	 * Returns with id of group.
	 * 
	 * @return id of group
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the id of group.
	 * 
	 * @param id id of group
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns with the owner user.
	 * 
	 * @return owner user instance
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	public Users getUsers() {
		return this.users;
	}

	/**
	 * Sets the owner user instance.
	 * 
	 * @param users user instance of owner
	 */
	public void setUsers(Users users) {
		this.users = users;
	}

	/**
	 * Returns with group name.
	 * 
	 * @return name of group
	 */
	@Column(name = "name", length = 75)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of group.
	 * 
	 * @param name group name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns with tasks of group.
	 * 
	 * @return set of tasks
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tasksGroups", orphanRemoval=true)
	public Set<Tasks> getTasks() {
		return this.tasks;
	}

	/**
	 * Set the group's tasks.
	 * 
	 * @param tasks set of tasks
	 */
	public void setTasks(Set<Tasks> tasks) {
		this.tasks = tasks;
	}

}
