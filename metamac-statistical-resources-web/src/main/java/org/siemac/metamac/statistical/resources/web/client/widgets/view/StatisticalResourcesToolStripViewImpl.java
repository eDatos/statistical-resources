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
    private CustomToolStripButton publicationsButton;
    private CustomToolStripButton queriesButton;

    @Inject
    public StatisticalResourcesToolStripViewImpl() {
        super();

        datasetsButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().datasets());
        datasetsButton.setID(StatisticalResourcesToolStripButtonEnum.DATASETS.getValue());

        publicationsButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().publications());
        publicationsButton.setID(StatisticalResourcesToolStripButtonEnum.PUBLICATIONS.getValue());

        queriesButton = new CustomToolStripButton(StatisticalResourcesWeb.getConstants().queries());
        queriesButton.setID(StatisticalResourcesToolStripButtonEnum.QUERIES.getValue());

        toolStrip.addButton(datasetsButton);
        toolStrip.addButton(publicationsButton);
        toolStrip.addButton(queriesButton);
    }

    @Override
    public HasClickHandlers getGoDatasetsList() {
        return datasetsButton;
    }

    @Override
    public HasClickHandlers getGoPublicationsList() {
        return publicationsButton;
    }

    @Override
    public HasClickHandlers getGoQueriesList() {
        return queriesButton;
    }
}
