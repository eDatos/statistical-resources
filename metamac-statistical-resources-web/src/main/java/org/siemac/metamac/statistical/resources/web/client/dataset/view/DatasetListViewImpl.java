package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetListPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.NewDatasetWindow;
import org.siemac.metamac.statistical.resources.web.client.enums.ToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetPaginatedListResult;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
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
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DatasetListViewImpl extends ViewImpl implements DatasetListPresenter.DatasetListView {

    private DatasetListUiHandlers    uiHandlers;
    private VLayout                  panel;

    private ToolStripButton          newDatasetButton;
    private ToolStripButton          deleteDatasetButton;

    private SearchSectionStack       searchSectionStack;

    private PaginatedCheckListGrid   datasetsList;

    private NewDatasetWindow         newDatasetWindow;
    private DeleteConfirmationWindow deleteConfirmationWindow;

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
                newDatasetWindow.setUiHandlers(uiHandlers);
                newDatasetWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (newDatasetWindow.validateForm()) {
                            uiHandlers.createDataset(newDatasetWindow.getNewDatasetDto());
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

        // Search

        searchSectionStack = new SearchSectionStack();
        searchSectionStack.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                uiHandlers.retrieveDatasets(DatasetListPresenter.DATASET_LIST_FIRST_RESULT, DatasetListPresenter.DATASET_LIST_MAX_RESULTS, searchSectionStack.getSearchCriteria());
            }
        });

        // Concepts scheme list

        datasetsList = new PaginatedCheckListGrid(DatasetListPresenter.DATASET_LIST_MAX_RESULTS, new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                uiHandlers.retrieveDatasets(firstResult, maxResults, null);
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
                    uiHandlers.goToDataset(urn);
                }
            }
        });

        ListGridField fieldCode = new ListGridField(DatasetDS.IDENTIFIER, getConstants().datasetIdentifier());
        fieldCode.setAlign(Alignment.LEFT);
        ListGridField fieldName = new ListGridField(DatasetDS.TITLE, getConstants().datasetTitle());
        ListGridField status = new ListGridField(DatasetDS.PROC_STATUS, getConstants().datasetProcStatus());
        datasetsList.getListGrid().setFields(fieldCode, fieldName, status);

        panel = new VLayout();
        panel.addMember(toolStrip);
        panel.addMember(searchSectionStack);
        panel.addMember(datasetsList);

        deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().datasetDeleteConfirmationTitle(), getConstants().datasetDeleteConfirmation());
        deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);
        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.deleteDatasets(getIdsFromSelected());
                deleteConfirmationWindow.hide();
            }
        });
    }

    @Override
    public void setDatasetPaginatedList(GetDatasetPaginatedListResult datasetsPaginatedList) {
        setDatasetList(datasetsPaginatedList.getDatasetList());
        datasetsList.refreshPaginationInfo(datasetsPaginatedList.getPageNumber(), datasetsPaginatedList.getDatasetList().size(), datasetsPaginatedList.getTotalResults());
    }

    @Override
    public void goToDatasetListLastPageAfterCreate() {
        datasetsList.goToLastPageAfterCreate();
    }

    private void setDatasetList(List<DatasetDto> datasetsDtos) {
        DatasetRecord[] records = new DatasetRecord[datasetsDtos.size()];
        int index = 0;
        for (DatasetDto datasetDto : datasetsDtos) {
            records[index++] = DatasetRecordUtils.getDatasetRecord(datasetDto);
        }
        datasetsList.getListGrid().setData(records);
    }

    public List<Long> getIdsFromSelected() {
        List<Long> codes = new ArrayList<Long>();
        for (ListGridRecord record : datasetsList.getListGrid().getSelectedRecords()) {
            DatasetRecord schemeRecord = (DatasetRecord) record;
            codes.add(schemeRecord.getId());
        }
        return codes;
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == DatasetListPresenter.TYPE_SetContextAreaContentDatasetListToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (ToolStripButtonEnum.DATASETS.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
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
    public void setUiHandlers(DatasetListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public void clearSearchSection() {
        searchSectionStack.reset();
    }

    private void showListGridDeleteButton() {
        if (DatasetClientSecurityUtils.canDeleteDataset()) {
            deleteDatasetButton.show();
        }
    }

}
