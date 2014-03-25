package org.siemac.metamac.statistical.resources.web.client.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripLayoutEnum;
import org.siemac.metamac.statistical.resources.web.client.view.handlers.MainPageUiHandlers;
import org.siemac.metamac.web.common.client.widgets.RadioToolStripButton;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class StatisticalResourcesMenu extends ToolStrip {

    private RadioToolStripButton datasetsButton;
    private RadioToolStripButton publicationsButton;
    private RadioToolStripButton queriesButton;

    private MainPageUiHandlers   uiHandlers;

    public StatisticalResourcesMenu() {
        super();
        setWidth100();
        setAlign(Alignment.LEFT);
        // DATASETS

        datasetsButton = new RadioToolStripButton(getConstants().datasets());
        datasetsButton.setID(StatisticalResourcesToolStripButtonEnum.DATASETS.getValue());
        datasetsButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().goToDatasets();
            }
        });
        addButton(datasetsButton);

        // PUBLICATIONS

        publicationsButton = new RadioToolStripButton(getConstants().publications());
        publicationsButton.setID(StatisticalResourcesToolStripButtonEnum.PUBLICATIONS.getValue());
        publicationsButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().goToPublications();
            }
        });
        addButton(publicationsButton);

        // QUERIES

        queriesButton = new RadioToolStripButton(getConstants().queries());
        queriesButton.setID(StatisticalResourcesToolStripButtonEnum.QUERIES.getValue());
        queriesButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().goToQueries();
            }
        });
        addButton(queriesButton);
    }

    public void setUiHandlers(MainPageUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public MainPageUiHandlers getUiHandlers() {
        return this.uiHandlers;
    }

    public void selectButton(StatisticalResourcesToolStripButtonEnum type) {
        deselectAll();
        switch (type) {
            case DATASETS:
                datasetsButton.select();
                break;
            case PUBLICATIONS:
                publicationsButton.select();
                break;
            case QUERIES:
                queriesButton.select();
                break;
        }
    }

    public void deselectAll() {
        datasetsButton.deselect();
        publicationsButton.deselect();
        queriesButton.deselect();
    }

    public void selectLayout(StatisticalResourcesToolStripLayoutEnum resourceType) {
        switch (resourceType) {
            case OPERATION_RESOURCES:
                datasetsButton.setVisible(true);
                publicationsButton.setVisible(true);
                queriesButton.setVisible(true);
                break;
            case STATISTIC_DESKTOP:
                datasetsButton.setVisible(false);
                publicationsButton.setVisible(false);
                queriesButton.setVisible(false);
                break;
        }
    }

}
