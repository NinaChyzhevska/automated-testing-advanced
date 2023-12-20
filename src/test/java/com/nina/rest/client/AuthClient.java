package com.nina.rest.client;

import com.nina.rest.client.http.BasicAuth;
import com.nina.rest.client.http.HttpClient;
import com.nina.rest.config.Config;
import com.nina.rest.model.UserSession;
import com.nina.util.EnvironmentPropertyLoader;

import java.util.Map;

public class AuthClient {
    private static final String GRANT_TYPE = "password";
    private static final String BASIC_AUTH_USERNAME = "ui";
    private static final String BASIC_AUTH_PASSWORD = "uiman";

    private final HttpClient httpClient;

    public AuthClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public UserSession login(String username, String password) {
        var fullPath = EnvironmentPropertyLoader.getProperty("hostUrl") + Config.OAUTH_ENDPOINT;
        var formParams = Map.of(
                "grant_type", GRANT_TYPE,
                "username", username,
                "password", password
        );
        var basicAuth = new BasicAuth(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD);
        return httpClient.post(fullPath, formParams, basicAuth, UserSession.class);
    }
}
