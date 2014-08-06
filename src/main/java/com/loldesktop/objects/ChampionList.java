/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.objects;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author ubuntudev
 */
public class ChampionList {
    
    private Map<String, Champion> data;
    private Map<String, String> keys;
    //private ArrayList<Champion> champions;
    private String format, type, version;
    
    public Map<String, Champion> getData() {
        return data;
    }

    public void setData(Map<String, Champion> data) {
        this.data = data;
    }

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }
    /*
    public ArrayList<Champion> getChampionList() {
        return champions;
    }

    public void setChampions(ArrayList<Champion> champions) {
        this.champions = champions;
    }
    */
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
