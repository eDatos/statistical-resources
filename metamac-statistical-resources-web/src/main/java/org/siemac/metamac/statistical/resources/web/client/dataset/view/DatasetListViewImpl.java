package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.Date;
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
import org.siemac.metamac.statistical.resources.web.client.utils.ResourceFieldUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.ProgramPublicationWindow;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.web.common.client.listener.UploadListener;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DatasetListViewImpl extends StatisticalResourceBaseListViewImpl<DatasetListUiHandlers> implements DatasetListPresenter.DatasetListView {

    private CustomToolStripButton   importDatasourcesButton;

    private NewDatasetWindow        newDatasetWindow;

    private ImportDatasourcesWindow importDatasourcesWindow;

    private String                  operationUrn;

    @Inject
    public DatasetListViewImpl() {
        super();

        // ToolStrip

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

        // ListGrid

        listGrid.getListGrid().setDataSource(new DatasetDS());
        listGrid.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) { // Clicking checkBox will be ignored
                    String urn = ((DatasetRecord) event.getRecord()).getAttribute(DatasetDS.URN);
                    getUiHandlers().goToDataset(urn);
                }
            }
        });
        listGrid.getListGrid().setFields(ResourceFieldUtils.getDatasetListGridFields());

        // Delete configuration window

        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteDatasets(getSelectedResourcesUrns());
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
    // LISTGRID
    //

    @Override
    protected void retrieveResultSet(int firstResult, int maxResults) {
        getUiHandlers().retrieveDatasetsByStatisticalOperation(operationUrn, firstResult, maxResults, null);
    }

    //
    // LISTGRID BUTTONS
    //

    // Create

    @Override
    protected ClickHandler getNewButtonClickHandler() {
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

    // Sent to production validation

    @Override
    protected ClickHandler getSendToProductionValidationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<DatasetVersionDto> datasetVersionDtos = StatisticalResourcesRecordUtils.getDatasetVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().sendToProductionValidation(datasetVersionDtos);
            }
        };
    }

    // Send to diffusion validation

    @Override
    protected ClickHandler getSendToDiffusionValidationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<DatasetVersionDto> datasetVersionDtos = StatisticalResourcesRecordUtils.getDatasetVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().sendToDiffusionValidation(datasetVersionDtos);
            }
        };
    }

    // Reject validation

    @Override
    protected ClickHandler getRejectValidationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<DatasetVersionDto> datasetVersionDtos = StatisticalResourcesRecordUtils.getDatasetVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().rejectValidation(datasetVersionDtos);
            }
        };
    }

    // Publish

    @Override
    protected ClickHandler getPublishClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<DatasetVersionDto> datasetVersionDtos = StatisticalResourcesRecordUtils.getDatasetVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().publish(datasetVersionDtos);
            }
        };
    }

    // Program publication

    @Override
    protected ClickHandler getProgramPublicationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                final ProgramPublicationWindow window = new ProgramPublicationWindow(getConstants().lifeCycleProgramPublication());
                window.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (window.validateForm()) {
                            Date selectedDate = window.getSelectedDate();
                            // TODO Send to date and hour selected to service
                            List<DatasetVersionDto> datasetVersionDtos = StatisticalResourcesRecordUtils.getDatasetVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                            getUiHandlers().programPublication(datasetVersionDtos);
                            window.destroy();
                        }
                    }
                });
            }
        };
    }

    // Cancel programmed publication

    @Override
    protected ClickHandler getCancelProgrammedPublicationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // TODO Auto-generated method stub

            }
        };
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

    //
    // LISTGRID ACTIONS
    //

    @Override
    protected boolean canDelete(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canSendToProductionValidation(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canSendToDiffusionValidation(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canRejectValidation(ListGridRecord record) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean canPublish(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canProgramPublication(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canCancelProgrammedPublication(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canVersion(ListGridRecord record) {
        // TODO Auto-generated method stub
        return true;
    }
}
