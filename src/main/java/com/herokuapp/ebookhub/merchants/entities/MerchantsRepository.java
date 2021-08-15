package com.herokuapp.ebookhub.merchants.entities;

// import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
// import java.util.ArrayList;

import com.herokuapp.ebookhub.FirebaseConfig;
import com.herokuapp.ebookhub.merchants.dto.request.SellerRequest;

public class MerchantsRepository {

    private FirebaseConfig firebaseConfig;

    public MerchantsRepository(FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }

    public FirebaseConfig getFirebaseConfig() {
        return this.firebaseConfig;
    }
    
    public String findAll(String id) {
        // List<Merchants> merchants = new ArrayList<>();
        try {
            // String authToken = this.firebaseConfig.getToken();
            String authToken = FirebaseConfig.getToken();
            String databaseUrl;
            if (id != null) {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/merchants/" + id
                + ".json?print=pretty&access_token=" + authToken;
            }else {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/merchants"
                + ".json?print=pretty&access_token=" + authToken;
            }
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

    public String save(SellerRequest sellerRequest, String requestMethod) {
        try {
            String rawData = "{\"first_name\": \"" + sellerRequest.getFirstName() + "\"";
            rawData += ",\"last_name\": \"" + sellerRequest.getLastName() + "\"";
            rawData += ",\"merchant_name\": \"" + sellerRequest.getMerchantName() + "\"";
            rawData += ",\"profile_picture\": \"" + sellerRequest.getProfilePicture() + "\"";
            rawData += ",\"username\": \"" + sellerRequest.getUsername();
            rawData += "\"}";
            String authToken = FirebaseConfig.getToken();
            String databaseUrl;
            if (requestMethod.split(" ")[0].equalsIgnoreCase("PUT")) {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/merchants/" 
                + requestMethod.split(" ")[1] 
                + ".json?print=pretty&access_token=" + authToken;
            }else {
                databaseUrl = "https://flyit-e0aa3.firebaseio.com/merchants.json?"
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