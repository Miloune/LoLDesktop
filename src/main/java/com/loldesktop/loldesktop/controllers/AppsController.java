/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.loldesktop.controllers;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;
import com.loldesktop.chatapi.ChatAPI;
import com.loldesktop.loldesktop.UserSingleton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * FXML Controller class
 *
 * @author Miloune
 */
public class AppsController implements Initializable {

    @FXML // fx:id="friendTreeView"
    private TreeView<String> friendTreeView;
    
    private ChatAPI chatAPI;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chatAPI = UserSingleton.getUserSingleton().getChatAPI();
        initializeTreeView();
    }    
    
    public void initializeTreeView() {
        List<Friend> allFriends = chatAPI.getAllOnlineFriends();
        TreeItem<String> rootNode = new TreeItem<>("All friends");
        
        rootNode.setExpanded(true);
        for(Friend friend : allFriends) {
            TreeItem<String> frd = new TreeItem<>(friend.getName());
            boolean found = false;
            for(TreeItem<String> grpNode : rootNode.getChildren()) {
                if(grpNode.getValue().contentEquals(friend.getGroup().getName())) {
                    grpNode.getChildren().add(frd);
                    found = true;
                    break;
                }
            }
            if(!found) {
                TreeItem<String> grpNode = new TreeItem<>(friend.getGroup().getName());
                rootNode.getChildren().add(grpNode);
                grpNode.getChildren().add(frd);
            }
        }
        
        friendTreeView.setRoot(rootNode);
        friendTreeView.setShowRoot(false);
    }
}
