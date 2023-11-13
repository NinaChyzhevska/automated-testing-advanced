package com.nina.rest.client;

import com.nina.rest.config.Config;
import com.nina.rest.model.UserSession;
import com.nina.util.EnvironmentPropertyLoader;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.URLENC;

public class AuthClient {
    private static final String GRANT_TYPE = "password";
    private static final String BASIC_AUTH_USERNAME = "ui";
    private static final String BASIC_AUTH_PASSWORD = "uiman";

    public UserSession login(String username, String password) {
        return given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .auth().basic(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD)
                .formParam("grant_type", GRANT_TYPE)
                .formParam("username", username)
                .formParam("password", password)
                .contentType(URLENC)
                .log().body().log().method().log().parameters()
                .when()
                .post(Config.OAUTH_ENDPOINT)
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(UserSession.class);
    }
}
