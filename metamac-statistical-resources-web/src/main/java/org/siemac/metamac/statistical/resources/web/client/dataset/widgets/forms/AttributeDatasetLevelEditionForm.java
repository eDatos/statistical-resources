package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class AttributeDatasetLevelEditionForm extends AttributeBaseForm {

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        SearchExternalItemSimpleItem value = createEnumeratedValueItem();
        if (dsdAttributeInstanceDtos != null && !dsdAttributeInstanceDtos.isEmpty() && dsdAttributeInstanceDtos.get(0).getValue() != null) {
            value.setExternalItem(dsdAttributeInstanceDtos.get(0).getValue().getExternalItemValue());
        }
        setFields(value);
    }

    @Override
    protected void buildNonEnumeratedRepresentationForm(List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        CustomTextItem value = new CustomTextItem(DsdAttributeDS.VALUE, getConstants().datasetAttributeValue());
        if (dsdAttributeInstanceDtos != null && !dsdAttributeInstanceDtos.isEmpty()) {
            value.setValue(dsdAttributeInstanceDtos.get(0).getValue());
        }
        setFields(value);
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        if (getItem(DsdAttributeDS.VALUE) != null && getItem(DsdAttributeDS.VALUE) instanceof SearchExternalItemSimpleItem) {
            ((SearchExternalItemSimpleItem) getItem(DsdAttributeDS.VALUE)).setResources(externalItemDtos, firstResult, totalResults);
        }
    }

    private SearchExternalItemSimpleItem createEnumeratedValueItem() {
        return new SearchExternalItemSimpleItem(DsdAttributeDS.VALUE, getConstants().datasetAttributeValue(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                getUiHandlers().retrieveItemsFromItemSchemeForDatasetLevelAttribute(dsdAttributeDto.getAttributeRepresentation(), firstResult, maxResults, webCriteria);
            }
        };
    }
}
