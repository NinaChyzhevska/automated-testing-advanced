package com.nina.rest.config;

public class Config {
    public static final String PROJECT_NAME = "vinni_personal";
    public static final long TEST_WIDGET_ID = 136498L;

    public static final String DASHBOARDS_PATH = "/api/v1/{projectName}/dashboard";
    public static final String DASHBOARD_BY_ID_PATH = DASHBOARDS_PATH + "/{dashboardId}";
    public static final String ADD_WIDGET_BY_ID_PATH = DASHBOARD_BY_ID_PATH + "/add";
    public static final String REMOVE_WIDGET_BY_ID_PATH = DASHBOARD_BY_ID_PATH + "/{widgetId}";

    public static final String OAUTH_ENDPOINT = "/uat/sso/oauth/token";
}
