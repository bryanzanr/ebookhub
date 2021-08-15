package com.herokuapp.ebookhub.customers.usecases;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.herokuapp.ebookhub.customers.dto.request.PreferenceRequest;
import com.herokuapp.ebookhub.customers.entities.CustomersRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginCustomersUseCase {

    public ResponseEntity<List<Object>> GetCustomers(String id) {
        
        List<Object> response = new ArrayList<Object>();
        String data = new CustomersRepository().findAll(id);
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

    public ResponseEntity<Map<String, Object>> AddPreference(
        PreferenceRequest preferenceRequest
    ) {
        Map<String, Object> response = new HashMap<String, Object>();
        String prefs = preferenceRequest.getPrefs();
        if (prefs.indexOf("Error") != -1) {
            response.put("timestamp", new Date());
            response.put("status", 415);
            response.put("error", prefs);
            response.put("path", "/customers");
            return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        String data = new CustomersRepository().save(prefs, "POST");
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

    public ResponseEntity<Map<String, Object>> UpdatePreference(
        PreferenceRequest preferenceRequest, String id
    ) {
        Map<String, Object> response = new HashMap<String, Object>();
        String prefs = preferenceRequest.getPrefs();
        if (prefs.indexOf("Error") != -1) {
            response.put("timestamp", new Date());
            response.put("status", 415);
            response.put("error", prefs);
            response.put("path", "/customers");
            return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        String data = new CustomersRepository().save(prefs, "PUT " + id);
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