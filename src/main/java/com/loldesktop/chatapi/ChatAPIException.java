/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.chatapi;

/**
 * TODO Useless atm, see you later
 * @author Miloune
 */
public class ChatAPIException extends Exception {
    
    public static final int ERROR_TEST = 400;
    
    private final int errorCode;
    private final String errorMessage;

    public ChatAPIException(int errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = getError(errorCode);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public static String getError(int code) {
        switch (code) {
            case ERROR_TEST:
                return "TEST";
        }
        return null;
    }
}
