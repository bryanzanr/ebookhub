package com.herokuapp.ebookhub.merchants.entities;

// import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
// import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.herokuapp.ebookhub.FirebaseConfig;
import com.herokuapp.ebookhub.merchants.dto.response.MerchantsListResponse;

public class MerchantsRepository {

    private FirebaseConfig firebaseConfig;

    public MerchantsRepository(FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }
    
    public String findAll() {
        // List<Merchants> merchants = new ArrayList<>();
        try {
            // String authToken = this.firebaseConfig.getToken();
            String authToken = FirebaseConfig.getToken();
            String databaseUrl = "https://flyit-e0aa3.firebaseio.com/merchants.json?"
            + "print=pretty&access_token=" + authToken;
            URL url = new URL(databaseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                StringBuilder sb = new StringBuilder();
                for (String line; (line = reader.readLine()) != null; ) {
                    // Merchants merchant = new Merchants(line);
                    // System.out.println("response " + line);
                    // merchants.add(new Merchants(line));
                    sb.append(line);
                }
                // Gson gson = new Gson();
                // System.out.println("response " + sb.toString());
                // JsonObject data = gson.fromJson(sb.toString(), JsonObject.class);
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
        // return merchants;
        // return new MerchantsListResponse();
        return "";
    }
    
}