package org.siemac.metamac.statistical.resources.web.client.widgets.view;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.widgets.presenter.StatisticalResourcesToolStripPresenterWidget;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.toolstrip.view.MetamacToolStripViewImpl;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class StatisticalResourcesToolStripViewImpl extends MetamacToolStripViewImpl implements StatisticalResourcesToolStripPresenterWidget.StatisticalResourcesToolStripView {

    private CustomToolStripButton datasetsButton;
    private CustomToolStripButton collectionsButton;
    private CustomToolStripButton queriesButton;

    @Inject
    public StatisticalResourcesToolStripViewImpl() {
        super();

        datasetsButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().datasets());
        datasetsButton.setID(StatisticalResourcesToolStripButtonEnum.DATASETS.getValue());

        collectionsButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().collections());
        collectionsButton.setID(StatisticalResourcesToolStripButtonEnum.PUBLICATIONS.getValue());

        queriesButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().queries());
        queriesButton.setID(StatisticalResourcesToolStripButtonEnum.QUERIES.getValue());

        toolStrip.addButton(datasetsButton);
        toolStrip.addButton(collectionsButton);
        toolStrip.addButton(queriesButton);
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
