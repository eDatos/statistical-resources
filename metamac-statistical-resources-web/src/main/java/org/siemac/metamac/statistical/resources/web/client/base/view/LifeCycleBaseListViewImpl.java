package org.siemac.metamac.statistical.resources.web.client.base.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.LifeCycleBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.record.LifeCycleResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.statistical.resources.web.client.widgets.LifeCycleResourcePaginatedCheckListGrid;
import org.siemac.metamac.statistical.resources.web.client.widgets.VersionWindow;
import org.siemac.metamac.web.common.client.widgets.BaseAdvancedSearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class LifeCycleBaseListViewImpl<C extends UiHandlers> extends ViewWithUiHandlers<C> implements LifeCycleBaseListPresenter.LifeCycleBaseListView {

    protected VLayout                                 panel;

    protected ToolStrip                               toolStrip;
    protected CustomToolStripButton                   newButton;
    protected CustomToolStripButton                   deleteButton;
    protected CustomToolStripButton                   sendToProductionValidationButton;
    protected CustomToolStripButton                   sendToDiffusionValidationButton;
    protected CustomToolStripButton                   rejectValidationButton;
    protected CustomToolStripButton                   publishButton;
    protected CustomToolStripButton                   versionButton;

    protected DeleteConfirmationWindow                deleteConfirmationWindow;

    protected LifeCycleResourcePaginatedCheckListGrid listGrid;

    public LifeCycleBaseListViewImpl() {
        super();

        // ToolStrip

        toolStrip = new ToolStrip();
        toolStrip.setWidth100();

        newButton = createNewButton();
        toolStrip.addButton(newButton);

        deleteButton = createDeleteButton();
        toolStrip.addButton(deleteButton);

        sendToProductionValidationButton = createSendToProductionValidationButton();
        toolStrip.addButton(sendToProductionValidationButton);

        sendToDiffusionValidationButton = createSendToDiffusionValidation();
        toolStrip.addButton(sendToDiffusionValidationButton);

        rejectValidationButton = createRejectValidationButton();
        toolStrip.addButton(rejectValidationButton);

        publishButton = createPublishButton();
        toolStrip.addButton(publishButton);

        versionButton = createVersionButton();
        toolStrip.addButton(versionButton);

        // ListGrid

        listGrid = new LifeCycleResourcePaginatedCheckListGrid(StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                LifeCycleBaseListViewImpl.this.retrieveResultSet(firstResult, maxResults);
            }
        });
        listGrid.getListGrid().setAutoFitMaxRecords(StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
        listGrid.getListGrid().setUseAllDataSourceFields(false);
        listGrid.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                updateListGridButtonsVisibility();
            }
        });
        listGrid.setHeight100();
        listGrid.getListGrid().setCanMultiSort(Boolean.FALSE);

        // Panel

        panel = new VLayout();

        panel.addMember(toolStrip);
        panel.addMember(createAdvacedSearchSectionStack());
        panel.addMember(listGrid);

        // Delete confirmation window

        deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().lifeCycleDeleteConfirmationTitle(), getConstants().lifeCycleDeleteConfirmation());
        deleteConfirmationWindow.setVisible(false);
    }

    protected List<String> getSelectedResourcesUrns() {
        List<String> urns = new ArrayList<String>();
        for (ListGridRecord record : listGrid.getListGrid().getSelectedRecords()) {
            LifeCycleResourceRecord lifeCycleRecord = (LifeCycleResourceRecord) record;
            urns.add(lifeCycleRecord.getUrn());
        }
        return urns;
    }

    //
    // LISTGRID BUTTONS
    //

    // Create

    private CustomToolStripButton createNewButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().actionNew(), RESOURCE.newListGrid().getURL());
        button.addClickHandler(getNewButtonClickHandler());
        button.setVisible(canCreate());
        return button;
    }
    // Delete

    private CustomToolStripButton createDeleteButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().actionDelete(), RESOURCE.deleteListGrid().getURL());
        button.setVisible(false);
        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                deleteConfirmationWindow.show();
            }
        });
        return button;
    }

    private void showDeleteButton(ListGridRecord[] records) {
        boolean canBeDeleted = true;
        for (ListGridRecord record : records) {
            if (!canDelete(record)) {
                canBeDeleted = false;
                break;
            }
        }
        if (canBeDeleted) {
            deleteButton.show();
        }
    }

    // Send to production validation

    private CustomToolStripButton createSendToProductionValidationButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleSendToProductionValidation(), GlobalResources.RESOURCE.validateProduction().getURL());
        button.setVisible(false);
        button.addClickHandler(getSendToProductionValidationClickHandler());
        return button;
    }

    private void showSendToProductionValidationButton(ListGridRecord[] records) {
        boolean canSendToProductionValidation = true;
        for (ListGridRecord record : records) {
            if (!canSendToProductionValidation(record)) {
                canSendToProductionValidation = false;
                break;
            }
        }
        if (canSendToProductionValidation) {
            sendToProductionValidationButton.show();
        }
    }

    // Send to diffusion validation

    private CustomToolStripButton createSendToDiffusionValidation() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleSendToDiffusionValidation(), GlobalResources.RESOURCE.validateDiffusion().getURL());
        button.setVisible(false);
        button.addClickHandler(getSendToDiffusionValidationClickHandler());
        return button;
    }

    private void showSendtoDiffusionValidationButton(ListGridRecord[] records) {
        boolean canSendToDiffusionValidation = true;
        for (ListGridRecord record : records) {
            if (!canSendToDiffusionValidation(record)) {
                canSendToDiffusionValidation = false;
                break;
            }
        }
        if (canSendToDiffusionValidation) {
            sendToDiffusionValidationButton.show();
        }
    }

    // Reject validation

    private CustomToolStripButton createRejectValidationButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleRejectValidation(), GlobalResources.RESOURCE.reject().getURL());
        button.setVisible(false);
        button.addClickHandler(getRejectValidationClickHandler());
        return button;
    }

    private void showRejectValidationButton(ListGridRecord[] records) {
        boolean canRejectValidation = true;
        for (ListGridRecord record : records) {
            if (!canRejectValidation(record)) {
                canRejectValidation = false;
                break;
            }
        }
        if (canRejectValidation) {
            rejectValidationButton.show();
        }
    }

    // Publish

    private CustomToolStripButton createPublishButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCyclePublish(), GlobalResources.RESOURCE.publish().getURL());
        button.setVisible(false);
        button.addClickHandler(getPublishClickHandler());
        return button;
    }

    private void showPublishButton(ListGridRecord[] records) {
        boolean canPublish = true;
        for (ListGridRecord record : records) {
            if (!canPublish(record)) {
                canPublish = false;
                break;
            }
        }
        if (canPublish) {
            publishButton.show();
        }
    }

    // Version

    private CustomToolStripButton createVersionButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleVersioning(), GlobalResources.RESOURCE.version().getURL());
        button.setVisible(false);
        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final VersionWindow versionWindow = new VersionWindow(getConstants().lifeCycleVersioning());
                versionWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (versionWindow.validateForm()) {
                            version(versionWindow.getSelectedVersion());
                            versionWindow.destroy();
                        }
                    }
                });
            }
        });
        return button;
    }

    protected void showVersionButton(ListGridRecord[] records) {
        boolean canVersion = true;
        for (ListGridRecord record : records) {
            if (!canVersion(record)) {
                canVersion = false;
                break;
            }
        }
        if (canVersion) {
            versionButton.show();
        }
    }

    // Visibility methods

    protected void updateListGridButtonsVisibility() {
        hideSelectionDependentButtons();
        if (listGrid.getListGrid().getSelectedRecords().length > 0) {
            showSelectionDependentButtons(listGrid.getListGrid().getSelectedRecords());
        }
    }

    protected void showSelectionDependentButtons(ListGridRecord[] records) {
        showDeleteButton(records);
        showSendToProductionValidationButton(records);
        showSendtoDiffusionValidationButton(records);
        showRejectValidationButton(records);
        showPublishButton(records);
        showVersionButton(records);
    }

    protected void hideSelectionDependentButtons() {
        deleteButton.hide();
        sendToProductionValidationButton.hide();
        sendToDiffusionValidationButton.hide();
        rejectValidationButton.hide();
        publishButton.hide();
        versionButton.hide();
    }

    //
    // ABSTRACT METHODS
    //

    protected abstract BaseAdvancedSearchSectionStack createAdvacedSearchSectionStack();

    protected abstract void retrieveResultSet(int firstResult, int maxResults);

    protected abstract ClickHandler getNewButtonClickHandler();
    protected abstract ClickHandler getSendToProductionValidationClickHandler();
    protected abstract ClickHandler getSendToDiffusionValidationClickHandler();
    protected abstract ClickHandler getRejectValidationClickHandler();
    protected abstract ClickHandler getPublishClickHandler();

    protected abstract boolean canCreate();
    protected abstract boolean canDelete(ListGridRecord record);
    protected abstract boolean canSendToProductionValidation(ListGridRecord record);
    protected abstract boolean canSendToDiffusionValidation(ListGridRecord record);
    protected abstract boolean canRejectValidation(ListGridRecord record);
    protected abstract boolean canPublish(ListGridRecord record);

    protected abstract boolean canVersion(ListGridRecord record);

    protected abstract void version(VersionTypeEnum versionType);
}
