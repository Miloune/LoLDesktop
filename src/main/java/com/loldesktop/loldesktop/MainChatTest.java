package com.loldesktop.loldesktop;

import com.github.theholywaffle.lolchatapi.ChatServer;
import com.github.theholywaffle.lolchatapi.FriendRequestPolicy;
import com.github.theholywaffle.lolchatapi.LolChat;
import com.github.theholywaffle.lolchatapi.listeners.ChatListener;
import com.github.theholywaffle.lolchatapi.riotapi.RateLimit;
import com.github.theholywaffle.lolchatapi.riotapi.RiotApiKey;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ubuntudev
 */
public class MainChatTest {

    static LolChat api = new LolChat(ChatServer.EUW,
            FriendRequestPolicy.MANUAL, new RiotApiKey(ParametersSingleton.getParametersSingleton().getApiKey(), RateLimit.DEFAULT));

    public static void main(String[] args) {
        api.addChatListener(new ChatListener() {

            @Override
            public void onMessage(Friend friend, String message) {
                System.out.println("[All]>" + friend.getName() + ": " + message);

            }
        });
        
        if (api.login("USERNAME", "PASSWORD")) {
			System.out.println("Connected !");
			// ...
		}
    }

}
