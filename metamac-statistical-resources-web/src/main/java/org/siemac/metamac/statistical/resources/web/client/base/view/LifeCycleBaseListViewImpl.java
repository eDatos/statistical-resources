package org.siemac.metamac.statistical.resources.web.client.base.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.LifeCycleBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.record.LifeCycleResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.statistical.resources.web.client.widgets.LifeCycleResourcePaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class LifeCycleBaseListViewImpl<C extends UiHandlers> extends ViewWithUiHandlers<C> implements LifeCycleBaseListPresenter.LifeCycleBaseListView {

    protected VLayout                                 panel;
    protected SearchSectionStack                      searchSectionStack;

    protected ToolStrip                               toolStrip;
    protected CustomToolStripButton                   newButton;
    protected CustomToolStripButton                   deleteButton;
    protected CustomToolStripButton                   sendToProductionValidationButton;
    protected CustomToolStripButton                   sendToDiffusionValidationButton;
    protected CustomToolStripButton                   rejectValidationButton;
    protected CustomToolStripButton                   programPublicationButton;
    protected CustomToolStripButton                   cancelProgrammedPublicationButton;
    protected CustomToolStripButton                   publishButton;

    protected DeleteConfirmationWindow                deleteConfirmationWindow;

    protected LifeCycleResourcePaginatedCheckListGrid listGrid;

    public LifeCycleBaseListViewImpl() {
        super();

        // Search

        searchSectionStack = new SearchSectionStack();

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

        programPublicationButton = createProgramPublicationButton();
        toolStrip.addButton(programPublicationButton);

        cancelProgrammedPublicationButton = createCancelProgrammedPublicationButton();
        toolStrip.addButton(cancelProgrammedPublicationButton);

        // ListGrid

        listGrid = new LifeCycleResourcePaginatedCheckListGrid(StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                retrieveResultSet(firstResult, maxResults);
            }
        });
        listGrid.getListGrid().setAutoFitMaxRecords(StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
        listGrid.getListGrid().setAutoFitData(Autofit.VERTICAL);
        listGrid.getListGrid().setUseAllDataSourceFields(false);
        listGrid.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                updateListGridButtonsVisibility();
            }
        });

        // Panel

        panel = new VLayout();
        panel.addMember(toolStrip);
        panel.addMember(searchSectionStack);
        panel.addMember(listGrid);

        // Delete confirmation window

        deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().lifeCycleDeleteConfirmationTitle(), getConstants().lifeCycleDeleteConfirmation());
        deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);
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
        // TODO Security
        return button;
    }

    // Delete

    private CustomToolStripButton createDeleteButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().actionDelete(), RESOURCE.deleteListGrid().getURL());
        button.setVisibility(Visibility.HIDDEN);
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
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showSendToProductionValidationButton(ListGridRecord[] records) {
        boolean canSendToProductionValidation = true;
        for (ListGridRecord record : records) {
            if ((!ProcStatusEnum.DRAFT.equals(((LifeCycleResourceRecord) record).getProcStatusEnum()) && ProcStatusEnum.VALIDATION_REJECTED.equals(((LifeCycleResourceRecord) record)
                    .getProcStatusEnum())) || !canSendToProductionValidation(record)) {
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
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showSendtoDiffusionValidationButton(ListGridRecord[] records) {
        boolean canSendToDiffusionValidation = true;
        for (ListGridRecord record : records) {
            if (!ProcStatusEnum.PRODUCTION_VALIDATION.equals(((LifeCycleResourceRecord) record).getProcStatusEnum()) || !canSendToDiffusionValidation(record)) {
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
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showRejectValidationButton(ListGridRecord[] records) {
        boolean canRejectValidation = true;
        for (ListGridRecord record : records) {
            if ((!ProcStatusEnum.PRODUCTION_VALIDATION.equals(((LifeCycleResourceRecord) record).getProcStatusEnum()) && !ProcStatusEnum.DIFFUSION_VALIDATION.equals(((LifeCycleResourceRecord) record)
                    .getProcStatusEnum())) || !canRejectValidation(record)) {
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
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showPublishButton(ListGridRecord[] records) {
        boolean canPublish = true;
        for (ListGridRecord record : records) {
            if (!ProcStatusEnum.DIFFUSION_VALIDATION.equals(((LifeCycleResourceRecord) record).getProcStatusEnum()) || !canPublish(record)) {
                canPublish = false;
                break;
            }
        }
        if (canPublish) {
            publishButton.show();
        }
    }

    // Program publication

    private CustomToolStripButton createProgramPublicationButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleProgramPublication(), GlobalResources.RESOURCE.programPublication().getURL());
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showProgramPublicationButton(ListGridRecord[] records) {
        boolean canProgramPublication = true;
        for (ListGridRecord record : records) {
            if (!ProcStatusEnum.DIFFUSION_VALIDATION.equals(((LifeCycleResourceRecord) record).getProcStatusEnum()) || !canProgramPublication(record)) {
                canProgramPublication = false;
                break;
            }
        }
        if (canProgramPublication) {
            programPublicationButton.show();
        }
    }

    // Cancel programmed publication

    private CustomToolStripButton createCancelProgrammedPublicationButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleCancelProgramedPublication(), GlobalResources.RESOURCE.reject().getURL());
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showCancelProgrammedPublicationButton(ListGridRecord[] records) {
        boolean canCancelProgrammedPublication = true;
        for (ListGridRecord record : records) {
            if (!ProcStatusEnum.PUBLISHED.equals(((LifeCycleResourceRecord) record).getProcStatusEnum()) || !canCancelProgrammedPublication(record)) {
                canCancelProgrammedPublication = false;
                break;
            }
        }
        if (canCancelProgrammedPublication) {
            cancelProgrammedPublicationButton.show();
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
        showProgramPublicationButton(records);
        showCancelProgrammedPublicationButton(records);
    }

    protected void hideSelectionDependentButtons() {
        deleteButton.hide();
        sendToProductionValidationButton.hide();
        sendToDiffusionValidationButton.hide();
        rejectValidationButton.hide();
        publishButton.hide();
        programPublicationButton.hide();
        cancelProgrammedPublicationButton.hide();
    }

    //
    // ABSTRACT METHODS
    //

    public abstract ClickHandler getNewButtonClickHandler();
    public abstract void retrieveResultSet(int firstResult, int maxResults);
    protected abstract boolean canDelete(ListGridRecord record);
    protected abstract boolean canSendToProductionValidation(ListGridRecord record);
    protected abstract boolean canSendToDiffusionValidation(ListGridRecord record);
    protected abstract boolean canRejectValidation(ListGridRecord record);
    protected abstract boolean canPublish(ListGridRecord record);
    protected abstract boolean canProgramPublication(ListGridRecord record);
    protected abstract boolean canCancelProgrammedPublication(ListGridRecord record);
}
