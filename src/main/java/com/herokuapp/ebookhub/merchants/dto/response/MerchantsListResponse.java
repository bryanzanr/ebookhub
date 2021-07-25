package com.herokuapp.ebookhub.merchants.dto.response;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MerchantsListResponse {

    private Map<String, Map<String, String>> map;

    public MerchantsListResponse() {}

    public MerchantsListResponse(Map<String, Map<String, String>> map) {
        this.map = map;
    }

}