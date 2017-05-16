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

/**
 * ListView record of model entity by id and title.
 * 
 * @author vitozy
 *
 */
public class ListRecord {
	private String title = "";
	private Integer id = 0;

	/**
	 * Initialize.
	 * 
	 * @param title title
	 * @param id entity id
	 */
	public ListRecord(String title, Integer id) {
		this.title = title;
		this.id = id;
	}

	/**
	 * Gets the entity's id.
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Returns with entity's title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * To string: gets title.
	 */
	@Override
	public String toString() {
		return getTitle();
	}
}
