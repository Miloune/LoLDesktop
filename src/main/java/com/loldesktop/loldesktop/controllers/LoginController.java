/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loldesktop.loldesktop.controllers;

import com.loldesktop.chatapi.ChatAPI;
import com.loldesktop.loldesktop.ParametersSingleton;
import com.loldesktop.loldesktop.UserSingleton;
import com.loldesktop.riotapi.RiotAPIServer;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Miloune
 */
public class LoginController implements Initializable {

    @FXML // fx:id="choiceServer"
    private ComboBox<String> choiceServer;

    @FXML // fx:id="login"
    private Button login;

    @FXML // fx:id="username"
    private TextField username;

    @FXML // fx:id="password"
    private PasswordField password;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadServer();

        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                loginUserSingleton();
            }
        });
    }

    private void loadServer() {
        Set<String> stringCollection = new HashSet<>();
        
        for (Enum c : RiotAPIServer.values()) {
            stringCollection.add(c.toString());
        }
        
        Set<String> treeSet = new TreeSet<>(stringCollection);
        treeSet.iterator();
        
        choiceServer.setVisibleRowCount(5);
        choiceServer.getItems().setAll(treeSet);
        choiceServer.getSelectionModel().selectFirst();
    }

    private void loginUserSingleton() {
        /*
         * User connected = true ? Assings data to UserSingleton
         */
        ChatAPI chatAPI = new ChatAPI(ParametersSingleton.getParametersSingleton().getApiKey(), choiceServer.getValue());
        if(chatAPI.loginChat(username.getText(), password.getText())) {
            UserSingleton.getUserSingleton().setUsername(username.getText());
            UserSingleton.getUserSingleton().setPassword(password.getText());
            UserSingleton.getUserSingleton().setRegion(choiceServer.getValue());
            System.out.println("Connected");
        } else {
            System.out.println("Connection error");
        }
        
        
    }
    
    @FXML
    private void handleUsernamePressed(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                loginUserSingleton();
            }
    }
    
    @FXML
    private void handlePasswordPressed(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                loginUserSingleton();
            }
    }
}
