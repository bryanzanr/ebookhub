package com.herokuapp.ebookhub.customers.entities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.herokuapp.ebookhub.FirebaseConfig;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomersRepository {

    public String findAll() {
        try {
            String authToken = FirebaseConfig.getToken();
            String databaseUrl = "https://flyit-e0aa3.firebaseio.com/users.json?"
            + "print=pretty&access_token=" + authToken;
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