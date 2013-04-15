package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasourceDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter.DatasetDatasourcesTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasourceContentEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasourceResourceIdentifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasourceResourceIdentifiersForm;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetResult;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Visibility;
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

public class DatasetDatasourcesTabViewImpl extends ViewWithUiHandlers<DatasetDatasourcesTabUiHandlers> implements DatasetDatasourcesTabView {

    private VLayout              panel;

    private DatasourcesListPanel datasourcesListPanel;
    private DatasourceFormPanel  datasourceFormPanel;

    private String               datasetUrn;

    public DatasetDatasourcesTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(5);
        panel.setHeight100();

        datasourcesListPanel = new DatasourcesListPanel();
        datasourcesListPanel.setWidth("99%");
        datasourceFormPanel = new DatasourceFormPanel();
        datasourceFormPanel.setWidth("99%");

        panel.addMember(datasourcesListPanel);
        panel.addMember(datasourceFormPanel);
    }

    @Override
    public void setDatasources(String datasetUrn, List<DatasourceDto> datasources) {
        this.datasetUrn = datasetUrn;
        datasourcesListPanel.setDatasourcesList(datasources);
    }

    @Override
    public void setDatasource(DatasourceDto datasourceDto) {
        datasourceFormPanel.selectDatasource(datasourceDto);
    }
    
    private void hideDetailView() {
        datasourceFormPanel.hide();
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
    
    public List<String> getUrnsFromSelected() {
        List<String> codes = new ArrayList<String>();
        for (ListGridRecord record : datasourcesListPanel.datasourcesList.getSelectedRecords()) {
            DatasourceRecord datasourceRecord = (DatasourceRecord) record;
            codes.add(datasourceRecord.getUrn());
        }
        return codes;
    }

    private class DatasourcesListPanel extends VLayout {

        private ToolStripButton          newDatasourceButton;
        private ToolStripButton          deleteDatasourceButton;
        private CustomListGrid   datasourcesList;

        private DeleteConfirmationWindow deleteConfirmationWindow;

        public DatasourcesListPanel() {
            // Toolstrip

            ToolStrip toolStrip = new ToolStrip();
            toolStrip.setWidth100();

            newDatasourceButton = new ToolStripButton(getConstants().actionNew(), RESOURCE.newListGrid().getURL());

            newDatasourceButton.setVisibility(DatasetClientSecurityUtils.canCreateDatasource() ? Visibility.VISIBLE : Visibility.HIDDEN);

            deleteDatasourceButton = new ToolStripButton(getConstants().actionDelete(), RESOURCE.deleteListGrid().getURL());
            deleteDatasourceButton.setVisibility(Visibility.HIDDEN);

            toolStrip.addButton(newDatasourceButton);
            toolStrip.addButton(deleteDatasourceButton);

            // List

            datasourcesList = new CustomListGrid();
            datasourcesList.setAutoFitMaxRecords(DatasetDatasourcesTabPresenter.DATASOURCE_LIST_MAX_RESULTS);
            datasourcesList.setAutoFitData(Autofit.VERTICAL);
            datasourcesList.setDataSource(new DatasourceDS());
            datasourcesList.setUseAllDataSourceFields(false);
            

            ListGridField fieldCode = new ListGridField(DatasourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
            fieldCode.setAlign(Alignment.LEFT);
            ListGridField fieldName = new ListGridField(DatasourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());
            datasourcesList.setFields(fieldCode, fieldName);

            deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().actionConfirmDeleteTitle(), getConstants().datasourceDeleteConfirmation());
            deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);


            // Panel conf
            addMember(toolStrip);
            addMember(datasourcesList);
            bindEvents();
        }

        private void bindEvents() {
            datasourcesList.addSelectionChangedHandler(new SelectionChangedHandler() {

                @Override
                public void onSelectionChanged(SelectionEvent event) {
                    if (datasourcesList.getSelectedRecords().length > 0) {
                        showListGridDeleteButton();
                    } else {
                        deleteDatasourceButton.hide();
                    }
                }
            });

            datasourcesList.addRecordClickHandler(new RecordClickHandler() {

                @Override
                public void onRecordClick(RecordClickEvent event) {
                    if (event.getFieldNum() != 0) { // Clicking checkBox will be ignored
                        DatasourceRecord record = ((DatasourceRecord) event.getRecord());
                        datasourceFormPanel.selectDatasource((DatasourceDto) record.getAttributeAsObject(DatasourceDS.DTO));
                    }
                }
            });
            newDatasourceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    datasourceFormPanel.createDatasource();
                }
            });
            deleteDatasourceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    deleteConfirmationWindow.show();
                }
            });
            deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().deleteDatasources(getUrnsFromSelected());
                    hideDetailView();
                }
            });
        }

        private void showListGridDeleteButton() {
            if (DatasetClientSecurityUtils.canDeleteDatasource()) {
                deleteDatasourceButton.show();
            }
        }

        public void setDatasourcesList(List<DatasourceDto> datasources) {
            DatasourceRecord[] records = new DatasourceRecord[datasources.size()];
            int index = 0;
            for (DatasourceDto datasourceDto : datasources) {
                records[index++] = StatisticalResourcesRecordUtils.getDatasourceRecord(datasourceDto);
            }
            datasourcesList.setData(records);
        }
    }



    private class DatasourceFormPanel extends VLayout {

        private MainFormLayout                           mainFormLayout;
        private DatasourceResourceIdentifiersForm        identifiersForm;
        private DatasourceResourceIdentifiersEditionForm identifiersEditionForm;
        private DatasourceContentEditionForm             contentEditionForm;

        private DatasourceDto                            datasource;

        public DatasourceFormPanel() {
            super();
            setWidth("99%");

            mainFormLayout = new MainFormLayout();
            mainFormLayout.setMargin(0);

            this.addMember(mainFormLayout);
            createViewForm();
            createEditionForm();

            bindEvents();
            this.hide();
        }

        private void bindEvents() {
            mainFormLayout.getSave().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().saveDatasource(getDatasource());
                }
            });

            mainFormLayout.getCancelToolStripButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (isCreationMode()) {
                        DatasourceFormPanel.this.hide();
                    }
                }
            });
        }

        private void createViewForm() {
            identifiersForm = new DatasourceResourceIdentifiersForm();
            mainFormLayout.addViewCanvas(identifiersForm);
        }

        private void createEditionForm() {
            identifiersEditionForm = new DatasourceResourceIdentifiersEditionForm();
            contentEditionForm = new DatasourceContentEditionForm();
            mainFormLayout.addEditionCanvas(identifiersEditionForm);
            mainFormLayout.addEditionCanvas(contentEditionForm);
        }

        private void createDatasource() {
            this.datasource = new DatasourceDto();

            mainFormLayout.setTitleLabelContents(getConstants().datasourceNew());
            mainFormLayout.setEditionMode();
            fillViewForm(datasource);
            fillEditionForm(datasource);

            mainFormLayout.redraw();
            this.show();
        }

        private void selectDatasource(DatasourceDto datasourceDto) {
            this.datasource = datasourceDto;
            mainFormLayout.setTitleLabelContents(datasourceDto.getCode());
            mainFormLayout.setViewMode();
            fillViewForm(datasource);
            fillEditionForm(datasource);
            mainFormLayout.redraw();
            this.show();
        }

        private void fillViewForm(DatasourceDto datasourceDto) {
            identifiersForm.setDatasourceDto(datasourceDto);
        }

        private void fillEditionForm(DatasourceDto datasourceDto) {
            identifiersEditionForm.setDatasourceDto(datasourceDto);
        }

        private DatasourceDto getDatasource() {
            datasource = identifiersEditionForm.getDatasourceDto(datasource);
            return datasource;
        }

        private boolean isCreationMode() {
            return StringUtils.isEmpty(this.datasource.getUrn());
        }
    }
}
