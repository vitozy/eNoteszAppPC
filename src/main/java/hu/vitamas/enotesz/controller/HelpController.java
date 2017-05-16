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

package hu.vitamas.enotesz.controller;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.vitamas.enotesz.util.Core;
import hu.vitamas.enotesz.view.SimpleWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * Controller of the Help window (help.fxml).
 * 
 * @author vitozy
 *
 */
public class HelpController implements Initializable {
	
	static Logger logger = LoggerFactory.getLogger(HelpController.class);
	
    @FXML
    Hyperlink webpageLink;
    
    @FXML
    Hyperlink enoteszLink;
    
    @FXML
    Button openHelpWebpageBtn;
    
    @FXML
    Button openCreditsBtn;
    
    @FXML
    Label versionLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	String version = Core.getConfigOf("project.version");
    	versionLabel.setText(version != null ? version : "nincs információ");
    	
        webpageLink.setOnAction(e -> Core.openWebsite("http://www.vitamas.hu"));
        
        enoteszLink.setOnAction(e -> Core.openWebsite("https://enotesz.vitamas.hu"));
        
        openHelpWebpageBtn.setOnMouseClicked(e -> Core.openWebsite("https://enotesz.vitamas.hu/welcome#app"));
        
        openCreditsBtn.setOnMouseClicked(e -> {
        	SimpleWindow.createOf("Credits", "credits").open();
        });
    }

}
