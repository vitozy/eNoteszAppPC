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
 * JSON response of auth API request.
 * 
 * <p>
 * If request is success: <br>
 * <b>Status code:</b> 200<br>
 * <b>User ID:</b> A number.
 * </p>
 * <p>
 * If request isn't success: <br>
 * <b>Status code:</b> 4xx or 5xx<br>
 * <b>User ID:</b> {@code null}
 * </p>
 * 
 * @author vitozy
 *
 */
public class AuthResponse {
	String status;
	Integer userid;
	
	/**
	 * Construct.
	 */
	public AuthResponse() {
	}
	
	/**
	 * Returns with status code.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets up the status code.
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Returns with user id.
	 * 
	 * @return the userID
	 */
	public Integer getUserid() {
		return userid;
	}
	
	/**
	 * Sets up user id.
	 * 
	 * @param userid the userID to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
}
