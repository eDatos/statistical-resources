package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getRelatedResourceValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourceValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.base.checks.MetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NavigationEnabledDynamicForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.CodeItemListItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchRelatedResourceLinkItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchSrmLinkItemWithSchemeFilterItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchMultipleCodeItemWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleVersionableStatisticalRelatedResourcePaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchAction;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class QueryProductionDescriptorsEditionForm extends NavigationEnabledDynamicForm {

    private QueryUiHandlers                                                  uiHandlers;

    private SearchSingleVersionableStatisticalRelatedResourcePaginatedWindow searchDatasetWindow;

    private Map<String, SearchMultipleCodeItemWindow>                        dimensionCodeSelectionWindow;
    private Map<String, CodeItemListItem>                                    selectionFields;

    protected SearchSrmLinkItemWithSchemeFilterItem                          maintainerItem;

    private Map<String, List<CodeItemDto>>                                   dtoSelection;
    
    public QueryProductionDescriptorsEditionForm() {
        super(getConstants().formProductionDescriptors());

        maintainerItem = createMaintainerItem();
        maintainerItem.setShowIfCondition(getFormItemIfFunctionCreateMode());
        
        ExternalItemLinkItem maintainerViewItem = new ExternalItemLinkItem(StatisticalResourceDS.MAINTAINER_VIEW, getConstants().siemacMetadataStatisticalResourceMaintainer());
        maintainerViewItem.setShowIfCondition(getFormItemIfFunctionEditionMode());

        SearchRelatedResourceLinkItem searchDatasetItem = createQueryDatasetItem();

        setFields(maintainerViewItem, maintainerItem, searchDatasetItem);
    }
    

    public void setQueryDto(QueryVersionDto queryDto) {
        setRelatedResourceValue(getItem(QueryDS.RELATED_DATASET_VERSION), queryDto.getRelatedDatasetVersion());
        setExternalItemValue(getItem(LifeCycleResourceDS.MAINTAINER), queryDto.getMaintainer());

        dtoSelection = queryDto.getSelection();
        if (queryDto.getRelatedDatasetVersion() != null) {
            retrieveDimensionsForDataset(queryDto.getRelatedDatasetVersion().getUrn());
        }
    }

    public QueryVersionDto getQueryDto(QueryVersionDto queryDto) {

        queryDto.setMaintainer(getExternalItemValue(getItem(StatisticalResourceDS.MAINTAINER)));
        queryDto.setRelatedDatasetVersion(getRelatedResourceValue(getItem(QueryDS.RELATED_DATASET_VERSION)));

        Map<String, List<CodeItemDto>> selection = new HashMap<String, List<CodeItemDto>>();
        for (String dimensionId : selectionFields.keySet()) {
            CodeItemListItem item = selectionFields.get(dimensionId);
            selection.put(dimensionId, item.getCodeItemsDtos());
        }

        queryDto.setSelection(selection);
        queryDto.setType(QueryTypeEnum.FIXED);
        return queryDto;
    }
    
    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(QueryUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    // ********************************************************
    // RELATED DATASET
    // *******************************************************

    private void setSelectedDataset(RelatedResourceDto datasetResource) {
        setRelatedResourceValue(getItem(QueryDS.RELATED_DATASET_VERSION), datasetResource);

        // Get dimensions
        if (datasetResource != null) {
            retrieveDimensionsForDataset(datasetResource.getUrn());
        }
    }

    public void setDatasetsForQuery(List<RelatedResourceDto> resourcesDtos, int firstResult, int elementsInPage, int totalResults) {
        if (searchDatasetWindow != null) {
            searchDatasetWindow.setResources(resourcesDtos);
            searchDatasetWindow.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    public void setStatisticalOperationsForDatasetSelection(List<ExternalItemDto> externalItemsDtos) {
        if (searchDatasetWindow != null) {
            searchDatasetWindow.setStatisticalOperations(externalItemsDtos);
            retrieveDatasetsAsResources(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, searchDatasetWindow.getSearchCriteria());
        }
    }

    private SearchRelatedResourceLinkItem createQueryDatasetItem() {

        final SearchRelatedResourceLinkItem datasetItem = new SearchRelatedResourceLinkItem(QueryDS.RELATED_DATASET_VERSION, getConstants().queryDatasetVersion(),
                getCustomLinkItemNavigationClickHandler());
        datasetItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchDatasetWindow = new SearchSingleVersionableStatisticalRelatedResourcePaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<VersionableStatisticalResourceWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, VersionableStatisticalResourceWebCriteria criteria) {
                                retrieveDatasetsAsResources(firstResult, maxResults, criteria);
                            }
                        });

                retrieveStatisticalOperationsForDatasetSelection();

                searchDatasetWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        RelatedResourceDto selectedResource = searchDatasetWindow.getSelectedResource();
                        searchDatasetWindow.markForDestroy();
                        // Set selected resource in form
                        setSelectedDataset(selectedResource);
                        dtoSelection = null;
                        validate(false);
                    }

                });
            }

        });
        datasetItem.setRequired(true);
        return datasetItem;
    }

    private void retrieveDatasetsAsResources(int firstResult, int maxResults, VersionableStatisticalResourceWebCriteria criteria) {
        uiHandlers.retrieveDatasetsForQuery(firstResult, maxResults, criteria);
    }

    private void retrieveStatisticalOperationsForDatasetSelection() {
        uiHandlers.retrieveStatisticalOperationsForDatasetSelection();
    }

    // ********************************************************
    // DATASET DIMENSIONS
    // *******************************************************

    public void setDatasetDimensionsIds(List<String> datasetDimensions) {
        List<FormItem> fields = new ArrayList<FormItem>();
        
        ExternalItemDto maintainer = getExternalItemValue(getItem(QueryDS.MAINTAINER));
        maintainerItem = createMaintainerItem();
        maintainerItem.setShowIfCondition(getFormItemIfFunctionCreateMode());
        fields.add(maintainerItem);
        
        RelatedResourceDto datasetVersion = getRelatedResourceValue(getItem(QueryDS.RELATED_DATASET_VERSION));

        SearchRelatedResourceLinkItem searchDatasetItem = createQueryDatasetItem();
        fields.add(searchDatasetItem);

        selectionFields = new HashMap<String, CodeItemListItem>();
        dimensionCodeSelectionWindow = new HashMap<String, SearchMultipleCodeItemWindow>();
        for (String dimensionId : datasetDimensions) {
            CodeItemListItem item = createCodeListItemForDimension(datasetVersion.getUrn(), dimensionId, true);
            fields.add(item);
            selectionFields.put(dimensionId, item);
        }
        setFields(fields.toArray(new FormItem[fields.size()]));

        if (dtoSelection != null && dtoSelection.size() > 0) {
            for (String dimensionId : datasetDimensions) {
                CodeItemListItem item = (CodeItemListItem) getItem(QueryDS.SELECTION + "_" + dimensionId);
                item.setCodeItems(dtoSelection.get(dimensionId));
            }
        }
        setExternalItemValue(getItem(QueryDS.MAINTAINER), maintainer);
        setRelatedResourceValue(getItem(QueryDS.RELATED_DATASET_VERSION), datasetVersion);
        this.markForRedraw();
    }

    public void setDatasetDimensionCodes(String dimensionId, List<CodeItemDto> codesDimension) {
        SearchMultipleCodeItemWindow window = dimensionCodeSelectionWindow.get(dimensionId);
        if (window != null) {
            window.setResources(codesDimension);
        }
    }

    private void setSelectedCodesForDimension(String dimensionId, List<CodeItemDto> selectedResources) {
        CodeItemListItem item = (CodeItemListItem) getItem(QueryDS.SELECTION + "_" + dimensionId);
        item.setCodeItems(selectedResources);
    }

    private CodeItemListItem createCodeListItemForDimension(final String datasetUrn, final String dimensionId, boolean editable) {
        CodeItemListItem item = new CodeItemListItem(QueryDS.SELECTION + "_" + dimensionId, dimensionId, editable);
        item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                final SearchMultipleCodeItemWindow window = new SearchMultipleCodeItemWindow(StatisticalResourcesWeb.getConstants().codeSelection(), new SearchAction<MetamacWebCriteria>() {

                    @Override
                    public void retrieveResultSet(MetamacWebCriteria webCriteria) {
                        uiHandlers.retrieveDimensionCodesForDataset(datasetUrn, dimensionId, webCriteria);
                    }
                });
                dimensionCodeSelectionWindow.put(dimensionId, window);

                uiHandlers.retrieveDimensionCodesForDataset(datasetUrn, dimensionId, new MetamacWebCriteria());

                window.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        List<CodeItemDto> selectedResources = window.getSelectedResources();
                        window.markForDestroy();
                        // Set selected resource in form
                        setSelectedCodesForDimension(dimensionId, selectedResources);
                        validate(false);
                    }

                });
            }
        });
        return item;
    }

    private void retrieveDimensionsForDataset(String urn) {
        uiHandlers.retrieveDimensionsForDataset(urn);
    }


    // *******************************************************************
    // MAINTAINER
    // *******************************************************************

    public void setDefaultMaintainer(ExternalItemDto defaultMaintainer) {
        if (defaultMaintainer != null) {
            setExternalItemValue(getItem(LifeCycleResourceDS.MAINTAINER), defaultMaintainer);
        }
    }

    public void setAgencySchemesForMaintainer(List<ExternalItemDto> externalItemsDtos, int firstResult, int elementsInPage, int totalResults) {
        if (maintainerItem != null) {
            maintainerItem.setFilterResources(externalItemsDtos, firstResult, elementsInPage, totalResults);
        }
    }

    public void setExternalItemsForMaintainer(List<ExternalItemDto> externalItemsDtos, int firstResult, int elementsInPage, int totalResults) {
        if (maintainerItem != null) {
            maintainerItem.setResources(externalItemsDtos, firstResult, elementsInPage, totalResults);
        }
    }

    private SearchSrmLinkItemWithSchemeFilterItem createMaintainerItem() {
        return new SearchSrmLinkItemWithSchemeFilterItem(LifeCycleResourceDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                uiHandlers.retrieveAgencies(firstResult, maxResults, webCriteria);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveAgencySchemes(firstResult, maxResults, webCriteria);
            }
        };
    }
    
    private FormItemIfFunction getFormItemIfFunctionEditionMode() {
        return new FormItemIfFunction() {
             
             @Override
             public boolean execute(FormItem item, Object value, DynamicForm form) {
                 ExternalItemDto maintainer = StatisticalResourcesFormUtils.getExternalItemValue(form.getItem(StatisticalResourceDS.MAINTAINER_VIEW));
                 return MetadataEditionChecks.canMaintainerBeEdited(maintainer != null ? maintainer.getId() : null);
             }
         };
     }

     private FormItemIfFunction getFormItemIfFunctionCreateMode() {
         return new FormItemIfFunction() {
             
             @Override
             public boolean execute(FormItem item, Object value, DynamicForm form) {
                 ExternalItemDto maintainer = StatisticalResourcesFormUtils.getExternalItemValue(form.getItem(StatisticalResourceDS.MAINTAINER_VIEW));
                 return !MetadataEditionChecks.canMaintainerBeEdited(maintainer != null ? maintainer.getId() : null);
             }
         };
     }
}
