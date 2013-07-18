package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemsValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemsValue;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchMultiExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class StatisticalResourceThematicContentClassifiersEditionForm extends GroupDynamicForm {

    private StatisticalResourceUiHandlers     uiHandlers;

    private SearchMultiExternalItemSimpleItem instancesItem;

    private String                            statisticalOperationCode;

    public StatisticalResourceThematicContentClassifiersEditionForm() {
        super(getConstants().formThematicContentClassifiers());

        ViewTextItem statisticalOperation = new ViewTextItem(StatisticalResourceDS.STATISTICAL_OPERATION, getConstants().siemacMetadataStatisticalResourceStatisticalOperation());
        instancesItem = createStatisticalOperationInstancesItem();

        setFields(statisticalOperation, instancesItem);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        statisticalOperationCode = siemacMetadataStatisticalResourceDto.getStatisticalOperation().getCode();
        setValue(StatisticalResourceDS.STATISTICAL_OPERATION, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getStatisticalOperation()));
        setExternalItemsValue(getItem(StatisticalResourceDS.STATISTICAL_OPERATION_INSTANCE), siemacMetadataStatisticalResourceDto.getStatisticalOperationInstances());
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        dto.getStatisticalOperationInstances().clear();
        dto.getStatisticalOperationInstances().addAll(getExternalItemsValue(getItem(StatisticalResourceDS.STATISTICAL_OPERATION_INSTANCE)));
        return dto;
    }

    // ************************************
    // STATISTICAL OPERATION INSTANCE
    // ************************************

    public void setStatisticalOperationInstances(List<ExternalItemDto> items, int firstResult, int totalResults) {
        instancesItem.setResources(items, firstResult, totalResults);
    }

    private SearchMultiExternalItemSimpleItem createStatisticalOperationInstancesItem() {
        return new SearchMultiExternalItemSimpleItem(StatisticalResourceDS.STATISTICAL_OPERATION_INSTANCE, getConstants().siemacMetadataStatisticalResourceStatisticalOperationInstance(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveStatisticalOperationInstances(statisticalOperationCode, firstResult, maxResults, webCriteria);
            }
        };
    }

    public void setUiHandlers(StatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
