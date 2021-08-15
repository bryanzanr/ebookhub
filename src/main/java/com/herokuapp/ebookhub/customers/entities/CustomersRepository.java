package com.herokuapp.ebookhub.customers.entities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.herokuapp.ebookhub.FirebaseConfig;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomersRepository {

    public String findAll(String id) {
        try {
            String authToken = FirebaseConfig.getToken();
            // System.out.println("postman " + authToken);
            String databaseUrl;
            if (id != null) {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/users/" + id
                + ".json?print=pretty&access_token=" + authToken;
            }else {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/users.json?"
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

    public String save(String preferenceRequest, String requestMethod) {
        try {
            String rawData = "{\"prefs\": \"" + preferenceRequest + "\"}";
            // String encodedData = URLEncoder.encode( rawData, "UTF-8" ); 
            String authToken = FirebaseConfig.getToken();
            String databaseUrl;
            if (requestMethod.split(" ")[0].equalsIgnoreCase("PUT")) {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/users/" 
                + requestMethod.split(" ")[1] 
                + ".json?print=pretty&access_token=" + authToken;
            }else {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/users.json?"
                + "print=pretty&access_token=" + authToken;
            }
            URL url = new URL(databaseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod.split(" ")[0]);
            // conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
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
                    // System.out.println(response.toString());
                    return response.toString();
                }
            // try (BufferedReader reader = new BufferedReader(
            //         new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            //     StringBuilder sb = new StringBuilder();
            //     for (String line; (line = reader.readLine()) != null; ) {
            //         sb.append(line);
            //     }
            //     return sb.toString();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}