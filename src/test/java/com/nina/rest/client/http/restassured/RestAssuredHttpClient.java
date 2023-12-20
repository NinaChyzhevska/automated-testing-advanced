package com.nina.rest.client.http.restassured;

import com.nina.rest.client.http.BasicAuth;
import com.nina.rest.client.http.HttpClient;
import com.nina.rest.client.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestAssuredHttpClient implements HttpClient {
    @Override
    public <T> T post(String url,
                      Map<String, String> formParams,
                      BasicAuth basicAuth,
                      Class<T> responseClass) {
        return given()
                .log().uri()
                .auth().basic(basicAuth.username(), basicAuth.password())
                .formParams(formParams)
                .contentType(URLENC)
                .log().body().log().method().log().parameters()
                .when()
                .post(url)
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(responseClass);
    }

    @Override
    public <T> T post(String url,
                      Map<String, String> pathParams,
                      Object body,
                      String oauthToken,
                      int expectedStatusCode,
                      Class<T> responseClass) {
        return given()
                .log().uri()
                .pathParams(pathParams)
                .auth().oauth2(oauthToken)
                .body(body)
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .post(url)
                .then()
                .log().body()
                .statusCode(expectedStatusCode)
                .extract().response().as(responseClass);
    }

    @Override
    public HttpResponse get(String url, Map<String, String> pathParams, String oauthToken) {
        return get(url, pathParams, Map.of(), oauthToken);
    }

    @Override
    public <T> T get(String url,
                     Map<String, String> pathParams,
                     Map<String, String> queryParams,
                     String oauthToken,
                     int expectedStatusCode,
                     Class<T> responseClass) {
        var response = get(url, pathParams, queryParams, oauthToken);
        assertThat(response.getStatusCode(), is(expectedStatusCode));
        return response.as(responseClass);
    }

    private HttpResponse get(String url,
                             Map<String, String> pathParams,
                             Map<String, String> queryParams,
                             String oauthToken) {
        var response = given()
                .log().uri()
                .pathParams(pathParams)
                .queryParams(queryParams)
                .auth().oauth2(oauthToken)
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .get(url)
                .then()
                .log().body()
                .extract().response();
        return new RestAssuredResponseAdapter(response);
    }

    @Override
    public <T> T delete(String url, Map<String, String> pathParams, String oauthToken, Class<T> responseClass) {
        return given()
                .log().uri()
                .pathParams(pathParams)
                .auth().oauth2(oauthToken)
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .delete(url)
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(responseClass);
    }

    @Override
    public <T> T put(String url,
                     Map<String, String> pathParams,
                     Object body,
                     String oauthToken,
                     int expectedStatusCode,
                     Class<T> responseClass) {
        return given()
                .log().uri()
                .pathParams(pathParams)
                .auth().oauth2(oauthToken)
                .body(body)
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .put(url)
                .then()
                .log().body()
                .statusCode(expectedStatusCode)
                .extract().response().as(responseClass);
    }
}
