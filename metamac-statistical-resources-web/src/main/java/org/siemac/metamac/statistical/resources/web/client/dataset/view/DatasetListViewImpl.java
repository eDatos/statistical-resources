package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.base.view.StatisticalResourceBaseListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.NewStatisticalResourceWindow;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetListPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ImportDatasourcesWindow;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.NewDatasetWindow;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.web.common.client.listener.UploadListener;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DatasetListViewImpl extends StatisticalResourceBaseListViewImpl<DatasetListUiHandlers> implements DatasetListPresenter.DatasetListView {

    private CustomToolStripButton   sendToProductionValidationButton;
    private CustomToolStripButton   sendToDiffusionValidationButton;
    private CustomToolStripButton   rejectValidationButton;
    private CustomToolStripButton   programPublicationButton;
    private CustomToolStripButton   cancelProgrammedPublicationButton;
    private CustomToolStripButton   publishButton;
    private CustomToolStripButton   importDatasourcesButton;

    private NewDatasetWindow        newDatasetWindow;

    private ImportDatasourcesWindow importDatasourcesWindow;

    private String                  operationUrn;

    @Inject
    public DatasetListViewImpl() {
        super();

        // ToolStrip

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

        toolStrip.addSeparator();

        importDatasourcesButton = createImportDatasourcesButton();
        toolStrip.addButton(importDatasourcesButton);

        // Search

        searchSectionStack.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                getUiHandlers().retrieveDatasetsByStatisticalOperation(operationUrn, 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, searchSectionStack.getSearchCriteria());
            }
        });

        listGrid = new PaginatedCheckListGrid(StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                getUiHandlers().retrieveDatasetsByStatisticalOperation(operationUrn, firstResult, maxResults, null);
            }
        });
        listGrid.getListGrid().setAutoFitMaxRecords(StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
        listGrid.getListGrid().setAutoFitData(Autofit.VERTICAL);
        listGrid.getListGrid().setDataSource(new DatasetDS());
        listGrid.getListGrid().setUseAllDataSourceFields(false);
        listGrid.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                updateListGridButtonsVisibility();
            }
        });

        listGrid.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) { // Clicking checkBox will be ignored
                    String urn = ((DatasetRecord) event.getRecord()).getAttribute(DatasetDS.URN);
                    getUiHandlers().goToDataset(urn);
                }
            }
        });

        ListGridField fieldCode = new ListGridField(DatasetDS.CODE, getConstants().identifiableStatisticalResourceCode());
        fieldCode.setAlign(Alignment.LEFT);
        ListGridField fieldName = new ListGridField(DatasetDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        ListGridField status = new ListGridField(DatasetDS.PROC_STATUS, getConstants().datasetProcStatus());
        listGrid.getListGrid().setFields(fieldCode, fieldName, status);

        panel.addMember(listGrid);

        // Delete configuration window

        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteDatasets(getUrnsFromSelected());
            }
        });

        // Import datasources window

        importDatasourcesWindow = new ImportDatasourcesWindow();
        importDatasourcesWindow.setUploadListener(new UploadListener() {

            @Override
            public void uploadFailed(String errorMessage) {
                getUiHandlers().datasourcesImportationFailed(errorMessage);
            }
            @Override
            public void uploadComplete(String fileName) {
                getUiHandlers().datasourcesImportationSucceed(fileName);
            }
        });
    }

    @Override
    public void setDatasetPaginatedList(String operationUrn, GetDatasetVersionsResult result) {
        setOperation(operationUrn);
        setDatasetList(result.getDatasetVersionDtos());
        listGrid.refreshPaginationInfo(result.getFirstResultOut(), result.getDatasetVersionDtos().size(), result.getTotalResults());
    }

    @Override
    public void goToDatasetListLastPageAfterCreate() {
        listGrid.goToLastPageAfterCreate();
    }

    @Override
    public void setDsdsForRelatedDsd(GetDsdsPaginatedListResult result) {
        List<ExternalItemDto> externalItemsDtos = result.getDsdsList();
        newDatasetWindow.setExternalItemsForRelatedDsd(externalItemsDtos, result.getFirstResultOut(), externalItemsDtos.size(), result.getTotalResults());
    }

    @Override
    public void setStatisticalOperationsForDsdSelection(List<ExternalItemDto> results, ExternalItemDto defaultSelected) {
        newDatasetWindow.setStatisticalOperationsForRelatedDsd(results, defaultSelected);
    }

    private void setDatasetList(List<DatasetVersionDto> datasetsDtos) {
        DatasetRecord[] records = new DatasetRecord[datasetsDtos.size()];
        int index = 0;
        for (DatasetVersionDto datasetDto : datasetsDtos) {
            records[index++] = StatisticalResourcesRecordUtils.getDatasetRecord(datasetDto);
        }
        listGrid.getListGrid().setData(records);
    }

    public void setOperation(String operationUrn) {
        this.operationUrn = operationUrn;
        this.importDatasourcesWindow.setStatisticalOperationUrn(operationUrn);
    }

    public List<String> getUrnsFromSelected() {
        List<String> codes = new ArrayList<String>();
        for (ListGridRecord record : listGrid.getListGrid().getSelectedRecords()) {
            DatasetRecord schemeRecord = (DatasetRecord) record;
            codes.add(schemeRecord.getUrn());
        }
        return codes;
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == DatasetListPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (StatisticalResourcesToolStripButtonEnum.DATASETS.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
                            ((ToolStripButton) canvas[i]).select();
                        }
                    }
                }
                panel.addMember(content, 0);
            }
        } else {
            // To support inheritance in your views it is good practice to call super.setInSlot when you can't handle the call.
            // Who knows, maybe the parent class knows what to do with this slot.
            super.setInSlot(slot, content);
        }
    }

    @Override
    protected NewStatisticalResourceWindow getNewStatisticalResourceWindow() {
        return newDatasetWindow;
    }

    //
    // LISTGRID BUTTONS
    //

    // Create

    @Override
    public ClickHandler getNewButtonClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                newDatasetWindow = new NewDatasetWindow(getConstants().datasetCreate());
                newDatasetWindow.setUiHandlers(getUiHandlers());
                newDatasetWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (newDatasetWindow.validateForm()) {
                            getUiHandlers().createDataset(newDatasetWindow.getNewDatasetVersionDto(operationUrn));
                            newDatasetWindow.destroy();
                        }
                    }
                });
                newDatasetWindow.setDefaultLanguage(StatisticalResourcesDefaults.defaultLanguage);
                newDatasetWindow.setDefaultMaintainer(StatisticalResourcesDefaults.defaultAgency);
            }
        };
    }

    // Delete

    private void showDeleteButton() {
        // TODO Security
        deleteButton.show();
    }

    // Import datasources

    private CustomToolStripButton createImportDatasourcesButton() {
        CustomToolStripButton importDatasourcesButton = new CustomToolStripButton(getConstants().actionLoadDatasources(), org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE
                .importResource().getURL());
        // TODO Security importDatasourcesButton.setVisible(...);
        importDatasourcesButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                importDatasourcesWindow.show();
            }
        });
        return importDatasourcesButton;
    }

    // Send to production validation

    private CustomToolStripButton createSendToProductionValidationButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleSendToProductionValidation(), GlobalResources.RESOURCE.validateProduction().getURL());
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showSendtoProductionValidationButton() {
        // TODO Security
        sendToProductionValidationButton.show();
    }

    // Send to diffusion validation

    private CustomToolStripButton createSendToDiffusionValidation() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleSendToDiffusionValidation(), GlobalResources.RESOURCE.validateDiffusion().getURL());
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showSendtoDiffusionValidationButton() {
        // TODO Security
        sendToDiffusionValidationButton.show();
    }

    // Reject validation

    private CustomToolStripButton createRejectValidationButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleRejectValidation(), GlobalResources.RESOURCE.reject().getURL());
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showRejectValidationButton() {
        // TODO Security
        rejectValidationButton.show();
    }

    // Publish

    private CustomToolStripButton createPublishButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCyclePublish(), GlobalResources.RESOURCE.publish().getURL());
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showPublishButton() {
        // TODO Security
        publishButton.show();
    }

    // Program publication

    private CustomToolStripButton createProgramPublicationButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleProgramPublication(), GlobalResources.RESOURCE.programPublication().getURL());
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showProgramPublicationButton() {
        // TODO Security
        programPublicationButton.show();
    }

    // Cancel programmed publication

    private CustomToolStripButton createCancelProgrammedPublicationButton() {
        CustomToolStripButton button = new CustomToolStripButton(getConstants().lifeCycleCancelProgramedPublication(), GlobalResources.RESOURCE.reject().getURL());
        button.setVisibility(Visibility.HIDDEN);
        return button;
    }

    private void showCancelProgrammedPublicationButton() {
        // TODO Security
        cancelProgrammedPublicationButton.show();
    }

    // Visibility methods

    private void updateListGridButtonsVisibility() {
        if (listGrid.getListGrid().getSelectedRecords().length > 0) {
            showSelectionDependentButtons();
        } else {
            hideSelectionDependentButtons();
        }
    }

    private void showSelectionDependentButtons() {
        showDeleteButton();
        showSendtoProductionValidationButton();
        showSendtoDiffusionValidationButton();
        showRejectValidationButton();
        showPublishButton();
        showProgramPublicationButton();
        showCancelProgrammedPublicationButton();
    }

    private void hideSelectionDependentButtons() {
        deleteButton.hide();
        sendToProductionValidationButton.hide();
        sendToDiffusionValidationButton.hide();
        rejectValidationButton.hide();
        publishButton.hide();
        programPublicationButton.hide();
        cancelProgrammedPublicationButton.hide();
    }
}
