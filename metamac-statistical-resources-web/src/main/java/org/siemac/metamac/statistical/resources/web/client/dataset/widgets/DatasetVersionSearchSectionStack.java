package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.SiemacMetadataResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleDsdPaginatedWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleItemWihtoutFilterWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetGeographicalGranularitiesListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListResult;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class DatasetVersionSearchSectionStack extends SiemacMetadataResourceSearchSectionStack {

    private SearchSingleItemWihtoutFilterWindow searchGeographicGranularitiesWindow;
    private SearchSingleItemWihtoutFilterWindow searchTemporalGranularitiesWindow;
    private SearchSingleDsdPaginatedWindow      searchDsdWindow;

    private DatasetListUiHandlers               uiHandlers;

    public DatasetVersionSearchSectionStack() {
    }

    @Override
    protected void createAdvancedSearchForm() {
        super.createAdvancedSearchForm();

        SearchExternalItemLinkItem geographicalGranularity = createGeographicGranularityItem(DatasetDS.GEOGRAPHIC_GRANULARITY, getConstants().datasetGeographicGranularity());
        SearchExternalItemLinkItem temporalGranularity = createTemporalGranularityItem(DatasetDS.TEMPORAL_GRANULARITY, getConstants().datasetTemporalGranularity());
        CustomDateItem dateStart = new CustomDateItem(DatasetDS.DATE_START, getConstants().datasetDateStart());
        CustomDateItem dateEnd = new CustomDateItem(DatasetDS.DATE_END, getConstants().datasetDateEnd());
        SearchExternalItemLinkItem dsd = createDsdItem(DatasetDS.RELATED_DSD, getConstants().datasetRelatedDSD());
        CustomDateItem dateNextUpdate = new CustomDateItem(DatasetDS.DATE_NEXT_UPDATE, getConstants().datasetDateNextUpdate());

        SelectItem statisticOfficiality = new SelectItem(DatasetDS.STATISTIC_OFFICIALITY, getConstants().datasetStatisticOfficiality());
        statisticOfficiality.setValueMap(CommonUtils.getStatisticOfficialityHashMap());

        advancedSearchForm.addFieldsInThePenultimePosition(geographicalGranularity, temporalGranularity, dateStart, dateEnd, dsd, dateNextUpdate, statisticOfficiality);
    }

    public DatasetVersionWebCriteria getDatasetVersionWebCriteria() {
        DatasetVersionWebCriteria criteria = (DatasetVersionWebCriteria) getSiemacMetadataStatisticalResourceWebCriteria(new DatasetVersionWebCriteria());

        ExternalItemDto selectedTemporalGranularity = ((SearchExternalItemLinkItem) advancedSearchForm.getItem(DatasetDS.TEMPORAL_GRANULARITY)).getExternalItemDto();
        criteria.setTemporalGranularityUrn(selectedTemporalGranularity != null ? selectedTemporalGranularity.getUrn() : null);

        ExternalItemDto selectedGeographicalGranularity = ((SearchExternalItemLinkItem) advancedSearchForm.getItem(DatasetDS.GEOGRAPHIC_GRANULARITY)).getExternalItemDto();
        criteria.setGeographicalGranularityUrn(selectedGeographicalGranularity != null ? selectedGeographicalGranularity.getUrn() : null);

        criteria.setDateStart(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_START)).getValueAsDate());
        criteria.setDateEnd(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_END)).getValueAsDate());

        ExternalItemDto selectedDsd = ((SearchExternalItemLinkItem) advancedSearchForm.getItem(DatasetDS.RELATED_DSD)).getExternalItemDto();
        criteria.setDsdUrn(selectedDsd != null ? selectedDsd.getUrn() : null);

        criteria.setStatisticOfficialityIdentifier(advancedSearchForm.getValueAsString(DatasetDS.STATISTIC_OFFICIALITY));

        criteria.setDateNextUpdate(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_NEXT_UPDATE)).getValueAsDate());
        return criteria;
    }

    @Override
    public void retrieveResources() {
        getUiHandlers().retrieveDatasets(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getDatasetVersionWebCriteria());
    }

    public void setUiHandlers(DatasetListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public DatasetListUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    //
    // RELATED RESOURCES
    //

    // Temporal granularities

    public void setTemporalGranularities(GetTemporalGranularitiesListResult result) {
        if (searchTemporalGranularitiesWindow != null) {
            searchTemporalGranularitiesWindow.setResources(result.getTemporalGranularities());
            searchTemporalGranularitiesWindow.refreshSourcePaginationInfo(result.getFirstResultOut(), result.getTemporalGranularities().size(), result.getTotalResults());
        }
    }

    private SearchExternalItemLinkItem createTemporalGranularityItem(String name, String title) {

        final SearchExternalItemLinkItem item = new SearchExternalItemLinkItem(name, title);
        item.setExternalItem(null);
        item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchTemporalGranularitiesWindow = new SearchSingleItemWihtoutFilterWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<MetamacWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria criteria) {
                                getUiHandlers().retrieveTemporalGranularitiesForSearchSection(firstResult, maxResults, criteria);
                            }

                        });

                getUiHandlers().retrieveTemporalGranularitiesForSearchSection(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, null);

                searchTemporalGranularitiesWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = searchTemporalGranularitiesWindow.getSelectedResource();
                        searchTemporalGranularitiesWindow.markForDestroy();
                        item.setExternalItem(selectedResource);
                        item.validate();
                    }
                });
            }
        });
        return item;
    }

    // Geographical granularities

    public void setGeographicalGranularities(GetGeographicalGranularitiesListResult result) {
        if (searchGeographicGranularitiesWindow != null) {
            searchGeographicGranularitiesWindow.setResources(result.getGeographicalGranularities());
            searchGeographicGranularitiesWindow.refreshSourcePaginationInfo(result.getFirstResultOut(), result.getGeographicalGranularities().size(), result.getTotalResults());
        }
    }

    private SearchExternalItemLinkItem createGeographicGranularityItem(String name, String title) {

        final SearchExternalItemLinkItem item = new SearchExternalItemLinkItem(name, title);
        item.setExternalItem(null);
        item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchGeographicGranularitiesWindow = new SearchSingleItemWihtoutFilterWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<MetamacWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria criteria) {
                                getUiHandlers().retrieveGeographicGranularitiesForSearchSection(firstResult, maxResults, criteria);
                            }

                        });

                getUiHandlers().retrieveGeographicGranularitiesForSearchSection(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, null);

                searchGeographicGranularitiesWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = searchGeographicGranularitiesWindow.getSelectedResource();
                        searchGeographicGranularitiesWindow.markForDestroy();
                        item.setExternalItem(selectedResource);
                        item.validate();
                    }
                });
            }
        });
        return item;
    }

    // DSD

    public void setStatisticalOperationsForDsdSelection(java.util.List<ExternalItemDto> externalItemDtos) {
        if (searchDsdWindow != null) {
            searchDsdWindow.setStatisticalOperations(externalItemDtos);
        }
    }

    public void setDsds(GetDsdsPaginatedListResult result) {
        if (searchDsdWindow != null) {
            searchDsdWindow.setResources(result.getDsdsList());
            searchDsdWindow.refreshSourcePaginationInfo(result.getFirstResultOut(), result.getDsdsList().size(), result.getTotalResults());
        }
    }

    private SearchExternalItemLinkItem createDsdItem(String name, String title) {

        final SearchExternalItemLinkItem item = new SearchExternalItemLinkItem(name, title);
        item.setExternalItem(null);
        item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchDsdWindow = new SearchSingleDsdPaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<DsdWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, DsdWebCriteria criteria) {
                                getUiHandlers().retrieveDsdsForSearchSection(firstResult, maxResults, criteria);
                            }
                        });

                // Load resources (to populate the selection window)
                getUiHandlers().retrieveStatisticalOperationsForDsdSelectionInSearchSection();
                getUiHandlers().retrieveDsdsForSearchSection(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, new DsdWebCriteria());

                searchDsdWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = searchDsdWindow.getSelectedResource();
                        searchDsdWindow.markForDestroy();
                        item.setExternalItem(selectedResource);
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
