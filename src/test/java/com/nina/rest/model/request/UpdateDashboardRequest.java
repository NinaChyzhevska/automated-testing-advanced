package com.nina.rest.model.request;

import com.nina.rest.model.Widget;

import java.util.List;

public class UpdateDashboardRequest {
    private String description;
    private String name;
    private Long id;
    private List<Widget> updateWidgets;

    public UpdateDashboardRequest() {
    }

    public UpdateDashboardRequest(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Widget> getUpdateWidgets() {
        return updateWidgets;
    }

    public void setUpdateWidgets(List<Widget> updateWidgets) {
        this.updateWidgets = updateWidgets;
    }
}
