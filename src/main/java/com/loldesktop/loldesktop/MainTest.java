package com.loldesktop.loldesktop;

import com.loldesktop.objects.*;
import com.loldesktop.riotapi.RiotAPI;
import com.loldesktop.riotapi.RiotAPIException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ubuntudev
 */
public class MainTest {
    static RiotAPI lol = new RiotAPI();
        
    public static void main(String[] args) {
        lol.setApiKey("yourapikey");
        lol.setRegion("euw");
        
        //AllChampionTest();
        //GetChampionByID();
        GetSummonerByName();
    }
    
    static void GetSummonerByName() {
        try {
            Summoner summoner = lol.getSummoner("x Milouee");
            System.out.println("Name: " + summoner.toString());
            
        } catch (RiotAPIException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    static void GetChampionByID() {
        try {
            Champion champion = lol.getChampion(1);
            System.out.println(champion.getName() + " -- " + champion.getTitle());
            
        } catch (RiotAPIException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static void AllChampionTest() {
        try {
            ChampionList champions = lol.getChampionsStaticData();
            
            //String[] keys = new String[champions.getData().size()];
            Champion[] values = new Champion[champions.getData().size()];
            int index = 0;
            
            for (Map.Entry<String, Champion> mapEntry : champions.getData().entrySet()) {
                //keys[index] = mapEntry.getKey();
                values[index] = mapEntry.getValue();
                index++;
            }
            
            for (Champion value : values) {
                System.out.println(value.getName() +" -- "+ value.getStats().getMovespeed());
            }
            
        } catch (RiotAPIException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
