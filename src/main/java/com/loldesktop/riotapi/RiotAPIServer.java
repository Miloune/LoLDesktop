/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.riotapi;

/**
 *
 * @author Miloune
 */
public enum RiotAPIServer {
    BR("BR"), 
    EUNE("EUNE"), 
    EUW("EUW"), 
    KR("KR"), 
    LAN("LAN"), 
    LAS("LAS"), 
    NA("NA"), 
    OCE("OCE"), 
    PBE("PBE"), 
    PH("PH"), 
    RU("RU"), 
    TH("TH"), 
    TR("TR"), 
    TW("TW"), 
    VN("VN");
    
    String server;

    private RiotAPIServer(String server) {
        this.server = server;
    }
    
    @Override
    public String toString() {
        return server;
    }
    
    
}
