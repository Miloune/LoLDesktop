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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Miloune
 */
public class AppsController implements Initializable {

    @FXML // fx:id="friendTreeView"
    private TreeView<String> friendTreeView;

    @FXML // fx:id="chatTabPane"
    private TabPane chatTabPane;

    private ChatAPI chatAPI;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chatAPI = UserSingleton.getUserSingleton().getChatAPI();
        initializeTreeView();
        initializeTabPane();
    }

    /**
     * Initialize friend TreeView
     * Event : Double click for open tab
     * TODO : Desactivate event for friend group
     * TODO : Implement listener for friend loggin / disconnect
     */
    public void initializeTreeView() {
        friendTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        addTabPane(friendTreeView.getSelectionModel().getSelectedItem().getValue());
                    }
                }
            }
        });
        List<Friend> allFriends = chatAPI.getAllOnlineFriends();
        TreeItem<String> rootNode = new TreeItem<>("All friends");

        for (final Friend friend : allFriends) {
            TreeItem<String> frd = new TreeItem<>(friend.getName());
            boolean found = false;
            for (TreeItem<String> grpNode : rootNode.getChildren()) {
                if (grpNode.getValue().contentEquals(friend.getGroup().getName())) {
                    grpNode.getChildren().add(frd);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> grpNode = new TreeItem<>(friend.getGroup().getName());
                rootNode.getChildren().add(grpNode);
                grpNode.getChildren().add(frd);
                grpNode.setExpanded(true);
            }
        }
        rootNode.setExpanded(true);
        friendTreeView.setRoot(rootNode);
        friendTreeView.setShowRoot(false);
    }

    /**
     * Initialize the tab pane with MouseEvent
     * Middle mouse to close tab
     */
    public void initializeTabPane() {
        chatTabPane.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.MIDDLE) {
                    chatTabPane.getTabs().remove(this);
                }
            }
        });
    }

    /**
     * Add tab, or select the tab if he already exist
     * @param friendName 
     */
    public void addTabPane(String friendName) {
        if(existTabPane(friendName))
        {
            Tab tab = chatTabPane.getTabs().get(getIdTabByName(friendName));
            SingleSelectionModel<Tab> selectionModel = chatTabPane.getSelectionModel();
            
            selectionModel.select(tab);

        }
        else {
            final Tab tab = new Tab();
            tab.setText(friendName);

            final TextArea tArea = new TextArea();
            tArea.layoutXProperty().setValue(4);
            tArea.layoutYProperty().setValue(290);
            tArea.setPrefRowCount(2);
            tArea.setPrefColumnCount(23);
            tArea.setWrapText(true);
            tArea.promptTextProperty().set("Write your message to " + friendName + " :");

            Pane pane = new AnchorPane();
            pane.getChildren().add(tArea);
            tab.setContent(pane);
            
            // Probablement ici
            tArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode() == KeyCode.ENTER)
                    {
                        System.out.println("Envoye à : "+tab.getText() + " -> " + tArea.getText());
                        chatAPI.sendMessageToFriendByName(tab.getText(), tArea.getText());
                        
                        tArea.clear();
                        event.consume();
                    }
                }
            });
            
            chatTabPane.getTabs().add(tab);
            
            SingleSelectionModel<Tab> selectionModel = chatTabPane.getSelectionModel();
            selectionModel.select(tab);
        }        
    }
    
    /**
     * Check if tab exist
     * @param nameTab
     * @return true if tab exist
     */
    public boolean existTabPane(String nameTab){
        return getIdTabByName(nameTab) != -1;
    }
    
    /**
     * Get the tab's id by his name
     * @param nameTab
     * @return tab's id
     */
    public int getIdTabByName(String nameTab){
        for(int i=0; i < chatTabPane.getTabs().size(); i++){
            if(chatTabPane.getTabs().get(i).getText().equals(nameTab))
                return i;
        }
        return -1;
    }
}
