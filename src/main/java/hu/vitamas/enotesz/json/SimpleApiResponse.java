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

package hu.vitamas.enotesz.json;


/**
 * JSON response of simple API requests.
 * 
 * <p>
 * If request is success: <br>
 * <b>Success:</b> {@code true}<br>
 * <b>Errors:</b> {@code null}<br>
 * <b>Status:</b> OK or something like this.
 * </p>
 * <p>
 * If request isn't success: <br>
 * <b>Success:</b> {@code false}<br>
 * <b>Errors:</b> A String object.<br>
 * <b>Status:</b> BAD_PASSWORD or something like this.
 * </p>
 * 
 * @author vitozy
 *
 */
public class SimpleApiResponse {
	String status;
	String errors;
	Boolean success;
	
	/**
	 * Construct.
	 */
	public SimpleApiResponse() {
	}

	/**
	 * Returns the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets up the status.
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the description of errors.
	 * 
	 * @return the errors
	 */
	public String getErrors() {
		return errors;
	}

	/**
	 * Sets up the description of errors.
	 * 
	 * @param errors the errors to set
	 */
	public void setErrors(String errors) {
		this.errors = errors;
	}

	/**
	 * Returns the success status ({@code true}/{@code false}).
	 * 
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * Sets up the success status ({@code true}/{@code false}).
	 * 
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
	
}
