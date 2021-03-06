package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.DataSourceTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.NewStatisticalResourceWindow;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleDsdPaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCheckboxItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchExternalItemLinkItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewDatasetWindow extends NewStatisticalResourceWindow {

    private static final String            FIELD_SAVE = "save-sch";

    private DatasetListUiHandlers          uiHandlers;

    private SearchExternalItemLinkItem     relatedDsdItem;

    private SearchSingleDsdPaginatedWindow searchDsdWindow;

    public NewDatasetWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem nameItem = new RequiredTextItem(DatasetDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        nameItem.setWidth("*");

        relatedDsdItem = createDsdItem();
        relatedDsdItem.setRequired(true);
        
        CustomCheckboxItem keepAllDataCheckBoxItem = createKeepAllDataCheckBoxItem();

        RequiredSelectItem dataSourceTypeItem = new RequiredSelectItem(DatasetDS.DATA_SOURCE_TYPE, getConstants().datasetVersionDataSourceType());
        dataSourceTypeItem.setValueMap(CommonUtils.getDataSourceTypeHashMap());
        dataSourceTypeItem.setAlign(Alignment.LEFT);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, getConstants().datasetCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(nameItem, relatedDsdItem, languageItem, maintainerItem, dataSourceTypeItem, keepAllDataCheckBoxItem, saveItem);
		form.setColWidths("30%", "70%");
        form.setWidth100();

        addItem(form);
        show();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public DatasetVersionDto getNewDatasetVersionDto() {
        DatasetVersionDto datasetDto = new DatasetVersionDto();
        datasetDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(DatasetDS.TITLE)));
        datasetDto.setRelatedDsd(form.getValueAsExternalItemDto(DatasetDS.RELATED_DSD));
        datasetDto.setDataSourceType(DataSourceTypeEnum.valueOf(form.getValueAsString(DatasetDS.DATA_SOURCE_TYPE)));
        datasetDto.setKeepAllData(Boolean.valueOf(form.getValueAsString(DatasetDS.KEEP_ALL_DATA)));
        populateSiemacResourceDto(datasetDto);

        return datasetDto;
    }

    public void setUiHandlers(DatasetListUiHandlers uiHandlers) {
        super.setSiemacUiHandlers(uiHandlers);
        this.uiHandlers = uiHandlers;
    }

    // ***********************************************************
    // RELATED DSD
    // ***********************************************************
    private void setRelatedDsd(ExternalItemDto relatedDsdDto) {
        form.setValue(DatasetDS.RELATED_DSD, relatedDsdDto);
    }

    public void setExternalItemsForRelatedDsd(List<ExternalItemDto> externalItemsDtos, int firstResult, int elementsInPage, int totalResults) {
        if (searchDsdWindow != null) {
            searchDsdWindow.setResources(externalItemsDtos);
            searchDsdWindow.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    public void setStatisticalOperationsForRelatedDsd(List<ExternalItemDto> externalItemsDtos, ExternalItemDto defaultSelected) {
        if (searchDsdWindow != null) {
            searchDsdWindow.setStatisticalOperations(externalItemsDtos);
            searchDsdWindow.setSelectedStatisticalOperation(defaultSelected);
            searchDsdWindow.setFixedDsdCode(null);
            searchDsdWindow.setOnlyLastVersion(true);

            retrieveResourcesForRelatedDsd(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, searchDsdWindow.getDsdWebCriteria());
        }
    }

    private CustomCheckboxItem createKeepAllDataCheckBoxItem() {
        FormItemIcon infoIcon = new FormItemIcon();
        infoIcon.setSrc(GlobalResources.RESOURCE.info().getURL());
        infoIcon.setPrompt(StatisticalResourcesWeb.getMessages().datasetKeepAllDataInfo());

        CustomCheckboxItem keepAllDataCheckBoxItem = new CustomCheckboxItem(DatasetDS.KEEP_ALL_DATA, getConstants().keepAllData());
        keepAllDataCheckBoxItem.setCanEdit(Boolean.TRUE);
        keepAllDataCheckBoxItem.setValue(Boolean.TRUE);
        keepAllDataCheckBoxItem.setIcons(infoIcon);

        return keepAllDataCheckBoxItem;
    }

    private SearchExternalItemLinkItem createDsdItem() {

        final SearchExternalItemLinkItem item = new SearchExternalItemLinkItem(DatasetDS.RELATED_DSD, getConstants().datasetRelatedDSD()) {

            @Override
            public void onSearch() {

                searchDsdWindow = new SearchSingleDsdPaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<DsdWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, DsdWebCriteria criteria) {
                                retrieveResourcesForRelatedDsd(firstResult, maxResults, criteria);
                            }
                        });

                // Load resources (to populate the selection window)
                retrieveStatisticalOperationsForDsdSelection();

                searchDsdWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = searchDsdWindow.getSelectedResource();
                        searchDsdWindow.markForDestroy();
                        // Set selected resource in form
                        setRelatedDsd(selectedResource);
                        form.validate(false);
                    }
                });
            }
        };
        return item;
    }
    public void retrieveStatisticalOperationsForDsdSelection() {
        uiHandlers.retrieveStatisticalOperationsForDsdSelection();
    }

    public void retrieveResourcesForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria) {
        uiHandlers.retrieveDsdsForRelatedDsd(firstResult, maxResults, criteria);
    }

}
