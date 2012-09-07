package org.siemac.metamac.statistical.resources.web.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class BreadCrumbsPanel extends FlowPanel {

    private PlaceManager placeManager;

    private List<Widget> breadCrumbs;
    private int          totalBreadCrumbs;
    private int          processedBreadCrumbs;

    @Inject
    public BreadCrumbsPanel(PlaceManager placeManager) {
        super();
        setHeight("20px");
        setStyleName("breadCrumbPanel");
        this.placeManager = placeManager;
    }

    public void clearBreadCrumbs(int breadCrumbsSize) {
        this.clear();
        processedBreadCrumbs = 0;
        totalBreadCrumbs = breadCrumbsSize;
        breadCrumbs = new ArrayList<Widget>(breadCrumbsSize);
        for (int i = 0; i < breadCrumbsSize; i++) {
            breadCrumbs.add(null);
        }
    }

    public void addBreadCrumbs(String title, int currentIndex) {
        if (title != null) {
            InlineHyperlink link = new InlineHyperlink(title, placeManager.buildRelativeHistoryToken(currentIndex + 1));
            breadCrumbs.set(currentIndex, link);
        }
        processedBreadCrumbs++;
        buildBreadCrumbsIfReady();
    }

    private void buildBreadCrumbsIfReady() {
        if (processedBreadCrumbs == totalBreadCrumbs) {
            for (Widget widget : breadCrumbs) {
                if (widget != null) {
                    if (this.getWidgetCount() > 0) {
                        this.add(new InlineLabel(" > "));
                    }
                    this.add(widget);
                }
            }
        }
    }

}
