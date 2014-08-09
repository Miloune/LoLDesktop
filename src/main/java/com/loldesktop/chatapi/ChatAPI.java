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
import com.github.theholywaffle.lolchatapi.riotapi.RateLimit;
import com.github.theholywaffle.lolchatapi.riotapi.RiotApiKey;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;
import com.github.theholywaffle.lolchatapi.wrapper.FriendGroup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miloune
 */
public class ChatAPI {

    private final LolChat api;

    public ChatAPI(String apiKey, String region) {
        this.api = new LolChat(ChatServer.valueOf(region), FriendRequestPolicy.MANUAL, new RiotApiKey(apiKey, RateLimit.DEFAULT));
        // TODO Probably ADD LISTENERS HERE (ALWAYS BEFORE LOGIN)
    }

    /**
     * LogIn to chat
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
     * Set Online or Offline
     * True for Online
     * False for Offline
     * @param online 
     */
    public void setOnlineOrOffline(boolean online) {
        if(online)
            api.setOnline();
        else
            api.setOffline();
    }
    
    /**
     * Define AVAILABLE or BUSY or AWAY
     * @param status 
     */
    public void setChatMode(ChatMode status){
        api.setChatMode(status);
    }
    
    /**
     * Get all Friend Groups
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
     * @param groupName
     * @return all friend from group
     */
    public ArrayList<Friend> getAllFriendsByGroup(String groupName) {
        ArrayList<Friend> friendList = new ArrayList<>();
        FriendGroup friendGroupByName = api.getFriendGroupByName(groupName);
        for(final Friend f : friendGroupByName.getFriends()) {
            friendList.add(f);
        }
        return friendList;
    }
    
    /**
     * Change name of friend group
     * @param lastName
     * @param newName 
     */
    public void changeNameFriendGroup(String lastName, String newName) {
        api.getFriendGroupByName(lastName).setName(newName);
    }
    
    /**
     * Move a friend to a group
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
     * @return list of all friends
     */
    public List<Friend> getAllFriends() {
        return api.getFriends();
    }
    
    /**
     * Get all friends online
     * @return list of all friends online
     */
    public List<Friend> getAllOnlineFriends() {
        return api.getOnlineFriends();
    }
    
    /**
     * Get all friends offline
     * @return list of all friends offline
     */
    public List<Friend> getAllOfflineFriends() {
        return api.getOfflineFriends();
    }
}
