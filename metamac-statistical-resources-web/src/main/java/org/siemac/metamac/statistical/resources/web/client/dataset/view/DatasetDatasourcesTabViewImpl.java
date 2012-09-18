package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasourceDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter.DatasetDatasourcesTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.NewDatasetWindow;
import org.siemac.metamac.statistical.resources.web.client.utils.RecordUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListResult;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;


public class DatasetDatasourcesTabViewImpl extends ViewImpl implements DatasetDatasourcesTabView {
    
    private DatasetDatasourcesTabUiHandlers uiHandlers;
    private VLayout                         panel;
    
    private DatasourcesListPanel            datasourcesListPanel;

    
    private String datasetUrn;
    
    public DatasetDatasourcesTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(5);
        panel.setHeight100();
        
        datasourcesListPanel = new DatasourcesListPanel();
        
        panel.addMember(datasourcesListPanel);
    }
    
    @Override
    public void setDatasourcesPaginatedList(String datasetUrn, GetDatasourcesByDatasetPaginatedListResult result) {
        this.datasetUrn = datasetUrn;
        datasourcesListPanel.setDatasourcesPaginatedList(result);
    }
    
    @Override
    public Widget asWidget() {
        return panel;
    }
    
    @Override
    public void setUiHandlers(DatasetDatasourcesTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
    

    
    private class DatasourcesListPanel extends VLayout {
        private ToolStripButton                 newDatasourceButton;
        private ToolStripButton                 deleteDatasourceButton;
        private PaginatedCheckListGrid          datasourcesList;

        private DeleteConfirmationWindow        deleteConfirmationWindow;
        
        
        public DatasourcesListPanel() {
          //Toolstrip
            
            ToolStrip toolStrip = new ToolStrip();
            toolStrip.setWidth100();

            newDatasourceButton = new ToolStripButton(getConstants().actionNew(), RESOURCE.newListGrid().getURL());

            newDatasourceButton.setVisibility(DatasetClientSecurityUtils.canCreateDatasource() ? Visibility.VISIBLE : Visibility.HIDDEN);

            deleteDatasourceButton = new ToolStripButton(getConstants().actionDelete(), RESOURCE.deleteListGrid().getURL());
            deleteDatasourceButton.setVisibility(Visibility.HIDDEN);

            toolStrip.addButton(newDatasourceButton);
            toolStrip.addButton(deleteDatasourceButton);
            
            //List
            
            datasourcesList = new PaginatedCheckListGrid(DatasetDatasourcesTabPresenter.DATASOURCE_LIST_MAX_RESULTS, new PaginatedAction() {
                @Override
                public void retrieveResultSet(int firstResult, int maxResults) {
                    uiHandlers.retrieveDatasourcesByDataset(datasetUrn, firstResult, maxResults);
                }
            });
            
            datasourcesList.getListGrid().setAutoFitMaxRecords(DatasetDatasourcesTabPresenter.DATASOURCE_LIST_MAX_RESULTS);
            datasourcesList.getListGrid().setAutoFitData(Autofit.VERTICAL);
            datasourcesList.getListGrid().setDataSource(new DatasourceDS());
            datasourcesList.getListGrid().setUseAllDataSourceFields(false);
            
            ListGridField fieldCode = new ListGridField(DatasourceDS.IDENTIFIER, getConstants().datasetIdentifier());
            fieldCode.setAlign(Alignment.LEFT);
            ListGridField fieldName = new ListGridField(DatasourceDS.TITLE, getConstants().datasetTitle());
            datasourcesList.getListGrid().setFields(fieldCode, fieldName);
            
            //Panel conf
            addMember(toolStrip);
            addMember(datasourcesList);
            bindEvents();
        }
        
        private void bindEvents() {
            datasourcesList.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {
                @Override
                public void onSelectionChanged(SelectionEvent event) {
                    if (datasourcesList.getListGrid().getSelectedRecords().length > 0) {
                        showListGridDeleteButton();
                    } else {
                        deleteDatasourceButton.hide();
                    }
                }
            });
            /*
            datasourcesList.getListGrid().addRecordClickHandler(new RecordClickHandler() {

                @Override
                public void onRecordClick(RecordClickEvent event) {
                    if (event.getFieldNum() != 0) { // Clicking checkBox will be ignored
                        String urn = ((DatasetRecord) event.getRecord()).getAttribute(DatasetDS.URN);
                        uiHandlers.goToDataset(urn);
                    }
                }
            });*/
            newDatasourceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
              
                }
            });
            deleteDatasourceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    deleteConfirmationWindow.show();
                }
            });
        }
        
        private void showListGridDeleteButton() {
            if (DatasetClientSecurityUtils.canDeleteDatasource()) {
                deleteDatasourceButton.show();
            }
        }
        
        public void setDatasourcesPaginatedList(GetDatasourcesByDatasetPaginatedListResult result) {
            DatasourceRecord[] records = new DatasourceRecord[result.getDatasourcesList().size()];
            int index = 0;
            for (DatasourceDto datasourceDto : result.getDatasourcesList()) {
                records[index++] = RecordUtils.getDatasourceRecord(datasourceDto);
            }
            datasourcesList.getListGrid().setData(records);
            datasourcesList.refreshPaginationInfo(result.getPageNumber(), result.getDatasourcesList().size(), result.getTotalResults());
        }
    }
}
