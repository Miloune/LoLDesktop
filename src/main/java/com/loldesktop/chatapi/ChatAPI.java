/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loldesktop.chatapi;

import com.github.theholywaffle.lolchatapi.ChatMode;
import com.github.theholywaffle.lolchatapi.ChatServer;
import com.github.theholywaffle.lolchatapi.FriendRequestPolicy;
import com.github.theholywaffle.lolchatapi.LolChat;
import com.github.theholywaffle.lolchatapi.LolStatus;
import com.github.theholywaffle.lolchatapi.LolStatus.Queue;
import com.github.theholywaffle.lolchatapi.LolStatus.Tier;
import com.github.theholywaffle.lolchatapi.listeners.ChatListener;
import com.github.theholywaffle.lolchatapi.listeners.ConnectionListener;
import com.github.theholywaffle.lolchatapi.listeners.FriendListener;
import com.github.theholywaffle.lolchatapi.riotapi.RateLimit;
import com.github.theholywaffle.lolchatapi.riotapi.RiotApiKey;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;
import com.github.theholywaffle.lolchatapi.wrapper.FriendGroup;
import com.loldesktop.loldesktop.runnable.NewMessageRennable;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author Miloune
 */
public class ChatAPI {

    private final LolChat api;

    public ChatAPI(String apiKey, String region) {
        this.api = new LolChat(ChatServer.valueOf(region), FriendRequestPolicy.MANUAL, new RiotApiKey(apiKey, RateLimit.DEFAULT));

        // TODO Probably ADD LISTENERS HERE (ALWAYS BEFORE LOGIN)
        this.api.addChatListener(new ChatListener() {
            @Override
            public void onMessage(Friend friend, String message) {
                Platform.runLater(new NewMessageRennable(friend, message, friend, message));
            }
        });
        
        this.api.addConnectionListener(new ConnectionListener() {

            @Override
            public void connectionClosed() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void connectionClosedOnError(Exception excptn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void reconnectingIn(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void reconnectionFailed(Exception excptn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void reconnectionSuccessful() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        this.api.addFriendListener(new FriendListener() {

            @Override
            public void onFriendAvailable(Friend friend) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onFriendAway(Friend friend) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onFriendBusy(Friend friend) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onFriendJoin(Friend friend) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onFriendLeave(Friend friend) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onFriendStatusChange(Friend friend) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onNewFriend(Friend friend) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRemoveFriend(String string, String string1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    /**
     * LogIn to chat
     *
     * @param username
     * @param password
     * @return
     */
    public boolean loginChat(String username, String password) {
        return api.login(username, password);
    }

    /**
     * Disconnect to chat
     */
    public void disconnectChat() {
        api.disconnect();
    }

    /**
     * Send a message to all friend
     *
     * @param message
     */
    public void sendMessageAllFriend(String message) {
        for (final Friend f : api.getFriends()) {
            f.sendMessage(message);
        }
    }

    /**
     * Send a message to a friend
     *
     * @param name
     * @param message
     */
    public void sendMessageToFriendByName(String name, String message) {
        final Friend f = api.getFriendByName(name);
        if (f != null && f.isOnline()) {
            f.sendMessage(message);
        }
    }

    /**
     * Get summoner's information like Ranked / Status / Spectating / Normal
     * Leaves
     *
     * @param name
     * @return LolStatus
     */
    public LolStatus getSummonerStatusByName(String name) {
        final Friend friend = api.getFriendByName(name);
        final LolStatus status = friend.getStatus();
        return status;
    }

    /**
     * Define new status
     *
     * @param level
     * @param leagueQueue
     * @param leagueTier
     * @param leagueName
     */
    public void setNewStatus(int level, Queue leagueQueue, Tier leagueTier, String leagueName) {
        final LolStatus newStatus = new LolStatus();
        newStatus.setLevel(level);
        newStatus.setRankedLeagueQueue(leagueQueue);
        newStatus.setRankedLeagueTier(leagueTier);
        newStatus.setRankedLeagueName(leagueName);
        api.setStatus(newStatus);
    }

    /**
     * Define new status from summoner
     *
     * @param name
     */
    public void copieStatusFromSummoner(String name) {
        final LolStatus copyStatus = api.getFriendByName(name).getStatus();
        api.setStatus(copyStatus);
    }

    /**
     * Set Online or Offline True for Online False for Offline
     *
     * @param online
     */
    public void setOnlineOrOffline(boolean online) {
        if (online) {
            api.setOnline();
        } else {
            api.setOffline();
        }
    }

    /**
     * Define AVAILABLE or BUSY or AWAY
     *
     * @param chatMode
     */
    public void setChatMode(ChatMode chatMode) {
        api.setChatMode(chatMode);
    }

    /**
     * Get all Friend Groups
     *
     * @return ArrayList de FriendGroup
     */
    public ArrayList<FriendGroup> getAllGroups() {
        ArrayList<FriendGroup> friendList = new ArrayList<>();
        for (final FriendGroup g : api.getFriendGroups()) {
            friendList.add(g);
        }
        return friendList;
    }

    /**
     * Get all friend from group
     *
     * @param groupName
     * @return all friend from group
     */
    public ArrayList<Friend> getAllFriendsByGroup(String groupName) {
        ArrayList<Friend> friendList = new ArrayList<>();
        FriendGroup friendGroupByName = api.getFriendGroupByName(groupName);
        for (final Friend f : friendGroupByName.getFriends()) {
            friendList.add(f);
        }
        return friendList;
    }

    /**
     * Change name of friend group
     *
     * @param lastName
     * @param newName
     */
    public void changeNameFriendGroup(String lastName, String newName) {
        api.getFriendGroupByName(lastName).setName(newName);
    }

    /**
     * Move a friend to a group
     *
     * @param friendName
     * @param newGroupName
     */
    public void moveFriendToGroup(String friendName, String newGroupName) {
        final Friend f = api.getFriendByName(friendName);
        final FriendGroup friendGroup = api.getFriendGroupByName(newGroupName);
        friendGroup.addFriend(f);
    }

    /**
     * Get all friends
     *
     * @return list of all friends
     */
    public List<Friend> getAllFriends() {
        return api.getFriends();
    }

    /**
     * Get all friends online
     *
     * @return list of all friends online
     */
    public List<Friend> getAllOnlineFriends() {
        return api.getOnlineFriends();
    }

    /**
     * Get all friends offline
     *
     * @return list of all friends offline
     */
    public List<Friend> getAllOfflineFriends() {
        return api.getOfflineFriends();
    }

    /**
     * TODO : Must be tested Know if he's connected
     *
     * @return true if he's connected
     */
    public boolean isConnected() {
        return api.isLoaded();
    }

    /**
     * Get friend by his name
     *
     * @param name
     * @return Friend
     */
    public Friend getFriendByName(String name) {
        return api.getFriendByName(name);
    }

    /**
     * Add a friend by summonerName
     *
     * @param name
     * @return true if he's added
     */
    public boolean addFriendByName(String name) {
        return api.addFriendByName(name);
    }

    /**
     * Create a friendgroup
     *
     * @param friendGroup
     * @return FriendGroup
     */
    public FriendGroup addFriendGroup(String friendGroup) {
        return api.addFriendGroup(friendGroup);
    }

    /**
     * Get list of pending friend request
     *
     * @return list of pending friend request
     */
    public List<Friend> getPendingFriendRequest() {
        return api.getPendingFriendRequests();
    }

    public void setLolStatus(LolStatus lolStatus) {
        api.setStatus(lolStatus);
    }

    // TODO Delete, just for test
    public void test() {
        LolStatus aa = new LolStatus();
    }
}
