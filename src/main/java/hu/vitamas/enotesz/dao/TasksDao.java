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
import hu.vitamas.enotesz.model.Tasks;

/**
 * Dao class of Tasks model.
 * 
 * @author vitozy
 *
 */
public class TasksDao extends GenericDao<Tasks> {

	/**
	 * Initializes the SessionFactory.
	 */
	public TasksDao() {
		super();
	}
	
	/**
	 * Returns with tasks of user that contains deadline.
	 * 
	 * @param userID id of current user
	 * @return list of tasks
	 */
	public List<Tasks> deadlineTasks(Integer userID) {
		openSession();
		Query<Tasks> q = getSession().createQuery("FROM Tasks WHERE deadline is not null and tasksGroups.users.userid = :user", Tasks.class);
		q.setParameter("user", userID);
		q.setMaxResults(8);
		List<Tasks> result = q.list();
		closeSession();
		return result;
	}
	
	/**
	 * Returns with tasks of user that has "Fontos" (important) or "Sürgős" (urgent) importance.
	 * 
	 * @param userID id of current user
	 * @return list of tasks
	 */
	public List<Tasks> importantTasks(Integer userID) {
		openSession();
		Query<Tasks> q = getSession().createQuery("FROM Tasks WHERE (tasksPriority.name = 'Fontos' or tasksPriority.name = 'Sürgős') and tasksGroups.users.userid = :user", Tasks.class);
		q.setParameter("user", userID);
		q.setMaxResults(8);
		List<Tasks> result = q.list();
		closeSession();
		return result;
	}
}
