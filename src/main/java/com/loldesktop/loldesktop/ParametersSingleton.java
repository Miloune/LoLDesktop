/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.loldesktop;

/**
 *
 * @author Miloune
 */
public class ParametersSingleton {
    
    private final String apiKey = "YOU API KEY";
    
    private ParametersSingleton() {
    }
    
    private static final ParametersSingleton parametersSingleton = new ParametersSingleton();

    public String getApiKey() {
        return apiKey;
    }

    public static ParametersSingleton getParametersSingleton() {
        return parametersSingleton;
    }
    
    private Object readResolve() {
        return parametersSingleton;
    }
}
