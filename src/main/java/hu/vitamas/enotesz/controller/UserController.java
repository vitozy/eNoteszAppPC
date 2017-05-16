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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import hu.vitamas.enotesz.dao.UsersDao;
import hu.vitamas.enotesz.model.Users;
import hu.vitamas.enotesz.util.Auth;
import hu.vitamas.enotesz.view.Alerts;
import hu.vitamas.enotesz.view.SimpleWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

/**
 * Controller of the Account/User information window (user/user.fxml).
 * 
 * @author vitozy
 *
 */
public class UserController implements Initializable {

	static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@FXML
    private Button changeDetail;

    @FXML
    private Label userEmail;

    @FXML
    private Label userName;

    @FXML
    private Label userID;

    @FXML
    private Button changePassword;
    
    @FXML
    private Button refresh;
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	 getDatas();
    	 
    	 changeDetail.setOnMouseClicked(this::detailchange);
         changePassword.setOnMouseClicked(this::passchange);
         refresh.setOnMouseClicked(e -> getDatas());
    }

    private void detailchange(MouseEvent e) {
    	SimpleWindow.createOf("Fiók adatok módosítása", "user/detailchange").open();
    }
    
    private void passchange(MouseEvent e) {
    	SimpleWindow.createOf("Fiók jelszó módosítása", "user/passchange").open();
    }
    
    private void getDatas()
    {
    	try {
   		 UsersDao usersDao = new UsersDao();
   		 Users user = usersDao.findById(Auth.getUserID(), Users.class);
            
            userID.setText(user.getUserid().toString());
            userEmail.setText(user.getEmail());
            userName.setText(user.getName());
            
        } catch (Exception e) {
			 logger.error("User data error -- session", e);
			
       	 Alerts.error("Sikertelen adatlekérés!").showAndWait();
        }
    }
}
