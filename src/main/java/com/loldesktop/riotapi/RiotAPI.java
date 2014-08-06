/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.riotapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loldesktop.objects.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ubuntudev
 */
public class RiotAPI {
    
    private String apiKey;
    private String region;

    public static final String RANKED_SOLO_5x5 = "RANKED_SOLO_5x5";
    public static final String RANKED_TEAM_5x5 = "RANKED_TEAM_5x5";
    public static final String RANKED_TEAM_3x3 = "RANKED_TEAM_3x3";
    
    Gson gson = new Gson();

    public RiotAPI(String apiKey, String region) {
        this.apiKey = apiKey;
        this.region = region;
    }

    public RiotAPI() {
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    private String generateBaseUrl() {
        return  "https://" + region + ".api.pvp.net/api/lol/" + region;
    }
    
    private String generateBaseUrlStaticData() {
        return "https://global.api.pvp.net/api/lol/static-data/"+ region;
    }
    
    /**
     * DEPRECATED
     * @return List of all champions.
     * @throws RiotAPIException 
     */
    public ChampionList getChampions() throws RiotAPIException {
        RiotAPICaller caller = new RiotAPICaller();
        String response = caller.request(generateBaseUrl() + "/v1.2/champion" + "?api_key=" + apiKey);
        ChampionList championList = gson.fromJson(response, ChampionList.class);
        return championList;
    }
    
    /**
     * 
     * @return List of all champions.
     * @throws RiotAPIException 
     */
    public ChampionList getChampionsStaticData() throws RiotAPIException {
        RiotAPICaller caller = new RiotAPICaller();
        String response = caller.request(generateBaseUrlStaticData()+ "/v1.2/champion" + "?champData=all&api_key=" + apiKey);
        ChampionList championList = gson.fromJson(response, ChampionList.class);
        return championList;
    }

    /**
     * @TODO To improve
     * @return List of all free champions.
     * @throws RiotAPIException 
     */
    public ChampionList getFreeChampions() throws RiotAPIException {
        RiotAPICaller caller = new RiotAPICaller();
        String response = caller.request(generateBaseUrl() + "/v1.2/champion" + "?freeToPlay=true&api_key=" + apiKey);
        ChampionList championList = gson.fromJson(response, ChampionList.class);
        return championList;
    }
    
    /**
     * Retrieve champion by ID
     * @param id
     * @return Champion
     * @throws RiotAPIException 
     */
    public Champion getChampion(int id) throws RiotAPIException {
        RiotAPICaller caller = new RiotAPICaller();
        String response = caller.request(generateBaseUrlStaticData()+ "/v1.2/champion/" + id + "?api_key=" + apiKey);
        Champion champion = gson.fromJson(response, Champion.class);
        return champion;
    }
    
    /**
     * Retrieve summoner by Name
     * @param summonerName
     * @return Summoner
     * @throws RiotAPIException 
     */
    public Summoner getSummoner(String summonerName) throws RiotAPIException {
        ArrayList<String> name = new ArrayList<>();
        String namesWithoutSpace = summonerName.replaceAll(" ", "");
        name.add(namesWithoutSpace);
        Map<String, Summoner> summoner = getSummonersByName(name);
        return summoner.get(namesWithoutSpace.toLowerCase());
    }
    
    public Map<String, Summoner> getSummonersByName(List<String> summonerNames) throws RiotAPIException {
        RiotAPICaller caller = new RiotAPICaller();
        String names = "";
        String namesWithoutSpace;
        for (String i : summonerNames) {
            namesWithoutSpace = i.replaceAll(" ", "");
            names = names + namesWithoutSpace + ",";
        }
        String response = caller.request(generateBaseUrl() + "/v1.4/summoner/by-name/" + names + "?api_key=" + apiKey);
        Map<String, Summoner> summoner = gson.fromJson(response, new TypeToken<Map<String, Summoner>>() {
        }.getType());
        return summoner;
    }
}
