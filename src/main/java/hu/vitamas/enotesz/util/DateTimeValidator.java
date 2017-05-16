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

import java.util.regex.Pattern;

/**
 * Validate dates and times.
 * 
 * @author vitozy
 *
 */
public class DateTimeValidator {

	private static final String TIME_PATTERN = "([01][0-9]|2[0-3]):[0-5][0-9]";

	/**
	 * Validates a time string.
	 * 
	 * @param timeStr time to validate
	 * @return is valid or not
	 */
	public static Boolean isValidTime(String timeStr)
	{
		Pattern pattern = Pattern.compile(TIME_PATTERN);
		return pattern.matcher(timeStr).matches();
	}

}
