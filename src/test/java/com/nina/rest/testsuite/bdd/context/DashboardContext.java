package com.nina.rest.testsuite.bdd.context;

import com.nina.rest.client.DashboardClient;
import com.nina.rest.model.response.DashboardResponse;
import com.nina.rest.model.response.SearchDashboardsResponse;

public class DashboardContext {
    private DashboardClient dashboardClient;
    private Long dashboardId;
    private String dashboardName;
    private String dashboardDescription;
    private DashboardResponse dashboardResponse;
    private SearchDashboardsResponse dashboardsSearchResponse;

    public DashboardClient getDashboardClient() {
        return dashboardClient;
    }

    public void setDashboardClient(DashboardClient dashboardClient) {
        this.dashboardClient = dashboardClient;
    }

    public Long getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(Long dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public String getDashboardDescription() {
        return dashboardDescription;
    }

    public void setDashboardDescription(String dashboardDescription) {
        this.dashboardDescription = dashboardDescription;
    }

    public DashboardResponse getDashboardResponse() {
        return dashboardResponse;
    }

    public void setDashboardResponse(DashboardResponse dashboardResponse) {
        this.dashboardResponse = dashboardResponse;
    }

    public SearchDashboardsResponse getDashboardsSearchResponse() {
        return dashboardsSearchResponse;
    }

    public void setDashboardsSearchResponse(SearchDashboardsResponse dashboardsSearchResponse) {
        this.dashboardsSearchResponse = dashboardsSearchResponse;
    }
}
