package com.herokuapp.ebookhub.advertisements.entities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.herokuapp.ebookhub.FirebaseConfig;
import com.herokuapp.ebookhub.advertisements.dto.request.BroadcastRequest;

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

    public String save(BroadcastRequest broadcastRequest, String requestMethod) {
        try {
            // System.out.println("ANEH " + broadcastRequest);
            String rawData = "{\"author\": \"" + broadcastRequest.getAuthor() + "\"";
            rawData += ",\"description\": \"" + broadcastRequest.getDescription() + "\"";
            rawData += ",\"img\": \"" + broadcastRequest.getImage() + "\"";
            rawData += ",\"lat\": \"" + broadcastRequest.getLatitude() + "\"";
            rawData += ",\"long\": \"" + broadcastRequest.getLongitude() + "\"";
            rawData += ",\"publish\": \"" + broadcastRequest.getPublishDate() + "\"";
            rawData += ",\"tag\": \"" + broadcastRequest.getPrefsTag() + "\"";
            rawData += ",\"title\": \"" + broadcastRequest.getTitle();
            rawData += "\"}";
            String authToken = FirebaseConfig.getToken();
            String databaseUrl;
            if (requestMethod.split(" ")[0].equalsIgnoreCase("PUT")) {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/advertisements/" 
                + requestMethod.split(" ")[1] 
                + ".json?print=pretty&access_token=" + authToken;
            }else {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/advertisements.json?"
                + "print=pretty&access_token=" + authToken;
            }
            URL url = new URL(databaseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod.split(" ")[0]);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = rawData.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }
            InputStream is;
            if (conn.getResponseCode() == 400) {
                is = conn.getErrorStream();
            }else {
                is = conn.getInputStream();
            }
            try(BufferedReader br = new BufferedReader(
                new InputStreamReader(is, "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}