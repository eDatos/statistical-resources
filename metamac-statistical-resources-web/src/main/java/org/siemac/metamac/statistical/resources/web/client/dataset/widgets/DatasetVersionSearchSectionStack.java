package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.SiemacMetadataResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleItemWihtoutFilterWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListResult;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class DatasetVersionSearchSectionStack extends SiemacMetadataResourceSearchSectionStack {

    private SearchSingleItemWihtoutFilterWindow searchTemporalGranularitiesWindow;

    private DatasetListUiHandlers               uiHandlers;

    public DatasetVersionSearchSectionStack() {
    }

    @Override
    protected void createAdvancedSearchForm() {
        super.createAdvancedSearchForm();

        // TODO geographical granularity

        SearchExternalItemLinkItem temporalGranularity = createTemporalGranularityItem(DatasetDS.TEMPORAL_GRANULARITY, getConstants().datasetTemporalGranularity());

        CustomDateItem dateStart = new CustomDateItem(DatasetDS.DATE_START, getConstants().datasetDateStart());
        CustomDateItem dateEnd = new CustomDateItem(DatasetDS.DATE_END, getConstants().datasetDateEnd());
        // TODO related DSD
        CustomDateItem dateNextUpdate = new CustomDateItem(DatasetDS.DATE_NEXT_UPDATE, getConstants().datasetDateNextUpdate());

        advancedSearchForm.addFieldsInThePenultimePosition(temporalGranularity, dateStart, dateEnd, dateNextUpdate);
    }

    public DatasetVersionWebCriteria getDatasetVersionWebCriteria() {
        DatasetVersionWebCriteria criteria = (DatasetVersionWebCriteria) getSiemacMetadataStatisticalResourceWebCriteria(new DatasetVersionWebCriteria());

        ExternalItemDto selectedTemporalGranularity = ((SearchExternalItemLinkItem) advancedSearchForm.getItem(DatasetDS.TEMPORAL_GRANULARITY)).getExternalItemDto();
        criteria.setTemporalGranularityUrn(selectedTemporalGranularity != null ? selectedTemporalGranularity.getUrn() : null);

        criteria.setDateStart(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_START)).getValueAsDate());
        criteria.setDateEnd(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_END)).getValueAsDate());
        criteria.setDateNextUpdate(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_NEXT_UPDATE)).getValueAsDate());
        return criteria;
    }

    @Override
    public void retrieveResources() {
        getUiHandlers().retrieveDatasetsByStatisticalOperation(null, 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getDatasetVersionWebCriteria());
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
                                uiHandlers.retrieveTemporalGranularities(firstResult, maxResults, criteria);
                            }

                        });

                uiHandlers.retrieveTemporalGranularities(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, null);

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
}
