package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetListPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.NewDatasetWindow;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DatasetListViewImpl extends ViewWithUiHandlers<DatasetListUiHandlers> implements DatasetListPresenter.DatasetListView {

    private VLayout                  panel;

    private ToolStripButton          newDatasetButton;
    private ToolStripButton          deleteDatasetButton;

    private PaginatedCheckListGrid   datasetsList;

    private NewDatasetWindow         newDatasetWindow;
    private DeleteConfirmationWindow deleteConfirmationWindow;

    private String                   operationUrn;

    @Inject
    public DatasetListViewImpl() {
        super();

        // ToolStrip

        ToolStrip toolStrip = new ToolStrip();
        toolStrip.setWidth100();

        newDatasetButton = new ToolStripButton(getConstants().actionNew(), RESOURCE.newListGrid().getURL());
        newDatasetButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                newDatasetWindow = new NewDatasetWindow(getConstants().datasetCreate());
                newDatasetWindow.setUiHandlers(getUiHandlers());
                newDatasetWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (newDatasetWindow.validateForm()) {
                            getUiHandlers().createDataset(newDatasetWindow.getNewDatasetDto(operationUrn));
                            newDatasetWindow.destroy();
                        }
                    }
                });
            }
        });
        newDatasetButton.setVisibility(DatasetClientSecurityUtils.canCreateDataset() ? Visibility.VISIBLE : Visibility.HIDDEN);

        deleteDatasetButton = new ToolStripButton(getConstants().actionDelete(), RESOURCE.deleteListGrid().getURL());
        deleteDatasetButton.setVisibility(Visibility.HIDDEN);
        deleteDatasetButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                deleteConfirmationWindow.show();
            }
        });

        toolStrip.addButton(newDatasetButton);
        toolStrip.addButton(deleteDatasetButton);

        datasetsList = new PaginatedCheckListGrid(DatasetListPresenter.DATASET_LIST_MAX_RESULTS, new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                getUiHandlers().retrieveDatasetsByStatisticalOperation(operationUrn, firstResult, maxResults);
            }
        });
        datasetsList.getListGrid().setAutoFitMaxRecords(DatasetListPresenter.DATASET_LIST_MAX_RESULTS);
        datasetsList.getListGrid().setAutoFitData(Autofit.VERTICAL);
        datasetsList.getListGrid().setDataSource(new DatasetDS());
        datasetsList.getListGrid().setUseAllDataSourceFields(false);
        datasetsList.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                if (datasetsList.getListGrid().getSelectedRecords().length > 0) {
                    // Show delete button
                    showListGridDeleteButton();
                } else {
                    deleteDatasetButton.hide();
                }
            }
        });

        datasetsList.getListGrid().addRecordClickHandler(new RecordClickHandler() {

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
        datasetsList.getListGrid().setFields(fieldCode, fieldName, status);

        panel = new VLayout();
        panel.addMember(toolStrip);
        panel.addMember(datasetsList);

        deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().datasetDeleteConfirmationTitle(), getConstants().datasetDeleteConfirmation());
        deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);
        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteDatasets(getUrnsFromSelected());
            }
        });
    }

    @Override
    public void setDatasetPaginatedList(String operationUrn, GetDatasetsResult result) {
        setOperation(operationUrn);
        setDatasetList(result.getDatasetDtos());
        datasetsList.refreshPaginationInfo(result.getFirstResultOut(), result.getDatasetDtos().size(), result.getTotalResults());
    }

    @Override
    public void goToDatasetListLastPageAfterCreate() {
        datasetsList.goToLastPageAfterCreate();
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

    private void setDatasetList(List<DatasetDto> datasetsDtos) {
        DatasetRecord[] records = new DatasetRecord[datasetsDtos.size()];
        int index = 0;
        for (DatasetDto datasetDto : datasetsDtos) {
            records[index++] = StatisticalResourcesRecordUtils.getDatasetRecord(datasetDto);
        }
        datasetsList.getListGrid().setData(records);
    }

    public void setOperation(String operationUrn) {
        this.operationUrn = operationUrn;
    }

    public List<String> getUrnsFromSelected() {
        List<String> codes = new ArrayList<String>();
        for (ListGridRecord record : datasetsList.getListGrid().getSelectedRecords()) {
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

    private void showListGridDeleteButton() {
        if (DatasetClientSecurityUtils.canDeleteDataset()) {
            deleteDatasetButton.show();
        }
    }
}
