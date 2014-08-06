/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.riotapi;

/**
 *
 * @author ubuntudev
 */
public class RiotAPIException extends Exception {
    
    public static final int ERROR_API_KEY_LIMIT = 429;
    public static final int ERROR_API_KEY_WRONG = 401;
    public static final int ERROR_DATA_NOT_FOUND = 404;
    public static final int ERROR_BAD_REQUEST = 400;
    public static final int ERROR_INTERNAL_SERVER_ERROR = 500;
    public static final int ERROR_SERVICE_UNAVAILABLE = 503;
    
    private final int errorCode;
    private final String errorMessage;

    public RiotAPIException(int errorCode) {
        super(getError(errorCode));
        this.errorCode = errorCode;
        this.errorMessage = getError(errorCode);
    }
    
    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public static String getError(int code) {
        switch (code) {
            case ERROR_API_KEY_LIMIT:
                return "API Limit reached";
            case ERROR_API_KEY_WRONG:
                return "Unauthorized";
            case ERROR_BAD_REQUEST:
                return "Bad request";
            case ERROR_INTERNAL_SERVER_ERROR:
                return "Internal server error";
            case ERROR_DATA_NOT_FOUND:
                return "Data not found";
            case ERROR_SERVICE_UNAVAILABLE:
                return "Service unavailable";
        }
        return null;
    }
}
