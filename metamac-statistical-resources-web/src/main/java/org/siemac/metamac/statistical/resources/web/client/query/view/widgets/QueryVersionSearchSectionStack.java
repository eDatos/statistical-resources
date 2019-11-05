package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableRelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.query.utils.QueryProductionDescriptionFormUtils;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.LifeCycleResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchRelatedResourceLinkItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleDatasetVersionVersionableRelatedResourcePaginatedWindow2;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.QueryVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.utils.RelatedResourceUtils;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.handlers.CustomLinkItemNavigationClickHandler;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class QueryVersionSearchSectionStack extends LifeCycleResourceSearchSectionStack {

    private SearchSingleDatasetVersionVersionableRelatedResourcePaginatedWindow2 searchDatasetVesionWindow;

    private QueryListUiHandlers                                                 uiHandlers;

    public QueryVersionSearchSectionStack() {
    }

    public QueryVersionWebCriteria getQueryVersionWebCriteria() {
        QueryVersionWebCriteria criteria = (QueryVersionWebCriteria) getLifeCycleResourceWebCriteria(new QueryVersionWebCriteria());

        RelatedResourceDto selectedDatasetVersion = ((SearchRelatedResourceLinkItem) advancedSearchForm.getItem(QueryDS.RELATED_DATASET_VERSION)).getRelatedResourceDto();
        criteria.setDatasetVersionUrn(selectedDatasetVersion != null ? selectedDatasetVersion.getUrn() : null);

        criteria.setQueryStatus(CommonUtils.getQueryStatusEnum(advancedSearchForm.getValueAsString(QueryDS.STATUS)));
        criteria.setQueryType(CommonUtils.getQueryTypeEnum(advancedSearchForm.getValueAsString(QueryDS.TYPE)));
        return criteria;
    }

    @Override
    protected void createAdvancedSearchForm() {
        super.createAdvancedSearchForm();

        SearchRelatedResourceLinkItem datasetVersion = createSearchDatasetVersionItem(QueryDS.RELATED_DATASET_VERSION, getConstants().queryDataset());

        SelectItem status = new SelectItem(QueryDS.STATUS, getConstants().queryStatus());
        status.setValueMap(CommonUtils.getQueryStatusHashMap());

        SelectItem type = new SelectItem(QueryDS.TYPE, getConstants().queryType());
        type.setValueMap(CommonUtils.getQueryTypeHashMap());

        advancedSearchForm.addFieldsInThePenultimePosition(datasetVersion, status, type);
    }

    @Override
    public void retrieveResources() {
        getUiHandlers().retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getQueryVersionWebCriteria());
    }

    public void setUiHandlers(QueryListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public QueryListUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    //
    // RELATED RESOURCES
    //

    // Dataset version

    public void setStatisticalOperationsForDatasetVersionSelection(List<ExternalItemDto> results) {
        if (searchDatasetVesionWindow != null) {
            searchDatasetVesionWindow.setStatisticalOperations(results);
        }
    }

    public void setDatasetVersions(GetDatasetVersionsResult result) {
        if (searchDatasetVesionWindow != null) {
            List<VersionableRelatedResourceDto> versionableRelatedResourceDtos = RelatedResourceUtils.getDatasetVersionBaseDtosAsVersionableRelatedResourceDtos(result.getDatasetVersionBaseDtos());
            searchDatasetVesionWindow.setResources(versionableRelatedResourceDtos);
            searchDatasetVesionWindow.refreshSourcePaginationInfo(result.getFirstResultOut(), versionableRelatedResourceDtos.size(), result.getTotalResults());
        }
    }

    private SearchRelatedResourceLinkItem createSearchDatasetVersionItem(String name, String title) {

        final SearchRelatedResourceLinkItem item = new SearchRelatedResourceLinkItem(name, title, new CustomLinkItemNavigationClickHandler() {

            @Override
            public BaseUiHandlers getBaseUiHandlers() {
                return getUiHandlers();
            }
        });
        item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchDatasetVesionWindow = new SearchSingleDatasetVersionVersionableRelatedResourcePaginatedWindow2(getConstants().resourceSelection(),
                        StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, new SearchPaginatedAction<DatasetVersionWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, DatasetVersionWebCriteria criteria) {
                                getUiHandlers().retrieveDatasetVersionsForSearchSection(firstResult, maxResults, criteria);
                            }
                        });

                getUiHandlers().retrieveStatisticalOperationsForDatasetVersionSelectionInSearchSection();
                getUiHandlers().retrieveDatasetVersionsForSearchSection(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, new DatasetVersionWebCriteria());

                searchDatasetVesionWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        VersionableRelatedResourceDto selectedResource = searchDatasetVesionWindow.getSelectedResource();
                        searchDatasetVesionWindow.markForDestroy();
                        // Set selected resource in form
                        QueryProductionDescriptionFormUtils.setRelatedDataset(selectedResource, item);
                        item.validate();
                    }
                });
            }
        });
        return item;
    }

    // Statistical operations

    @Override
    public void retrieveStatisticalOperations(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        getUiHandlers().retrieveStatisticalOperationsForSearchSection(firstResult, maxResults, criteria);
    }
}
