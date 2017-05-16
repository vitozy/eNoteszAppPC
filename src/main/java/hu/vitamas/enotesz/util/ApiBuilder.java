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

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Building API request with chaining methods.
 * 
 * @author vitozy
 *
 */
public class ApiBuilder {
	private String params = "";
	private String url = "";
	private String method = "";
	
	
	static Logger logger = LoggerFactory.getLogger(ApiBuilder.class);
	
	/**
	 * Initialize new instance.
	 */
	public ApiBuilder(){}
	
	/**
	 * Sets URL parameters by a {@literal "&"} concatenated string.
	 * 
	 * @param params url parameters
	 * @return ApiBuilder
	 */
	public ApiBuilder setParams(String params){
		this.params = params;
		return this;
	}
	
	/**
	 * Sets URL parameters by a HashMap.
	 * 
	 * @param map hashmap of parameters
	 * @return ApiBuilder
	 */
	public ApiBuilder setParams(HashMap<String,String> map){
		ArrayList<String> list = new ArrayList<>();
		
		for(Map.Entry<String, String> entry : map.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    list.add(key + "=" + value);
		}
		
		this.params = String.join("&", list);
		return this;
	}
	
	/**
	 * Sets URL of request.
	 * 
	 * @param url URL
	 * @return ApiBuilder
	 */
	public ApiBuilder setUrl(String url){
		this.url = url;
		return this;
	}
	
	/**
	 * Sets the method of request. POST, GET, PUT, etc.
	 * 
	 * @param method method of request
	 * @return ApiBuilder
	 */
	public ApiBuilder setMethod(String method){
		this.method = method;
		return this;
	}
	
	/**
	 * Sends the request to the server.
	 * 
	 * @return response of server
	 */
	public String sendRequest(){
		try {
			URL url = new URL(this.url);
			
			byte[] postData = this.params.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod(this.method);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setUseCaches(false);
			
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(postData);
			}
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
			
			return IOUtils.toString(conn.getInputStream(), "UTF-8");
		} catch (Exception e) {}
		
		return null;
	}
}
