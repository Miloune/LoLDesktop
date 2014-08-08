/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.loldesktop;

import java.io.Serializable;

/**
 *
 * @author Miloune
 */
public class UserSingleton implements Serializable {

    private String username;
    private String password;
    private String region;
    
    private UserSingleton() {
    }
    
    private static final UserSingleton userSingleton = new UserSingleton();

    public static UserSingleton getUserSingleton() {
        return userSingleton;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    private Object readResolve() {
        return userSingleton;
    }
}
