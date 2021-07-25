package com.herokuapp.ebookhub.advertisements.usecases;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.herokuapp.ebookhub.advertisements.entities.AdvertisementsRepository;

import java.util.ArrayList;

public class FetchAdvertisementsUseCase {

    public FetchAdvertisementsUseCase() {}

    public ResponseEntity<List<Object>> GetAdvertisements() {
        
        List<Object> response = new ArrayList<Object>();
        String data = new AdvertisementsRepository().findAll();
        Gson gson = new Gson();
        JsonObject temp = gson.fromJson(data, JsonObject.class);
        try {
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