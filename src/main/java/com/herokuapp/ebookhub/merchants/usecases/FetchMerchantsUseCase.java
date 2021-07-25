package com.herokuapp.ebookhub.merchants.usecases;

import org.springframework.http.ResponseEntity;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.herokuapp.ebookhub.FirebaseConfig;
// import com.herokuapp.ebookhub.merchants.entities.Merchants;
import com.herokuapp.ebookhub.merchants.entities.MerchantsRepository;

import java.util.ArrayList;
import java.util.Map;
// import java.util.HashMap;

import org.springframework.http.HttpStatus;

public class FetchMerchantsUseCase {
    
    private FirebaseConfig firebaseConfig;

    public FetchMerchantsUseCase(FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }

    public ResponseEntity<List<Object>> GetMerchants() {
        List<Object> response = new ArrayList<Object>();
        // List<Merchants> merchants = new MerchantsRepository(this.firebaseConfig).findAll();
        String data = new MerchantsRepository(this.firebaseConfig).findAll();
        Gson gson = new Gson();
        JsonObject temp = gson.fromJson(data, JsonObject.class);
        try {
            // for (int i = 0; i < merchants.size(); i++) {
            //     Map<String, Object> entity = new HashMap<String, Object>();
            //     entity.put("data", merchants.get(i));	
            //     response.add(entity);
            // }
            // response.add(data);
            for (Map.Entry<String, JsonElement> entry: temp.entrySet()) {
                JsonObject tmp = entry.getValue().getAsJsonObject();
                response.add(tmp);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}