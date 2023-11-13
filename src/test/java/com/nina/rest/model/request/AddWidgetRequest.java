package com.nina.rest.model.request;

import com.nina.rest.model.Widget;

public class AddWidgetRequest {
    private Widget addWidget;

    public AddWidgetRequest() {
    }

    public AddWidgetRequest(Widget addWidget) {
        this.addWidget = addWidget;
    }

    public Widget getAddWidget() {
        return addWidget;
    }

    public void setAddWidget(Widget addWidget) {
        this.addWidget = addWidget;
    }
}
