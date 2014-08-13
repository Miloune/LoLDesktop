/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loldesktop.loldesktop.controllers;

import com.github.theholywaffle.lolchatapi.LolStatus;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;
import com.loldesktop.chatapi.ChatAPI;
import com.loldesktop.chatapi.ChatAPIMessage;
import com.loldesktop.loldesktop.MainApp;
import com.loldesktop.loldesktop.NumberField;
import com.loldesktop.loldesktop.UserSingleton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Miloune
 */
public final class AppsController implements Initializable {

    @FXML // fx:id="friendTreeView"
    private TreeView<String> friendTreeView;

    @FXML // fx:id="chatTabPane"
    private TabPane chatTabPane;

    private ChatAPI chatAPI;
    
    private MainApp mainApp;

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
        
        newMessageInc(chatAPI.getAllFriends().get(0), "haha");
    }
    
 
    /**
     * Initialize friend TreeView
     * Event : Double click for open tab
     * TODO : Implement listener for friend loggin / disconnect
     */
    private void initializeTreeView() {
        friendTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        if(friendTreeView.getSelectionModel().getSelectedItem().isLeaf())
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
    private void initializeTabPane() {
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
    private void addTabPane(String friendName) {
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

            final VBox chatBox = new VBox();
            // Add message to chatBox
            // chatBox.getChildren().add(new Label("Hello World !"));

            final ScrollPane scrollPane = new ScrollPane();
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.layoutXProperty().setValue(2);
            scrollPane.layoutYProperty().setValue(2);
            //scrollPane.setFitToWidth(false);
            //scrollPane.setFitToHeight(false);
            scrollPane.setPrefHeight(282);
            scrollPane.setPrefWidth(325);
            scrollPane.setContent(chatBox);
            
            Pane pane = new AnchorPane();
            pane.getChildren().add(scrollPane);
            pane.getChildren().add(tArea);
            tab.setContent(pane);
            
            tArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode() == KeyCode.ENTER)
                    {
                        System.out.println("Envoye à : "+tab.getText() + " -> " + tArea.getText());
                        chatAPI.sendMessageToFriendByName(tab.getText(), tArea.getText());
                        
                        ChatAPIMessage message = new ChatAPIMessage(new Date(), UserSingleton.getUserSingleton().getUsername(), tArea.getText());
                        
                        chatBox.getChildren().add(message.toLabel());
                        // TODO Deserialize and add here to dialog and serialise him
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
    private boolean existTabPane(String nameTab){
        return getIdTabByName(nameTab) != -1;
    }
    
    /**
     * Get the tab's id by his name
     * @param nameTab
     * @return tab's id
     */
    private int getIdTabByName(String nameTab){
        if(chatTabPane.getTabs().size() != 0)
        {
            for(int i=0; i < chatTabPane.getTabs().size(); i++){
                if(chatTabPane.getTabs().get(i).getText().equals(nameTab))
                    return i;
            }
        }
        return -1;
    }
    
    /**
     * When message from friend is incomming
     * @param friend
     * @param message 
     */
    public void newMessageInc(Friend friend, String message) {
        ChatAPIMessage msg = new ChatAPIMessage(new Date(), friend.getName(), message);
        
        Label msgLabel = msg.toLabel();
        
        if(existTabPane(friend.getName()) == false)
        {
            addTabPane(friend.getName());
        }
        Tab tab = chatTabPane.getTabs().get(getIdTabByName(friend.getName()));
        Pane pane = (Pane) tab.getContent();
        ScrollPane scrollPane = (ScrollPane) pane.getChildren().get(0);
        VBox vBox = (VBox) scrollPane.getContent();
        
        vBox.getChildren().add(msgLabel);
    }
    
    @FXML
    /**
     * Copy the Status from online friends
     */
    private void copyStatus() {
        List<String> choices = new ArrayList<>();
        
        for(Friend f : chatAPI.getAllOnlineFriends())
        {
            choices.add(f.getName());
        }

        Set<String> treeSet = new TreeSet<>(choices);
        treeSet.iterator();
        
        Optional<String> response = Dialogs.create()
                .title("Copy a friend's status")
                .message("Choose your friend :")
                .showChoices(treeSet);

        // One way to get the response value.
        if (response.isPresent()) {
            System.out.println("Copy status from : " + response.get());
            chatAPI.copieStatusFromSummoner(response.get());
        }
    }
    
    /**
     * Is called by the main app to give a reference back to itself.
     * @param aThis 
     */
    public void setMainApp(MainApp aThis) {
        this.mainApp = aThis;
    }
    
    @FXML
    /**
     * Disconnect user and back to login view
     */
    private void logOff() {
        this.chatAPI.disconnectChat();
        UserSingleton.getUserSingleton().clear();
        this.mainApp.showLoginOverview();
    }
    
    @FXML
    /**
     * Set custom status
     */
    private void setStatus() {
        final TextField statusMessage = new TextField();
        final NumberField normalWins = new NumberField();
        final NumberField levelAccount = new NumberField();

        final Action actionValide = new AbstractAction("Set status") {
            // This method is called when the valide button is clicked ...
            @Override
            public void handle(ActionEvent ae) {
                Dialog d = (Dialog) ae.getSource();
                
                LolStatus lolStatus = new LolStatus();
                lolStatus.setProfileIconId(1);
                lolStatus.setLevel(levelAccount.getInt());
                lolStatus.setNormalWins(normalWins.getInt());
                lolStatus.setStatusMessage(statusMessage.getText());
                chatAPI.setLolStatus(lolStatus);
                
                d.hide();
            }
        };

        Dialog dlg = new Dialog(null, "Change your status");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        
        statusMessage.setPromptText("Your status");
        
        grid.add(new Label("Status :"), 0, 0);
        grid.add(statusMessage, 1, 0);
        grid.add(new Label("Level Account"), 0, 1);
        grid.add(levelAccount, 1, 1);
        grid.add(new Label("Normal wins:"), 0, 2);
        grid.add(normalWins, 1, 2);
        
        ButtonBar.setType(actionValide, ButtonBar.ButtonType.OK_DONE);
        
        dlg.setMasthead("Set your own custom status");
        dlg.setContent(grid);
        dlg.resizableProperty().set(false);
        dlg.getActions().addAll(actionValide, Dialog.Actions.CANCEL);
        dlg.show();
    }
}
