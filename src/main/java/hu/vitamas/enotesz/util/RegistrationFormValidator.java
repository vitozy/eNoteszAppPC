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

/**
 * Validating registration form with chaining.
 * 
 * @author vitozy
 *
 */
public class RegistrationFormValidator {
	private Boolean valid;
	
	private RegistrationFormValidator(){
		valid = true;
	}
	
	/**
	 * Returns with new validator instance.
	 * 
	 * @return instance
	 */
	public static RegistrationFormValidator newValidator(){
		return new RegistrationFormValidator();
	}
	
	/**
	 * Checks the name. If not okay then set valid flag to false.
	 * 
	 * <p><b>Server API checks it is unique or not.</b>
	 * 
	 * @param name user's name
	 * @return RegistrationFormValidator
	 */
	public RegistrationFormValidator checkName(String name){
		if(name.length() == 0) valid = false;
		return this;
	}
	
	/**
	 * Checks email. If not okay then set valid flag to false.
	 * 
	 * <p><b>Server API checks it is unique and valid e-mail or not.</b>
	 * 
	 * @param email e-mail address
	 * @return RegistrationFormValidator
	 */
	public RegistrationFormValidator checkEmail(String email){
		if (email.length() == 0) valid = false;
		return this;
	}
	
	/**
	 * Checks passwords. If not okay then set valid flag to false.
	 * 
	 * @param pass password
	 * @param repass password again
	 * @return RegistrationFormValidator
	 */
	public RegistrationFormValidator checkPasswords(String pass, String repass){
		if (pass.length() < 8 || repass.length() < 8 || !pass.equals(repass)) 
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
