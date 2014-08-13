/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.chatapi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Label;

/**
 *
 * @author Miloune
 */
public class ChatAPIMessage implements Serializable {
    private final String messageDate;
    private final String summonerName;
    private final String message;

    public ChatAPIMessage(Date messageDate, String summonerName, String message) {
        SimpleDateFormat formater;
        //formater = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        formater = new SimpleDateFormat("hh:mm");
        String dateString = formater.format(messageDate);
        
        this.messageDate = "["+dateString+"]";
        this.summonerName = summonerName;
        this.message = message;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public String getMessage() {
        return message;
    }   
    
    public Label toLabel() {
        Label msg = new Label();
        msg.setText(this.messageDate + " " + this.summonerName + " : " + this.message);
        msg.setWrapText(true);
        msg.setMaxWidth(320);
        return msg;
    }
}
