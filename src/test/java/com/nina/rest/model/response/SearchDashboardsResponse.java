package com.nina.rest.model.response;

import com.nina.rest.model.Dashboard;
import com.nina.rest.model.PageInfo;

import java.util.List;

public class SearchDashboardsResponse {
    private List<Dashboard> content;
    private PageInfo page;

    public List<Dashboard> getContent() {
        return content;
    }

    public void setContent(List<Dashboard> content) {
        this.content = content;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }
}
