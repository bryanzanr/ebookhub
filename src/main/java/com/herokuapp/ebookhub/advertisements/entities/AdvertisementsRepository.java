package com.herokuapp.ebookhub.advertisements.entities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.herokuapp.ebookhub.FirebaseConfig;

public class AdvertisementsRepository {

    public AdvertisementsRepository() {}

    public String findAll(String id) {
        try {
            String authToken = FirebaseConfig.getToken();
            String databaseUrl;
            if (id != null) {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/advertisements/" + id
                + ".json?print=pretty&access_token=" + authToken;
            }else {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/advertisements.json?"
                + "print=pretty&access_token=" + authToken;
            }
            URL url = new URL(databaseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                StringBuilder sb = new StringBuilder();
                for (String line; (line = reader.readLine()) != null; ) {
                    sb.append(line);
                }
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}