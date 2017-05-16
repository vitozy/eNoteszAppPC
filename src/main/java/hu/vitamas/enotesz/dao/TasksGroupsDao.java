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

package hu.vitamas.enotesz.dao;

import java.util.List;

import org.hibernate.query.Query;

import hu.vitamas.enotesz.model.TasksGroups;

/**
 * Dao class of TasksGroups model.
 * 
 * @author vitozy
 *
 */
public class TasksGroupsDao extends GenericDao<TasksGroups> {

	/**
	 * Initializes the SessionFactory.
	 */
	public TasksGroupsDao() {
		super();
	}

	/**
	 * Returns list of tasks groups by user id.
	 * 
	 * @param userID id of user
	 * @return list of task groups by user id
	 */
	public List<TasksGroups> list(Integer userID) {
		openSession();
		Query<TasksGroups> q = getSession()
				.createQuery("FROM TasksGroups WHERE user = :user ORDER BY id", TasksGroups.class);
		q.setParameter("user", userID);
		List<TasksGroups> result = q.list();
		closeSession();
		return result;
	}
	
}
