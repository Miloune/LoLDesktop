/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loldesktop.loldesktop.runnable;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;
import com.loldesktop.loldesktop.UserSingleton;

/**
 *
 * @author Miloune
 */
public class NewMessageRennable implements Runnable {

    private final Friend friend;
    private final String message;

    public NewMessageRennable(Friend friend, String message) {
        this.friend = friend;
        this.message = message;
    }

    public NewMessageRennable(Friend friend, String message, Friend friend0, String message0) {
        this.friend = friend;
        this.message = message;
    }

    @Override
    public void run() {
        UserSingleton.getUserSingleton().getMainApp().getAppsController().newMessageInc(friend, message);
    }
}
