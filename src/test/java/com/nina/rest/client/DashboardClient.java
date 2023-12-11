package com.nina.rest.client;

import com.nina.rest.config.Config;
import com.nina.rest.model.*;
import com.nina.rest.model.request.AddWidgetRequest;
import com.nina.rest.model.request.UpdateDashboardRequest;
import com.nina.rest.model.response.DashboardResponse;
import com.nina.rest.model.response.ResponseMessage;
import com.nina.rest.model.response.SearchDashboardsResponse;
import com.nina.util.EnvironmentPropertyLoader;
import org.apache.http.HttpStatus;

import static com.nina.rest.config.Config.PROJECT_NAME;
import static com.nina.rest.config.Config.TEST_WIDGET_ID;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class DashboardClient {
    private final UserSession userSession;

    public DashboardClient(UserSession userSession) {
        this.userSession = userSession;
    }

    public Dashboard createDashboard(int expectedStatusCode, String name, String description) {
        Dashboard dashboard = new Dashboard(name, description);
        Dashboard response = given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .pathParam("projectName", PROJECT_NAME)
                .auth().oauth2(userSession.getAccessToken())
                .body(dashboard)
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .post(Config.DASHBOARDS_PATH)
                .then()
                .log().body()
                .statusCode(expectedStatusCode)
                .extract().response().as(Dashboard.class);
        dashboard.setId(response.getId());
        return dashboard;
    }

    public Dashboard getDashboardById(int expectedStatusCode, Long id) {
        return given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .pathParam("projectName", PROJECT_NAME)
                .pathParam("dashboardId", id)
                .auth().oauth2(userSession.getAccessToken())
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .get(Config.DASHBOARD_BY_ID_PATH)
                .then()
                .log().body()
                .statusCode(expectedStatusCode)
                .extract().response().as(Dashboard.class);
    }

    public DashboardResponse getDashboardById(Long id) {
        var response = given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .pathParam("projectName", PROJECT_NAME)
                .pathParam("dashboardId", id)
                .auth().oauth2(userSession.getAccessToken())
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .get(Config.DASHBOARD_BY_ID_PATH)
                .then()
                .log().body()
                .extract().response();
        return new DashboardResponse(response.as(Dashboard.class), response.getStatusCode());
    }

    public SearchDashboardsResponse searchDashboards(String value) {
        return given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .pathParam("projectName", PROJECT_NAME)
                .queryParam("filter.eq.name", value)
                .auth().oauth2(userSession.getAccessToken())
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .get(Config.DASHBOARDS_PATH)
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(SearchDashboardsResponse.class);
    }

    public ResponseMessage deleteDashboardById(Long id) {
        return given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .pathParam("projectName", PROJECT_NAME)
                .pathParam("dashboardId", id)
                .auth().oauth2(userSession.getAccessToken())
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .delete(Config.DASHBOARD_BY_ID_PATH)
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(ResponseMessage.class);
    }

    public Dashboard updateDashboard(Long dashboardId, String name, String description) {
        UpdateDashboardRequest updateDashboardRequest = new UpdateDashboardRequest(name, description);
        return given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .pathParam("projectName", PROJECT_NAME)
                .pathParam("dashboardId", dashboardId)
                .auth().oauth2(userSession.getAccessToken())
                .body(updateDashboardRequest)
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .put(Config.DASHBOARD_BY_ID_PATH)
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(Dashboard.class);
    }

    public ResponseMessage addWidgetToDashboard(int expectedStatusCode, Widget widget, Long dashboardId) {
        return given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .pathParam("projectName", PROJECT_NAME)
                .pathParam("dashboardId", dashboardId)
                .auth().oauth2(userSession.getAccessToken())
                .body(new AddWidgetRequest(widget))
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .put(Config.ADD_WIDGET_BY_ID_PATH)
                .then()
                .log().body()
                .statusCode(expectedStatusCode)
                .extract().response().as(ResponseMessage.class);
    }

    public ResponseMessage removeWidgetFromDashboard(Long dashboardId) {
        return given()
                .log().uri()
                .baseUri(EnvironmentPropertyLoader.getProperty("hostUrl"))
                .pathParam("projectName", PROJECT_NAME)
                .pathParam("dashboardId", dashboardId)
                .pathParam("widgetId", TEST_WIDGET_ID)
                .auth().oauth2(userSession.getAccessToken())
                .contentType(JSON)
                .log().body().log().method().log().parameters()
                .when()
                .delete(Config.REMOVE_WIDGET_BY_ID_PATH)
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(ResponseMessage.class);
    }
}
