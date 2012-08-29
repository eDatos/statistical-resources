package org.siemac.metamac.statistical.resources.web.client.widgets.view;

import org.siemac.metamac.statistical.resources.web.client.ResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.enums.ToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.widgets.presenter.ResourcesToolStripPresenterWidget;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class ResourcesToolStripViewImpl implements ResourcesToolStripPresenterWidget.ResourcesToolStripView {

    private ToolStrip             toolStrip;

    private CustomToolStripButton datasetsButton;
    private CustomToolStripButton collectionsButton;
    private CustomToolStripButton queriesButton;

    @Inject
    public ResourcesToolStripViewImpl() {
        super();
        toolStrip = new ToolStrip();
        toolStrip.setWidth100();
        toolStrip.setAlign(Alignment.LEFT);

        datasetsButton = new CustomToolStripButton(ResourcesWeb.getConstants().statisticalDatasets());
        datasetsButton.setID(ToolStripButtonEnum.DATASETS.getValue());

        collectionsButton = new CustomToolStripButton(ResourcesWeb.getConstants().statisticalCollections());
        collectionsButton.setID(ToolStripButtonEnum.COLLECTIONS.getValue());
        
        queriesButton = new CustomToolStripButton(ResourcesWeb.getConstants().statisticalQueries());
        queriesButton.setID(ToolStripButtonEnum.COLLECTIONS.getValue());

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
