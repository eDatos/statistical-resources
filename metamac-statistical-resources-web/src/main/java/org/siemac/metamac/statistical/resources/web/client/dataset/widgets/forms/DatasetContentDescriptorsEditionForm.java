package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemsValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemsValue;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;
import org.siemac.metamac.statistical.resources.web.client.base.checks.DatasetMetadataShowChecks;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.SiemacMetadataContentDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchMultiExternalItemSimpleItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.TemporalCodeListItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchMultipleSrmItemWithSchemeFilterPaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class DatasetContentDescriptorsEditionForm extends SiemacMetadataContentDescriptorsEditionForm {

    private DatasetMetadataTabUiHandlers                         uiHandlers;
    private SearchMultipleSrmItemWithSchemeFilterPaginatedWindow statisticalUnitWindow;

    private SearchMultiExternalItemSimpleItem                    geographicalGranularitiesItem;
    private SearchMultiExternalItemSimpleItem                    temporalGranularitiesItem;

    public DatasetContentDescriptorsEditionForm() {
        super();

        ExternalItemListItem geographicCoverage = new ExternalItemListItem(DatasetDS.GEOGRAPHIC_COVERAGE, getConstants().datasetGeographicCoverage(), false);
        geographicCoverage.setShowIfCondition(getCanShowCoveragesFunction());

        TemporalCodeListItem temporalCoverage = new TemporalCodeListItem(DatasetDS.TEMPORAL_COVERAGE, getConstants().datasetTemporalCoverage(), false);
        temporalCoverage.setShowIfCondition(getCanShowCoveragesFunction());

        ExternalItemListItem measures = new ExternalItemListItem(DatasetDS.MEASURES, getConstants().datasetMeasures(), false);
        measures.setShowIfCondition(getCanShowCoveragesFunction());

        geographicalGranularitiesItem = createGeographicGranularitiesItem();
        geographicalGranularitiesItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                List<ExternalItemDto> values = getExternalItemsValue(getItem(DatasetDS.GEOGRAPHIC_GRANULARITY));
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? (values != null && values.size() > 0) : true;
            }
        });

        temporalGranularitiesItem = createTemporalGranularitiesItem();
        temporalGranularitiesItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                List<ExternalItemDto> values = getExternalItemsValue(getItem(DatasetDS.TEMPORAL_GRANULARITY));
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? (values != null && values.size() > 0) : true;
            }
        });

        ViewTextItem dateStart = new ViewTextItem(DatasetDS.DATE_START, getConstants().datasetDateStart());

        ViewTextItem dateEnd = new ViewTextItem(DatasetDS.DATE_END, getConstants().datasetDateEnd());

        ExternalItemListItem statisticalUnit = createStatisticalUnitItem();

        addFields(dateStart, dateEnd, statisticalUnit, geographicCoverage, temporalCoverage, measures, geographicalGranularitiesItem, temporalGranularitiesItem);
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);

        setExternalItemsValue(getItem(DatasetDS.GEOGRAPHIC_GRANULARITY), datasetDto.getGeographicGranularities());

        setExternalItemsValue(getItem(DatasetDS.TEMPORAL_GRANULARITY), datasetDto.getTemporalGranularities());

        setValue(DatasetDS.DATE_START, datasetDto.getDateStart());
        setValue(DatasetDS.DATE_END, datasetDto.getDateEnd());

        setSelectedConceptsForStatisticalUnit(datasetDto.getStatisticalUnit());
    }

    public void setCoverages(List<ExternalItemDto> geoItems, List<TemporalCodeDto> temporalItems, List<ExternalItemDto> measureItems) {
        ((ExternalItemListItem) getItem(DatasetDS.GEOGRAPHIC_COVERAGE)).setExternalItems(geoItems);
        ((TemporalCodeListItem) getItem(DatasetDS.TEMPORAL_COVERAGE)).setTemporalCodes(temporalItems);
        ((ExternalItemListItem) getItem(DatasetDS.MEASURES)).setExternalItems(measureItems);
    }

    public DatasetVersionDto getDatasetVersionDto(DatasetVersionDto datasetDto) {
        datasetDto = (DatasetVersionDto) getSiemacMetadataStatisticalResourceDto(datasetDto);

        datasetDto.getGeographicGranularities().clear();
        datasetDto.getGeographicGranularities().addAll(getExternalItemsValue(getItem(DatasetDS.GEOGRAPHIC_GRANULARITY)));

        datasetDto.getTemporalGranularities().clear();
        datasetDto.getTemporalGranularities().addAll(getExternalItemsValue(getItem(DatasetDS.TEMPORAL_GRANULARITY)));

        datasetDto.getStatisticalUnit().clear();
        datasetDto.getStatisticalUnit().addAll(getExternalItemsValue(getItem(DatasetDS.STATISTICAL_UNIT)));

        return datasetDto;
    }

    // ***************************************************************************************
    // GEO GRANULARITIES
    // ***************************************************************************************

    public void setCodesForGeographicalGranularities(List<ExternalItemDto> items, int firstResult, int totalResults) {
        geographicalGranularitiesItem.setResources(items, firstResult, totalResults);
    }

    private SearchMultiExternalItemSimpleItem createGeographicGranularitiesItem() {
        return new SearchMultiExternalItemSimpleItem(DatasetDS.GEOGRAPHIC_GRANULARITY, getConstants().datasetGeographicGranularities(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveCodesForGeographicalGranularities(firstResult, maxResults, webCriteria);
            }
        };
    }

    // ***************************************************************************************
    // Time GRANULARITIES
    // ***************************************************************************************

    public void setCodesForTemporalGranularities(List<ExternalItemDto> items, int firstResult, int totalResults) {
        temporalGranularitiesItem.setResources(items, firstResult, totalResults);
    }

    private SearchMultiExternalItemSimpleItem createTemporalGranularitiesItem() {
        return new SearchMultiExternalItemSimpleItem(DatasetDS.TEMPORAL_GRANULARITY, getConstants().datasetTemporalGranularities(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveTemporalCodesForField(firstResult, maxResults, webCriteria, DatasetMetadataExternalField.TEMPORAL_GRANULARITY);
            }
        };
    }

    // ***************************************************************************************
    // STATISTICAL UNIT
    // ***************************************************************************************

    public void setConceptsForStatisticalUnit(List<ExternalItemDto> items, int firstResult, int totalResults) {
        if (statisticalUnitWindow != null) {
            statisticalUnitWindow.setResources(items);
            statisticalUnitWindow.refreshSourcePaginationInfo(firstResult, items.size(), totalResults);
        }
    }

    public void setConceptSchemesForStatisticalUnit(List<ExternalItemDto> items, int firstResult, int totalResults) {
        if (statisticalUnitWindow != null) {
            statisticalUnitWindow.setFilterResources(items);
            statisticalUnitWindow.refreshFilterSourcePaginationInfo(firstResult, items.size(), totalResults);
        }
    }

    public void setSelectedConceptsForStatisticalUnit(List<ExternalItemDto> items) {
        if (statisticalUnitWindow != null) {
            statisticalUnitWindow.setSelectedResources(items);
            setExternalItemsValue(getItem(DatasetDS.STATISTICAL_UNIT), items);
        }
    }

    private ExternalItemListItem createStatisticalUnitItem() {
        final ExternalItemListItem listItem = new ExternalItemListItem(DatasetDS.STATISTICAL_UNIT, getConstants().datasetStatisticalUnit(), true);
        listItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                SearchPaginatedAction<MetamacWebCriteria> filterAction = new SearchPaginatedAction<MetamacWebCriteria>() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                        retrieveConceptSchemesForStatisticalUnit(firstResult, maxResults, webCriteria);
                    }
                };

                statisticalUnitWindow = new SearchMultipleSrmItemWithSchemeFilterPaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        filterAction, new SearchPaginatedAction<ItemSchemeWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                                retrieveConceptsForStatisticalUnit(firstResult, maxResults, webCriteria);
                            }

                        });

                retrieveConceptsForStatisticalUnit(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, new ItemSchemeWebCriteria());

                statisticalUnitWindow.setSelectedResources(listItem.getSelectedRelatedResources());

                statisticalUnitWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        setSelectedConceptsForStatisticalUnit(statisticalUnitWindow.getSelectedResources());
                        statisticalUnitWindow.markForDestroy();
                    }
                });
            }
        });
        return listItem;
    }

    private void retrieveConceptSchemesForStatisticalUnit(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
        uiHandlers.retrieveConceptSchemesForStatisticalUnit(firstResult, maxResults, webCriteria);
    };

    private void retrieveConceptsForStatisticalUnit(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
        uiHandlers.retrieveConceptsForStatisticalUnit(firstResult, maxResults, webCriteria);
    };

    public void setUiHandlers(DatasetMetadataTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    private FormItemIfFunction getCanShowCoveragesFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return DatasetMetadataShowChecks.canCoveragesBeShown(procStatus);
            }
        };
    }
}
