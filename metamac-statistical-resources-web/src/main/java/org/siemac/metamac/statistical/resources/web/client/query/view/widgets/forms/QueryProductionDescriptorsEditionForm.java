package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NavigationEnabledDynamicForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.CodeItemListItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchMultipleCodeItemWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleStatisticalRelatedResourcePaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.utils.RelatedResourceUtils;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchAction;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchViewTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class QueryProductionDescriptorsEditionForm extends NavigationEnabledDynamicForm {

    private QueryUiHandlers                                       uiHandlers;

    private SearchSingleStatisticalRelatedResourcePaginatedWindow searchDatasetWindow;
    private RelatedResourceDto                                    selectedDatasetResource;

    private Map<String, SearchMultipleCodeItemWindow>             dimensionCodeSelectionWindow;
    private Map<String, CodeItemListItem>                         selectionFields;

    private Map<String, List<CodeItemDto>>                        dtoSelection;

    // FIXME add selection
    public QueryProductionDescriptorsEditionForm() {
        super(getConstants().formProductionDescriptors());

        SearchViewTextItem searchDatasetItem = createQueryDatasetItem(QueryDS.RELATED_DATASET_VERSION, getConstants().queryDatasetVersion());

        setFields(searchDatasetItem);
    }

    private void setSelectedDataset(RelatedResourceDto datasetResource) {
        this.selectedDatasetResource = datasetResource;
        setValue(QueryDS.RELATED_DATASET_VERSION, RelatedResourceUtils.getRelatedResourceName(datasetResource));

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

    public void setDatasetDimensionsIds(List<String> datasetDimensions) {
        List<FormItem> fields = new ArrayList<FormItem>();

        SearchViewTextItem searchDatasetItem = createQueryDatasetItem(QueryDS.RELATED_DATASET_VERSION, getConstants().queryDatasetVersion());
        fields.add(searchDatasetItem);

        selectionFields = new HashMap<String, CodeItemListItem>();
        dimensionCodeSelectionWindow = new HashMap<String, SearchMultipleCodeItemWindow>();
        for (String dimensionId : datasetDimensions) {
            CodeItemListItem item = createCodeListItemForDimension(dimensionId, true);
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

    private CodeItemListItem createCodeListItemForDimension(final String dimensionId, boolean editable) {
        CodeItemListItem item = new CodeItemListItem(QueryDS.SELECTION + "_" + dimensionId, dimensionId, editable);
        item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                // FIXME: title
                final SearchMultipleCodeItemWindow window = new SearchMultipleCodeItemWindow("title", new SearchAction<MetamacWebCriteria>() {

                    @Override
                    public void retrieveResultSet(MetamacWebCriteria webCriteria) {
                        uiHandlers.retrieveDimensionCodesForDataset(selectedDatasetResource.getUrn(), dimensionId, webCriteria);
                    }
                });
                dimensionCodeSelectionWindow.put(dimensionId, window);

                uiHandlers.retrieveDimensionCodesForDataset(selectedDatasetResource.getUrn(), dimensionId, new MetamacWebCriteria());

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

    public void setQueryDto(QueryDto queryDto) {
        this.selectedDatasetResource = queryDto.getRelatedDatasetVersion();
        setValue(QueryDS.RELATED_DATASET_VERSION, RelatedResourceUtils.getRelatedResourceName(queryDto.getRelatedDatasetVersion()));

        dtoSelection = queryDto.getSelection();
        if (queryDto.getRelatedDatasetVersion() != null) {
            retrieveDimensionsForDataset(queryDto.getRelatedDatasetVersion().getUrn());
        }
    }

    public QueryDto getQueryDto(QueryDto queryDto) {
        queryDto.setRelatedDatasetVersion(selectedDatasetResource);

        Map<String, List<CodeItemDto>> selection = new HashMap<String, List<CodeItemDto>>();
        for (String dimensionId : selectionFields.keySet()) {
            CodeItemListItem item = selectionFields.get(dimensionId);
            selection.put(dimensionId, item.getCodeItemsDtos());
        }

        queryDto.setSelection(selection);
        queryDto.setType(QueryTypeEnum.FIXED);
        return queryDto;
    }

    private SearchViewTextItem createQueryDatasetItem(String name, String title) {

        final SearchViewTextItem datasetItem = new SearchViewTextItem(name, title);
        datasetItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchDatasetWindow = new SearchSingleStatisticalRelatedResourcePaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
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

    private void retrieveDimensionsForDataset(String urn) {
        uiHandlers.retrieveDimensionsForDataset(urn);
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(QueryUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

}
