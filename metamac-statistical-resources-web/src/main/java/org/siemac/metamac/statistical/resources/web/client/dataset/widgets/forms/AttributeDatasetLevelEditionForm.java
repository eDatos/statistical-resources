package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class AttributeDatasetLevelEditionForm extends AttributeBaseForm {

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        SearchExternalItemSimpleItem value = createEnumeratedValueItem();
        if (dsdAttributeInstanceDto.getValue() != null) {
            value.setExternalItem(dsdAttributeInstanceDto.getValue().getExternalItemValue());
        }
        setFields(value);
    }

    @Override
    protected void buildNonEnumeratedRepresentationForm(DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        CustomTextItem value = new CustomTextItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());
        if (dsdAttributeInstanceDto.getValue() != null) {
            value.setValue(dsdAttributeInstanceDto.getValue().getStringValue());
        }
        setFields(value);
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        if (getItem(DsdAttributeInstanceDS.VALUE) != null && getItem(DsdAttributeInstanceDS.VALUE) instanceof SearchExternalItemSimpleItem) {
            ((SearchExternalItemSimpleItem) getItem(DsdAttributeInstanceDS.VALUE)).setResources(externalItemDtos, firstResult, totalResults);
        }
    }

    private SearchExternalItemSimpleItem createEnumeratedValueItem() {
        return new SearchExternalItemSimpleItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                getUiHandlers().retrieveItemsFromItemSchemeForDatasetLevelAttribute(dsdAttributeDto.getAttributeRepresentation(), firstResult, maxResults, webCriteria);
            }
        };
    }
}
