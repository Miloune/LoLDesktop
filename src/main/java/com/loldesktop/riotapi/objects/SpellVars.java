/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.riotapi.objects;

import java.util.ArrayList;

/**
 *
 * @author ubuntudev
 */
public class SpellVars {
    private ArrayList<Double> coeff;
    private String dyn;
    private String key;
    private String link;
    private String ranksWith;

    public ArrayList<Double> getCoeff() {
        return coeff;
    }

    public void setCoeff(ArrayList<Double> coeff) {
        this.coeff = coeff;
    }

    public String getDyn() {
        return dyn;
    }

    public void setDyn(String dyn) {
        this.dyn = dyn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRanksWith() {
        return ranksWith;
    }

    public void setRanksWith(String ranksWith) {
        this.ranksWith = ranksWith;
    }
}
