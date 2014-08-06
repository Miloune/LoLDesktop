/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.riotapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author ubuntudev
 */
public class RiotAPICaller {
    public String request(String URL) throws RiotAPIException {

        try {
            String requestURL = URL;

            URL url = new URL(requestURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(false);

            if (connection.getResponseCode() != 200) {
                throw new RiotAPIException(connection.getResponseCode());
            }

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,"utf-8"));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            connection.disconnect();

            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
