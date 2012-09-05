package org.siemac.metamac.statistical.resources.web.client.widgets.view;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.widgets.presenter.StatisticalResourcesToolStripPresenterWidget;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class StatisticalResourcesToolStripViewImpl implements StatisticalResourcesToolStripPresenterWidget.StatisticalResourcesToolStripView {

    private ToolStrip             toolStrip;

    private CustomToolStripButton datasetsButton;
    private CustomToolStripButton collectionsButton;
    private CustomToolStripButton queriesButton;

    @Inject
    public StatisticalResourcesToolStripViewImpl() {
        super();
        toolStrip = new ToolStrip();
        toolStrip.setWidth100();
        toolStrip.setAlign(Alignment.LEFT);

        datasetsButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().statisticalDatasets());
        datasetsButton.setID(StatisticalResourcesToolStripButtonEnum.DATASETS.getValue());

        collectionsButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().statisticalCollections());
        collectionsButton.setID(StatisticalResourcesToolStripButtonEnum.COLLECTIONS.getValue());
        
        queriesButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().statisticalQueries());
        queriesButton.setID(StatisticalResourcesToolStripButtonEnum.QUERIES.getValue());

        toolStrip.addButton(datasetsButton);
        toolStrip.addButton(collectionsButton);
        toolStrip.addButton(queriesButton);
    }

    @Override
    public void addToSlot(Object slot, Widget content) {

    }

    @Override
    public Widget asWidget() {
        return toolStrip;
    }

    @Override
    public void removeFromSlot(Object slot, Widget content) {

    }

    @Override
    public void setInSlot(Object slot, Widget content) {

    }
    
    @Override
    public HasClickHandlers getGoDatasetsList() {
        return datasetsButton;
    }
   
    @Override
    public HasClickHandlers getGoCollectionsList() {
        return collectionsButton;
    }
    
    @Override
    public HasClickHandlers getGoQueriesList() {
        return queriesButton;
    }
}
