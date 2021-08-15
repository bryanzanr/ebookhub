package com.herokuapp.ebookhub.advertisements.usecases;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.herokuapp.ebookhub.advertisements.dto.request.BroadcastRequest;
import com.herokuapp.ebookhub.advertisements.entities.AdvertisementsRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class FetchAdvertisementsUseCase {

    public FetchAdvertisementsUseCase() {}

    public ResponseEntity<List<Object>> GetAdvertisements(String id) {
        
        List<Object> response = new ArrayList<Object>();
        String data = new AdvertisementsRepository().findAll(id);
        Gson gson = new Gson();
        JsonObject temp = gson.fromJson(data, JsonObject.class);
        try {
            if (id != null) {
                response.add(temp);
            }else {
                for (Map.Entry<String, JsonElement> entry: temp.entrySet()) {
                    JsonObject tmp = entry.getValue().getAsJsonObject();
                    response.add(tmp);
                }
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.add(e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Map<String, Object>> BroadcastAdvertisement(
        BroadcastRequest broadcastRequest,
        String requestMethod
    ) {
        Map<String, Object> response = new HashMap<String, Object>();
        if (requestMethod.split(" ")[0].equalsIgnoreCase("PUT")
        && requestMethod.split(" ")[1] == null) {
            response.put("error", "no id provided");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String data = new AdvertisementsRepository().save(broadcastRequest
        , requestMethod);
        Gson gson = new Gson();
        JsonObject temp = gson.fromJson(data, JsonObject.class);
        try {
            response.put("data", temp);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}