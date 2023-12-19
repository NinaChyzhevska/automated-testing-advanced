package com.nina.rest.client.http.restassured;

import com.nina.rest.client.http.HttpResponse;
import io.restassured.response.Response;

public class RestAssuredResponseAdapter implements HttpResponse {
    private final Response response;

    public RestAssuredResponseAdapter(Response response) {
        this.response = response;
    }

    @Override
    public <T> T as(Class<T> responseClass) {
        return response.as(responseClass);
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }
}
