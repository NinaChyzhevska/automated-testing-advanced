package com.nina.rest.client.http;

public interface HttpResponse {

    /**
     * Get the body and map it to a Java object
     *
     * @param responseClass response class
     * @return parsed response
     * @param <T> type of the response
     */
    <T> T as(Class<T> responseClass);

    /**
     * Get the status code of the response.
     *
     * @return status code
     */
    int getStatusCode();
}
