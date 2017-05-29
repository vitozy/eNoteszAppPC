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

package hu.vitamas.enotesz.view;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class for creating easier a new window.
 * 
 * <p><b>Usage:</b>
 * 
 * <pre>
 * SimpleWindow.create()
 *             .setTitle("Title of window")
 *             .setView("event/add")
 *             .setStyles(Arrays.asList("Styles","eventStyles"))
 *             .open();
 *             
 * SimpleWindow.createOf("Title of window","event/add",Arrays.asList("Styles","eventStyles")).open();
 * 
 * Stage stage = SimpleWindow.create()
 *             .setTitle("Title of window")
 *             .setView("event/add")
 *             .setStyles(Arrays.asList("Styles","eventStyles"))
 *             .toStage();
 * </pre>
 * 
 * @author vitozy
 *
 */
public class SimpleWindow {
	
	static Logger logger = LoggerFactory.getLogger(SimpleWindow.class);
	private String title = "";
	private List<String> styles = null;
	private String viewXML = "";

	/**
	 * Sets the title of new window.
	 * 
	 * @param title title of new window
	 * @return SimpleWindow instance
	 */
	public SimpleWindow setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	/**
	 * Sets FXML file location for the new window.
	 * 
	 * <p>
	 * If we would like to use the "resources/fxml/event/add.fxml" then sets value: "event/add".
	 * 
	 * @param viewXML fxml file of new window
	 * @return SimpleWindow instance
	 */
	public SimpleWindow setView(String viewXML)
	{
		this.viewXML = viewXML;
		return this;
	}
	
	/**
	 * Sets styles list for the new window.
	 * 
	 * <p>It is an option method if we need add styles from code.</p>
	 * <p>Usage:</p>
	 * <pre>setStyles(Arrays.asList("events","panels","buttons"));</pre>
	 * <p>Window will use the styles/events.css, styles/panels.css and styles/buttons.css style sheets.
	 * 
	 * @param list string list of styles
	 * @return SimpleWindow instance
	 */
	public SimpleWindow setStyles(List<String> list)
	{
		this.styles = list;
		return this;
	}
	
	/**
	 * Create a new SimpleWindow and you can chain the methods.
	 * 
	 * @return SimpleWindow instance
	 */
	public static SimpleWindow create()
	{
		return new SimpleWindow();
	}
	
	/**
	 * Create the new SimpleWindow instance with start parameters.
	 * 
	 * @param title title of window
	 * @param viewXML FXML of window  (example: event/add)
	 * @param styles style list of window  (example: {@code Arrays.asList("events","panels","buttons")})
	 * @return SimpleWindow instance
	 */
	public static SimpleWindow createOf(String title, String viewXML, List<String> styles)
	{
		return new SimpleWindow().setTitle(title).setView(viewXML).setStyles(styles);
	}
	
	/**
	 * Create the new SimpleWindow instance with start parameters.
	 * 
	 * @param title title of window
	 * @param viewXML FXML of window  (example: event/add)
	 * @return SimpleWindow instance
	 */
	public static SimpleWindow createOf(String title, String viewXML)
	{
		return new SimpleWindow().setTitle(title).setView(viewXML);
	}
	
	/**
	 * Opens the new window that we just created.
	 */
	public void open()
	{
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + viewXML + ".fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			if(styles != null)
			{
				styles.stream().forEach(css -> scene.getStylesheets().add("/styles/" + css + ".css"));
			}
			;
			stage.setTitle("eNotesz :: " + title);
			stage.getIcons().add(new Image(this.getClass().getResource("/images/logo_icon.png").toString()));
			stage.setScene(scene);
			stage.setResizable(false);
			stage.sizeToScene();
			stage.show();
		} catch (Exception ex) {
			logger.error("Opening SIMPLEWINDOW window", ex);
		}
	}
	
	/**
	 * Returns the Stage instance of SimpleWindow.
	 * 
	 * <p>If we need a complex window creations we can gets the Stage.
	 * 
	 * @return Stage instance of window
	 */
	public Stage toStage()
	{
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + viewXML + ".fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			if(styles != null)
			{
				styles.stream().forEach(css -> scene.getStylesheets().add("/styles/" + css + ".css"));
			}
			;
			stage.setTitle("eNotesz :: " + title);
			stage.getIcons().add(new Image(this.getClass().getResource("/images/logo_icon.png").toString()));
			stage.setScene(scene);
			stage.setResizable(false);
			stage.sizeToScene();
			return stage;
		} catch (Exception ex) {
			logger.error("toStage SIMPLEWINDOW window", ex);
		}
		
		return null;
	}
}
