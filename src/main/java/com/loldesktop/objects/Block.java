/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.objects;

import java.util.ArrayList;

/**
 *
 * @author ubuntudev
 */
public class Block {
    private ArrayList<BlockItem> items;
    private boolean recMath;
    private String type;

    public ArrayList<BlockItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<BlockItem> items) {
        this.items = items;
    }

    public boolean getRecMath() {
        return recMath;
    }

    public void setRecMath(boolean recMath) {
        this.recMath = recMath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
