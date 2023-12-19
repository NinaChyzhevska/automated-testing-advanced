package com.nina.rest.client;

import com.nina.rest.client.http.HttpClient;
import com.nina.rest.config.Config;
import com.nina.rest.model.Dashboard;
import com.nina.rest.model.UserSession;
import com.nina.rest.model.Widget;
import com.nina.rest.model.request.AddWidgetRequest;
import com.nina.rest.model.request.UpdateDashboardRequest;
import com.nina.rest.model.response.DashboardResponse;
import com.nina.rest.model.response.ResponseMessage;
import com.nina.rest.model.response.SearchDashboardsResponse;
import com.nina.util.EnvironmentPropertyLoader;
import org.apache.http.HttpStatus;

import java.util.Map;

import static com.nina.rest.config.Config.PROJECT_NAME;
import static com.nina.rest.config.Config.TEST_WIDGET_ID;

public class DashboardClient {
    private final HttpClient httpClient;
    private final UserSession userSession;

    public DashboardClient(HttpClient httpClient, UserSession userSession) {
        this.httpClient = httpClient;
        this.userSession = userSession;
    }

    public Dashboard createDashboard(int expectedStatusCode, String name, String description) {
        var dashboard = new Dashboard(name, description);
        var fullPath = getFullPath(Config.DASHBOARDS_PATH);
        var pathParams = Map.of("projectName", PROJECT_NAME);
        var accessToken = userSession.getAccessToken();
        Dashboard response = httpClient.post(fullPath, pathParams, dashboard,
                accessToken, expectedStatusCode, Dashboard.class);
        dashboard.setId(response.getId());
        return dashboard;
    }

    public Dashboard getDashboardById(int expectedStatusCode, Long id) {
        var fullPath = getFullPath(Config.DASHBOARD_BY_ID_PATH);
        var pathParams = Map.of("projectName", PROJECT_NAME, "dashboardId", id.toString());
        var accessToken = userSession.getAccessToken();
        return httpClient.get(fullPath, pathParams, Map.of(), accessToken,
                expectedStatusCode, Dashboard.class);
    }

    public DashboardResponse getDashboardById(Long id) {
        var fullPath = getFullPath(Config.DASHBOARD_BY_ID_PATH);
        var pathParams = Map.of("projectName", PROJECT_NAME, "dashboardId", id.toString());
        var accessToken = userSession.getAccessToken();
        var response = httpClient.get(fullPath, pathParams, accessToken);
        return new DashboardResponse(response.as(Dashboard.class), response.getStatusCode());
    }

    public SearchDashboardsResponse searchDashboards(String value) {
        var fullPath = getFullPath(Config.DASHBOARDS_PATH);
        var pathParams = Map.of("projectName", PROJECT_NAME);
        var queryParams = Map.of("filter.eq.name", value);
        var accessToken = userSession.getAccessToken();
        return httpClient.get(fullPath, pathParams, queryParams, accessToken,
                HttpStatus.SC_OK, SearchDashboardsResponse.class);
    }

    public ResponseMessage deleteDashboardById(Long id) {
        var fullPath = getFullPath(Config.DASHBOARD_BY_ID_PATH);
        var pathParams = Map.of(
                "projectName", PROJECT_NAME,
                "dashboardId", id.toString());
        var accessToken = userSession.getAccessToken();
        return httpClient.delete(fullPath, pathParams, accessToken, ResponseMessage.class);
    }

    public Dashboard updateDashboard(Long dashboardId, String name, String description) {
        var updateDashboardRequest = new UpdateDashboardRequest(name, description);
        var fullPath = getFullPath(Config.DASHBOARD_BY_ID_PATH);
        var pathParams = Map.of(
                "projectName", PROJECT_NAME,
                "dashboardId", dashboardId.toString());
        var accessToken = userSession.getAccessToken();
        return httpClient.put(fullPath, pathParams, updateDashboardRequest,
                accessToken, HttpStatus.SC_OK, Dashboard.class);
    }

    public ResponseMessage addWidgetToDashboard(int expectedStatusCode, Widget widget, Long dashboardId) {
        var widgetCreationRequest = new AddWidgetRequest(widget);
        var fullPath = getFullPath(Config.ADD_WIDGET_BY_ID_PATH);
        var pathParams = Map.of(
                "projectName", PROJECT_NAME,
                "dashboardId", dashboardId.toString());
        var accessToken = userSession.getAccessToken();
        return httpClient.put(fullPath, pathParams, widgetCreationRequest,
                accessToken, expectedStatusCode, ResponseMessage.class);
    }

    public ResponseMessage removeWidgetFromDashboard(Long dashboardId) {
        var fullPath = getFullPath(Config.REMOVE_WIDGET_BY_ID_PATH);
        var pathParams = Map.of(
                "projectName", PROJECT_NAME,
                "dashboardId", dashboardId.toString(),
                "widgetId", String.valueOf(TEST_WIDGET_ID));
        var accessToken = userSession.getAccessToken();
        return httpClient.delete(fullPath, pathParams, accessToken, ResponseMessage.class);
    }

    private String getFullPath(String path) {
        return EnvironmentPropertyLoader.getProperty("hostUrl") + path;
    }
}
