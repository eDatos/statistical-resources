package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NavigationEnabledDynamicForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.SearchSingleStatisticalRelatedResourcePaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.utils.RelatedResourceUtils;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchViewTextItem;

import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class QueryProductionDescriptorsEditionForm extends NavigationEnabledDynamicForm {
    private final int FIRST_RESULT = 0;
    private final int MAX_RESULTS = 8;
    
    private QueryUiHandlers uiHandlers;
    private SearchSingleStatisticalRelatedResourcePaginatedWindow searchDatasetWindow;
    
    private RelatedResourceDto selectedDatasetResource;
    
    private SearchViewTextItem searchDatasetItem;
    
    //FIXME add selection
    public QueryProductionDescriptorsEditionForm() {
        super(getConstants().formProductionDescriptors());

        searchDatasetItem = createQueryDatasetItem(QueryDS.RELATED_DATASET_VERSION, getConstants().queryDatasetVersion());
        searchDatasetItem.setRequired(true);

        setFields(searchDatasetItem);
    }
    
    private void setSelectedDataset(RelatedResourceDto datasetResource) {
        this.selectedDatasetResource = datasetResource;
        setValue(QueryDS.RELATED_DATASET_VERSION, RelatedResourceUtils.getRelatedResourceName(datasetResource));
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
            retrieveDatasetsAsResources(FIRST_RESULT, MAX_RESULTS, searchDatasetWindow.getSearchCriteria());
        }
    }
    
    public void setQueryDto(QueryDto queryDto) {
        setSelectedDataset(queryDto.getRelatedDatasetVersion());
    }
    
    public QueryDto getQueryDto(QueryDto queryDto) {
        queryDto.setRelatedDatasetVersion(selectedDatasetResource);
        
        Map<String, Set<String>> selection = new HashMap<String, Set<String>>();
        selection.put("SEX", new HashSet<String>(Arrays.asList("code1")));
        queryDto.setSelection(selection);
        queryDto.setType(QueryTypeEnum.FIXED);
        return queryDto;
    }
    
    private SearchViewTextItem createQueryDatasetItem(String name, String title) {

        final SearchViewTextItem isReplacedByItem = new SearchViewTextItem(name, title);
        isReplacedByItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchDatasetWindow = new SearchSingleStatisticalRelatedResourcePaginatedWindow(getConstants().resourceSelection(), MAX_RESULTS, new SearchPaginatedAction<VersionableStatisticalResourceWebCriteria>() {
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
                        validate(false);
                    }
                    
                });
            }

        });
        return isReplacedByItem;
    }
    
    private void retrieveDatasetsAsResources(int firstResult, int maxResults, VersionableStatisticalResourceWebCriteria criteria) {
        uiHandlers.retrieveDatasetsForQuery(firstResult, maxResults, criteria);
    }
    

    private void retrieveStatisticalOperationsForDatasetSelection() {
        uiHandlers.retrieveStatisticalOperationsForDatasetSelection();
    }
    
    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
    
    
    public void setUiHandlers(QueryUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
