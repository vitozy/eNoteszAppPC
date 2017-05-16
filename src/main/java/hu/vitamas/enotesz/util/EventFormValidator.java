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

package hu.vitamas.enotesz.util;

import java.time.LocalDate;

/**
 * Validating event add and edit forms with chaining.
 * 
 * @author vitozy
 *
 */
public class EventFormValidator {
	private Boolean valid;
	
	private EventFormValidator(){
		valid = true;
	}
	
	/**
	 * Returns with new validator instance.
	 * 
	 * @return instance
	 */
	public static EventFormValidator newValidator(){
		return new EventFormValidator();
	}
	
	/**
	 * Checks the name. If not okay then set valid flag to false.
	 * 
	 * @param name name to validate
	 * @return EventFormValidator
	 */
	public EventFormValidator checkName(String name){
		if(name.length() == 0) valid = false;
		return this;
	}
	
	/**
	 * Checks dates. If dates are null or second dates is before then set valid flag to false.
	 * 
	 * @param from start date to validate
	 * @param to end date to validate
	 * @return EventFormValidator
	 */
	public EventFormValidator checkDates(LocalDate from, LocalDate to){
		if (from == null || to == null || from.isAfter(to)) valid = false;
		return this;
	}
	
	/**
	 * Checks times. If not okay then set valid flag to false.
	 * 
	 * <p>This method uses the {@link EventFormValidator EventFromValidator}
	 * 
	 * @param from start time to validate
	 * @param to start time to validate
	 * @return EventFormValidator
	 */
	public EventFormValidator checkTimes(String from, String to){
		if (from.length() == 0 || !DateTimeValidator.isValidTime(from) || 
				to.length() == 0 || !DateTimeValidator.isValidTime(to)) 
			valid = false;
		return this;
	}
	
	/**
	 * It is valid or not.
	 * 
	 * @return valid or not
	 */
	public Boolean isValid(){
		return valid;
	}
}
