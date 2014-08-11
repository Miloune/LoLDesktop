/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.chatapi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Miloune
 */
public class ChatAPIDialog implements Serializable {
    
    private final ArrayList<ArrayList<String>> allDialog;

    public ChatAPIDialog() {
        this.allDialog = new ArrayList();
    }
    
    /**
     * Add a message to the dialog
     * @param summonerName
     * @param messageName 
     */
    public void addMessage(String summonerName, String messageName){
        SimpleDateFormat formater;
        Date today = new Date();
        formater = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dateString = formater.format(today);
        
        ArrayList<String> messageDialog = new ArrayList();
        messageDialog.add(dateString);
        messageDialog.add(summonerName);
        messageDialog.add(messageName);
        
        this.allDialog.add(messageDialog);
    }
    
    /**
     * Delete dialog
     */
    public void deleteDialog() {
        this.allDialog.clear();
    }
    
    public ArrayList<ArrayList<String>> getDialog() {
        return this.allDialog;
    }
}
