package com.nina.rest.model.response;

import com.nina.rest.model.Dashboard;

public class DashboardResponse {
    private Dashboard dashboard;
    private int statusCode;

    public DashboardResponse(Dashboard dashboard, int statusCode) {
        this.dashboard = dashboard;
        this.statusCode = statusCode;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
