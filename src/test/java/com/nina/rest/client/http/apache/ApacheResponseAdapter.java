package com.nina.rest.client.http.apache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nina.rest.client.http.HttpResponse;

public class ApacheResponseAdapter implements HttpResponse {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String jsonResponse;
    private final int statusCode;

    public ApacheResponseAdapter(String jsonResponse, int statusCode) {
        this.jsonResponse = jsonResponse;
        this.statusCode= statusCode;
    }

    @Override
    public <T> T as(Class<T> responseClass) {
        try {
            return objectMapper.readValue(jsonResponse, responseClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse body: ", e);
        }
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
