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

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import org.hibernate.query.Query;

import hu.vitamas.enotesz.model.Events;

/**
 * Dao class of the Events model.
 * 
 * @author vitozy
 *
 */
public class EventsDao extends GenericDao<Events> {

	/**
	 * Initializes the SessionFactory.
	 */
	public EventsDao() {
		super();
	}

	/**
	 * Returns with upcoming events.
	 * 
	 * @param userID id of current user
	 * @return list of events
	 */
	public List<Events> upcomingEvents(Integer userID) {
		Instant nowInstant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
		
		openSession();
		Query<Events> q = getSession().createQuery("FROM Events WHERE dateTo >= :day and user = :user ORDER BY dateFrom", Events.class);
		q.setParameter("day", Date.from(nowInstant), TemporalType.DATE);
		q.setParameter("user", userID);
		q.setMaxResults(8);
		List<Events> result = q.list();
		closeSession();
		return result;
	}

	/**
	 * Returns with events by specified date.
	 * 
	 * @param day Date of selected day
	 * @param userID id of current user
	 * @return list of events
	 */
	public List<Events> eventsByDay(Date day, Integer userID) {
		openSession();
		Query<Events> q = getSession()
				.createQuery("FROM Events WHERE (dateFrom <= :day and dateTo >= :day) and user = :user", Events.class);
		q.setParameter("day", day, TemporalType.DATE);
		q.setParameter("user", userID);
		List<Events> result = q.list();
		closeSession();
		return result;
	}
}
