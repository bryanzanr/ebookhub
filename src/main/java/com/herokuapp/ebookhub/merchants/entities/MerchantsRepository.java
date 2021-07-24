package com.herokuapp.ebookhub.merchants.entities;

import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.herokuapp.ebookhub.FirebaseConfig;

public class MerchantsRepository {

    private FirebaseConfig firebaseConfig;

    public MerchantsRepository(FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }
    
    public List<Merchants> findAll() {
        List<Merchants> merchants = new ArrayList<>();
        try {
            String authToken = new FirebaseConfig().getToken();
            String databaseUrl = "https://flyit-e0aa3.firebaseio.com/merchants.json?"
            + "print=pretty&access_token=" + authToken;
            URL url = new URL(databaseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    merchants.add(new Merchants(line));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return merchants;
    }
    
}