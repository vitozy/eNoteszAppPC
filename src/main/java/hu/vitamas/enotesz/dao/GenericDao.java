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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hu.vitamas.enotesz.util.HibernateUtil;

/**
 * Generic dao of Dao classes.
 * 
 * @author vitozy
 * @param <T> class name
 */
public abstract class GenericDao<T> {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	/**
	 * Initializes the session factory instance.
	 */
	public GenericDao()
	{
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	/**
	 * Finds an entity by its ID.
	 * 
	 * @param id ID of the entity
	 * @param clazz class of the entity
	 * @return entity
	 */
	public T findById(Integer id, Class<T> clazz) {
		openSession();
		T result = (T) getSession().get(clazz, id);
		closeSession();
		return result;
	}

	/**
	 * Returns with all entities.
	 * 
	 * @param clazz class of the entity
	 * @return list of entities
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> clazz) {
		openSession();
		List<T> result = getSession().createQuery("from " + clazz.getName()).list();
		closeSession();
		return result;
	}

	/**
	 * Creates a new entity into database by entity object.
	 * 
	 * @param entity entity object we need to create
	 */
	public void create(T entity) {
		openTransaction();
		getSession().persist(entity);
		closeTransaction();
	}

	/**
	 * Updates an entity in database by entity object.
	 * 
	 * @param entity entity object we need to update
	 */
	public void update(T entity) {
		openTransaction();
		getSession().merge(entity);
		closeTransaction();
	}

	/**
	 * Deletes an entity from database by entity object.
	 * 
	 * @param entity entity object we need to delete
	 */
	public void delete(T entity) {
		openTransaction();
		getSession().delete(entity);
		closeTransaction();
	}

	/**
	 * Deletes an entity from database by id of entity.
	 * 
	 * @param entityId entity id we need to delete
	 * @param clazz class of the entity
	 */
	public void deleteById(Integer entityId, Class<T> clazz) {
		T entity = findById(entityId, clazz);
		delete(entity);
	}

	/**
	 * Returns with current session factory instance.
	 * 
	 * @return SessionFactory instance
	 */
	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
	
	/**
	 * Opens a new session.
	 */
	public void openSession() {
		session = getSessionFactory().openSession();
	}
	
	/**
	 * Closes the session.
	 */
	public void closeSession() {
		session.close();
	}
	
	/**
	 * Returns with current session instance.
	 * 
	 * @return Session instance
	 */
	public Session getSession()
	{
		return session;
	}
	
	/**
	 * Opens a new transaction.
	 */
	public void openTransaction() {
		openSession();
		transaction = getSession().beginTransaction();
	}
	
	/**
	 * Closes current transaction.
	 */
	public void closeTransaction() {
		getTransaction().commit();
		closeSession();
	}
	
	/**
	 * Returns with current transaction instance.
	 * 
	 * @return Transaction instance
	 */
	public Transaction getTransaction()
	{
		return transaction;
	}
}
