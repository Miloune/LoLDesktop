/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loldesktop.loldesktop.controllers;

import com.github.theholywaffle.lolchatapi.ChatMode;
import com.loldesktop.chatapi.ChatAPI;
import com.loldesktop.loldesktop.MainApp;
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
import org.controlsfx.dialog.Dialogs;

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
    
    private MainApp mainApp;

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

    /**
     * Load the server list and sort by alphabetical order
     */
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

    /**
     * Create User singleton
     */
    private void loginUserSingleton() {
        /*
         * User connected = true ? Assings data to UserSingleton
         */
        ChatAPI chatAPI = new ChatAPI(ParametersSingleton.getParametersSingleton().getApiKey(), choiceServer.getValue());
        if(chatAPI.loginChat(username.getText(), password.getText())) {
            UserSingleton.getUserSingleton().setUsername(username.getText());
            UserSingleton.getUserSingleton().setPassword(password.getText());
            UserSingleton.getUserSingleton().setRegion(choiceServer.getValue());
            UserSingleton.getUserSingleton().setChatAPI(chatAPI);
            UserSingleton.getUserSingleton().setConnected(true);
            
            chatAPI.setChatMode(ChatMode.AVAILABLE);
            System.out.println("Connected");
            mainApp.showAppsOverview();
        } else {
            chatAPI.disconnectChat(); // Close connection
            Dialogs.create().title("Unable to connect").masthead(null).message("Please, verify your login information and your selected server").showWarning();
        }
        
        
    }
    
    /**
     * Get key event on Username
     * ENTER : loginUser
     * @param event 
     */
    @FXML
    private void handleUsernamePressed(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                loginUserSingleton();
            }
    }
    
    /**
     * Get key event on Password
     * ENTER : loginUser
     * @param event 
     */
    @FXML
    private void handlePasswordPressed(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                loginUserSingleton();
            }
    }

    /**
     * Get key event on Select Server
     * ENTER : loginUser
     * @param event 
     */
    @FXML
    private void handleChoiceServerPressed(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                loginUserSingleton();
            }
    }
    
    /**
     * Get key event on button Login
     * ENTER : loginUser
     * @param event 
     */
    @FXML
    private void handleLoginPressed(KeyEvent event) {
            if(event.getCode() == KeyCode.ENTER) {
                loginUserSingleton();
            }
    }  
    
    /**
     * Is called by the main app to give a reference back to itself.
     * @param aThis 
     */
    public void setMainApp(MainApp aThis) {
        this.mainApp = aThis;
    }
}
