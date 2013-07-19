package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dataset.checks.DatasetMetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleDsdPaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.gwtplatform.mvp.client.UiHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class DatasetProductionDescriptorsEditionForm extends StatisticalResourceProductionDescriptorsEditionForm {

    private DatasetMetadataTabUiHandlers   uiHandlers;
    // related Dsd
    private SearchSingleDsdPaginatedWindow searchDsdWindow;
    private String                         fixedDsdCode;
    private ExternalItemDto                statisticalOperation;

    // components
    private ViewTextItem                   relatedDsdView;
    private SearchExternalItemLinkItem     relatedDsd;

    public DatasetProductionDescriptorsEditionForm() {

        relatedDsdView = new ViewTextItem(DatasetDS.RELATED_DSD_VIEW, getConstants().datasetRelatedDSD());
        relatedDsd = createDsdsItem(DatasetDS.RELATED_DSD, getConstants().datasetRelatedDSD());
        relatedDsd.setRequired(true);

        addFields(relatedDsdView, relatedDsd);
    }

    public void setUiHandlers(DatasetMetadataTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        this.uiHandlers = uiHandlers;
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        setRelatedDsd(datasetDto.getRelatedDsd());
        relatedDsd.setShowIfCondition(getRelatedDsdFormItemIfFunction(datasetDto));
        relatedDsdView.setShowIfCondition(getStaticRelatedDsdFormItemIfFunction(datasetDto));

        if (!DatasetMetadataEditionChecks.canDsdBeReplacedByAnyOtherDsd(datasetDto.getId(), datasetDto.getVersionLogic(), datasetDto.getProcStatus())) {
            fixedDsdCode = datasetDto.getRelatedDsd().getCode();
            statisticalOperation = datasetDto.getStatisticalOperation();
        } else {
            fixedDsdCode = null;
        }
    }

    public DatasetVersionDto getDatasetVersionDto(DatasetVersionDto datasetDto) {
        datasetDto = (DatasetVersionDto) getSiemacMetadataStatisticalResourceDto(datasetDto);
        datasetDto.setRelatedDsd(getExternalItemValue(getItem(DatasetDS.RELATED_DSD)));
        return datasetDto;
    }

    private void setRelatedDsd(ExternalItemDto relatedDsdDto) {
        setExternalItemValue(getItem(DatasetDS.RELATED_DSD), relatedDsdDto);
        setValue(DatasetDS.RELATED_DSD_VIEW, ExternalItemUtils.getExternalItemName(relatedDsdDto));
    }

    public void setExternalItemsForRelatedDsd(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        if (searchDsdWindow != null) {
            searchDsdWindow.setResources(externalItemsDtos);
            searchDsdWindow.refreshSourcePaginationInfo(firstResult, externalItemsDtos.size(), totalResults);
        }
    }

    public void setStatisticalOperationsForRelatedDsd(List<ExternalItemDto> externalItemsDtos, ExternalItemDto defaultSelected, String dsdCode) {
        if (searchDsdWindow != null) {
            searchDsdWindow.setStatisticalOperations(externalItemsDtos);
            searchDsdWindow.setSelectedStatisticalOperation(defaultSelected);
            searchDsdWindow.setFixedDsdCode(dsdCode);
            if (StringUtils.isEmpty(dsdCode)) {
                searchDsdWindow.setOnlyLastVersion(true);
            } else {
                searchDsdWindow.setOnlyLastVersion(false);
            }

            retrieveResourcesForRelatedDsd(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, searchDsdWindow.getDsdWebCriteria());
        }
    }

    public void retrieveResourcesForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria) {
        uiHandlers.retrieveDsdsForRelatedDsd(firstResult, maxResults, criteria);
    }

    public void retrieveStatisticalOperationsForDsdSelection() {
        uiHandlers.retrieveStatisticalOperationsForDsdSelection();
    }

    // FORM ITEM IF FUNCTIONS

    private FormItemIfFunction getRelatedDsdFormItemIfFunction(final DatasetVersionDto resource) {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return DatasetMetadataEditionChecks.canDsdBeEdited(resource.getId(), resource.getProcStatus());
            }
        };
    }
    private FormItemIfFunction getStaticRelatedDsdFormItemIfFunction(final DatasetVersionDto resource) {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return !DatasetMetadataEditionChecks.canDsdBeEdited(resource.getId(), resource.getProcStatus());
            }
        };
    }

    // WINDOWS

    private SearchExternalItemLinkItem createDsdsItem(String name, String title) {

        final SearchExternalItemLinkItem dsdItem = new SearchExternalItemLinkItem(name, title);
        dsdItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchDsdWindow = new SearchSingleDsdPaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<DsdWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, DsdWebCriteria criteria) {
                                retrieveResourcesForRelatedDsd(firstResult, maxResults, criteria);
                            }
                        });

                // Load resources (to populate the selection window)
                if (!StringUtils.isEmpty(fixedDsdCode)) {
                    setStatisticalOperationsForRelatedDsd(Arrays.asList(statisticalOperation), statisticalOperation, fixedDsdCode);
                } else {
                    retrieveStatisticalOperationsForDsdSelection();
                }
                searchDsdWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = searchDsdWindow.getSelectedResource();
                        searchDsdWindow.markForDestroy();
                        // Set selected resource in form
                        setRelatedDsd(selectedResource);
                        validate(false);
                    }
                });
            }
        });
        return dsdItem;
    }
}
