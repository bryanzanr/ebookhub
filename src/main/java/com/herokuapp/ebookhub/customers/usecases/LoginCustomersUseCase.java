package com.herokuapp.ebookhub.customers.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.herokuapp.ebookhub.customers.entities.CustomersRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginCustomersUseCase {

    public ResponseEntity<List<Object>> GetCustomers() {
        
        List<Object> response = new ArrayList<Object>();
        String data = new CustomersRepository().findAll();
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