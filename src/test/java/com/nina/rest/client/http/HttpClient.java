package com.nina.rest.client.http;

import java.util.Map;

public interface HttpClient {
    /**
     * Sends POST request with basic authentication
     *
     * @param url url with host including
     * @param formParams form params
     * @param basicAuth basic auth details
     * @param responseClass class of the response
     * @return parsed response
     * @param <T> type of the response
     */
    <T> T post(String url,
               Map<String, String> formParams,
               BasicAuth basicAuth,
               Class<T> responseClass);

    /**
     * Sends POST request with OAuth2 token
     * @param url url
     * @param pathParams path params
     * @param body body
     * @param oauthToken oauth token
     * @param expectedStatusCode expected status code
     * @param responseClass response class
     * @return parsed response body
     * @param <T> type of the response
     */
    <T> T post(String url,
               Map<String, String> pathParams,
               Object body,
               String oauthToken,
               int expectedStatusCode,
               Class<T> responseClass);

    /**
     * Executes GET request
     *
     * @param url target url
     * @param pathParams path params
     * @param oauthToken auth token
     * @return raw response
     */
    HttpResponse get(String url, Map<String, String> pathParams, String oauthToken);

    /**
     * Executes GET request
     *
     * @param url target url
     * @param pathParams path params
     * @param queryParams query params
     * @param oauthToken auth token
     * @param expectedStatusCode expected status code
     * @param responseClass response class
     * @return parsed response
     * @param <T> type of the response
     */
    <T> T get(String url,
              Map<String, String> pathParams,
              Map<String, String> queryParams,
              String oauthToken,
              int expectedStatusCode,
              Class<T> responseClass);

    /**
     * Executes DELETE request
     *
     * @param url target url
     * @param pathParams path params
     * @param oauthToken auth token
     * @param responseClass response class
     * @return parsed response
     * @param <T> type of the response
     */
    <T> T delete(String url,
                 Map<String, String> pathParams,
                 String oauthToken,
                 Class<T> responseClass);

    /**
     * Sends PUT request with OAuth2 token
     * @param url url
     * @param pathParams path params
     * @param body body
     * @param oauthToken oauth token
     * @param expectedStatusCode expected status code
     * @param responseClass response class
     * @return parsed response body
     * @param <T> type of the response
     */
    <T> T put(String url,
              Map<String, String> pathParams,
              Object body,
              String oauthToken,
              int expectedStatusCode,
              Class<T> responseClass);
}
