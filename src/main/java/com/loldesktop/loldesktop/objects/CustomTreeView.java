/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loldesktop.loldesktop.objects;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;
import com.github.theholywaffle.lolchatapi.wrapper.FriendGroup;
import com.loldesktop.loldesktop.UserSingleton;
import com.loldesktop.loldesktop.controllers.AppsController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ContextMenuBuilder;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Miloune
 */
public class CustomTreeView {

    private final TreeView<String> friendTreeView;

    public CustomTreeView(final TreeView<String> friendTreeView) {
        //UserSingleton.getUserSingleton().getMainApp().getAppsController().addTabPane("Toto");
        
        List<Friend> allFriends = UserSingleton.getUserSingleton().getChatAPI().getAllOnlineFriends();
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
        rootNode.setExpanded(true);
        friendTreeView.setRoot(rootNode);
        friendTreeView.setShowRoot(false);
        
        friendTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        if (friendTreeView.getSelectionModel().getSelectedItem().isLeaf()) {
                            Tab tab = UserSingleton.getUserSingleton().getMainApp().getAppsController().addTabPane(friendTreeView.getSelectionModel().getSelectedItem().getValue());
                            UserSingleton.getUserSingleton().getMainApp().getAppsController().selectTabPane(tab);
                        }
                    }
                }
                else if(event.getButton() == MouseButton.SECONDARY) {
                    // TODO set selected item by name
                }
            }
        });
        
        friendTreeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

            @Override
            public TreeCell<String> call(TreeView<String> arg0) {
                // Custom treecell for context menu
                return new CustomTreeCell();
            }
        });
        this.friendTreeView = friendTreeView;
    }

    public TreeView<String> getFriendTreeView() {
        return this.friendTreeView;
    }

    private static class CustomTreeCell extends TextFieldTreeCell<String> {

        private final ContextMenu rootContextMenu;

        public CustomTreeCell() {
            rootContextMenu
                    = ContextMenuBuilder.create()
                    .items(
                            MenuItemBuilder.create()
                            .text("Copie status")
                            .onAction(
                                    new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent arg0) {
                                            UserSingleton.getUserSingleton().getChatAPI().copieStatusFromSummoner(getTreeItem().getValue());
                                            System.out.println("Status copied from : " + getTreeItem().getValue());
                                        }
                                    }
                            )
                            .build(),
                            MenuItemBuilder.create()
                            .text("Move to")
                            .onAction(
                                    new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent arg0) {
                                            ArrayList<FriendGroup> choices = UserSingleton.getUserSingleton().getChatAPI().getAllGroups();

                                            Optional<FriendGroup> response = Dialogs.create()
                                            .title("Move " + getTreeItem().getValue() + " to")
                                            .message("Choose the group to move " + getTreeItem().getValue() + ":")
                                            .showChoices(choices);

                                            if (response.isPresent()) {
                                                UserSingleton.getUserSingleton().getChatAPI().moveFriendToGroup(getTreeItem().getValue(), response.get().toString());
                                                System.out.println(getTreeItem().getValue() + " moved to " + response.get());
                                            }
                                        }
                                    }
                            )
                            .build()
                    )
                    .build();
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            // if the item is not empty && isn't a root && it don't get children
            if (!empty && getTreeItem().getParent() != null && getTreeItem().getChildren().isEmpty()) {
                setContextMenu(rootContextMenu);
            }
        }
    }
}
