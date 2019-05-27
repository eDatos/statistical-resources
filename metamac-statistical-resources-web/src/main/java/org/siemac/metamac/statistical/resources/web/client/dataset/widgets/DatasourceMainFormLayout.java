package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesVersionSharedUtils;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCheckboxItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.core.client.Scheduler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;

public class DatasourceMainFormLayout extends MainFormLayout {

    private DatasetDatasourcesTabUiHandlers uiHandlers;

    private GroupDynamicForm                datasourceForm;
    private GroupDynamicForm                datasourceEditionForm;

    private DatasetVersionDto               datasetVersionDto;

    public DatasourceMainFormLayout() {
        super();
        setStyles();

        setViewMode();

        createViewForm();
        createEditionForm();

        bindEvents();
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;

        setViewMode();

        setDatasetVersionDtoViewMode(datasetVersionDto);
        setDatasetVersionDtoEditionMode(datasetVersionDto);

        setCanDelete(Boolean.FALSE);
        setCanEdit(StatisticalResourcesVersionSharedUtils.isInitialVersion(datasetVersionDto.getVersionLogic()) && DatasetClientSecurityUtils.canUpdateDatasetVersion(datasetVersionDto));
    }

    public DatasetDatasourcesTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }
    public void setUiHandlers(DatasetDatasourcesTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    private void setStyles() {
        setStyleName("normal");
    }
    private void setDatasetVersionDtoViewMode(DatasetVersionDto datasetVersionDto) {
        datasourceForm.setValue(DatasetDS.KEEP_ALL_DATA, Boolean.TRUE.equals(datasetVersionDto.isKeepAllData()) ? MetamacWebCommon.getConstants().yes() : MetamacWebCommon.getConstants().no());
    }

    private void setDatasetVersionDtoEditionMode(DatasetVersionDto datasetVersionDto) {
        datasourceEditionForm.setValue(DatasetDS.KEEP_ALL_DATA, datasetVersionDto.isKeepAllData());
    }

    private void createViewForm() {
        datasourceForm = new GroupDynamicForm(getConstants().datasetDatasources());

        ViewTextItem keepAllDataViewTextItem = new ViewTextItem(DatasetDS.KEEP_ALL_DATA, getConstants().keepAllData());

        datasourceForm.setFields(keepAllDataViewTextItem);
        addViewCanvas(datasourceForm);
    }

    private void createEditionForm() {
        datasourceEditionForm = new GroupDynamicForm(getConstants().datasetDatasources());

        FormItemIcon infoIcon = new FormItemIcon();
        infoIcon.setSrc(GlobalResources.RESOURCE.info().getURL());
        infoIcon.setPrompt(StatisticalResourcesWeb.getMessages().datasetKeepAllDataInfo());

        CustomCheckboxItem keepAllDataCheckBoxItem = new CustomCheckboxItem(DatasetDS.KEEP_ALL_DATA, getConstants().keepAllData());
        keepAllDataCheckBoxItem.setCanEdit(Boolean.TRUE);
        keepAllDataCheckBoxItem.setIcons(infoIcon);

        datasourceEditionForm.setFields(keepAllDataCheckBoxItem);
        addEditionCanvas(datasourceEditionForm);
    }

    private void bindEvents() {
        getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (datasourceEditionForm.isVisible() && datasourceEditionForm.validate(false)) {
                    // See: METAMAC-2516
                    // Two invokes to getXXXDto() is needed for Chrome, please don't remove this two call fix.
                    getDatasetVersionDto();
                    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

                        @Override
                        public void execute() {
                            getUiHandlers().saveDataset(getDatasetVersionDto());
                        }
                    });
                }
            }

        });
    }
    private DatasetVersionDto getDatasetVersionDto() {
        datasetVersionDto.setKeepAllData(Boolean.valueOf(datasourceEditionForm.getValueAsString(DatasetDS.KEEP_ALL_DATA)));
        return datasetVersionDto;
    }
}
