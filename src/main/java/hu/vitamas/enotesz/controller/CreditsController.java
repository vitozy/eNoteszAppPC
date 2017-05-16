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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * Controller of the Help window (help.fxml).
 * 
 * @author vitozy
 *
 */
public class CreditsController implements Initializable {
	
	static Logger logger = LoggerFactory.getLogger(CreditsController.class);
	
    @FXML
    Hyperlink webpageLink;
    
    @FXML
    Hyperlink flaticonLink;
    
    @FXML 
    Hyperlink faLink;
    
    @FXML
    Hyperlink lsfLink;
    
    @FXML
    Hyperlink licenseLink;
    
    @FXML
    Hyperlink sourceLink;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	//open project website
        webpageLink.setOnAction(e -> Core.openWebsite("https://enotesz.vitamas.hu"));
        
        //open Font Awesome (fa) website
        faLink.setOnAction(e -> Core.openWebsite("http://fontawesome.io"));
        
        //open Ligature Symbols (lsf) website
        lsfLink.setOnAction(e -> Core.openWebsite("http://kudakurage.com/ligature_symbols/"));
        
        //open Freepik's FlatIcon site
        flaticonLink.setOnAction(e -> Core.openWebsite("http://www.flaticon.com"));
        
        //open Apache License 2.0 information
        licenseLink.setOnAction(e -> Core.openWebsite("https://www.apache.org/licenses/LICENSE-2.0"));
        
        //open source at Github
        sourceLink.setOnAction(e -> Core.openWebsite("https://github.com/vitozy/enotesz-desktop/"));
    }

}
