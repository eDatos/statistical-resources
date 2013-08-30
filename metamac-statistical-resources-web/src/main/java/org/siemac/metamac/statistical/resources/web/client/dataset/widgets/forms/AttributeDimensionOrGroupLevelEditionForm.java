package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.AttributeValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DimensionCoverageValuesSelectionItem;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class AttributeDimensionOrGroupLevelEditionForm extends AttributeBaseForm {

    private DsdAttributeInstanceDto dsdAttributeInstanceDto;

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        this.dsdAttributeInstanceDto = dsdAttributeInstanceDto;
        DimensionCoverageValuesSelectionItem dimensionCoverageValuesSelectionItem = createDimensionValuesSelectionItem(DsdAttributeInstanceDS.DIMENSION_SELECTION_VALUES, getConstants()
                .datasetAttributeDimensionValuesSelection(), dsdAttributeInstanceDto);
        SearchExternalItemSimpleItem value = createEnumeratedValueItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());
        // TODO set values
        setFields(dimensionCoverageValuesSelectionItem, value);
    }

    @Override
    protected void buildNonEnumeratedRepresentationForm(DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        this.dsdAttributeInstanceDto = dsdAttributeInstanceDto;
        DimensionCoverageValuesSelectionItem dimensionCoverageValuesSelectionItem = createDimensionValuesSelectionItem(DsdAttributeInstanceDS.DIMENSION_SELECTION_VALUES, getConstants()
                .datasetAttributeDimensionValuesSelection(), dsdAttributeInstanceDto);
        CustomTextItem value = new CustomTextItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());
        // TODO set values
        setFields(dimensionCoverageValuesSelectionItem, value);
    }

    private DimensionCoverageValuesSelectionItem createDimensionValuesSelectionItem(String name, String title, DsdAttributeInstanceDto dsdAttributeInstanceDto) {

        Set<String> dimensionIds = dsdAttributeInstanceDto.getCodeDimensions().keySet();

        DimensionCoverageValuesSelectionItem selectionItem = new DimensionCoverageValuesSelectionItem(name, title, dimensionIds, true);
        selectionItem.setColSpan(4);

        // Load dimension values
        for (String dimensionId : dimensionIds) {
            getUiHandlers().retrieveDimensionCoverage(dimensionId, new MetamacWebCriteria());
        }

        return selectionItem;
    }

    public void setDimensionCoverageValues(String dimensionId, List<CodeItemDto> codeItemDtos) {
        ((DimensionCoverageValuesSelectionItem) getItem(DsdAttributeInstanceDS.DIMENSION_SELECTION_VALUES)).setDimensionCoverageValues(dimensionId, codeItemDtos);
    }

    public DsdAttributeInstanceDto getDsdAttributeInstanceDto() {
        // Code dimensions
        Map<String, List<CodeItemDto>> codeItems = ((DimensionCoverageValuesSelectionItem) getItem(DsdAttributeInstanceDS.DIMENSION_SELECTION_VALUES)).getSelectedCodeDimensions();
        dsdAttributeInstanceDto.setCodeDimensions(codeItems);

        // Value
        AttributeValueDto attributeValueDto = new AttributeValueDto();
        if (getItem(DsdAttributeInstanceDS.VALUE) instanceof CustomTextItem) {
            attributeValueDto.setStringValue(getValueAsString(DsdAttributeInstanceDS.VALUE));
        } else if (getItem(DsdAttributeInstanceDS.VALUE) instanceof SearchExternalItemSimpleItem) {
            ExternalItemDto selectedExternalItemDto = ((SearchExternalItemSimpleItem) getItem(DsdAttributeInstanceDS.VALUE)).getExternalItemDto();
            attributeValueDto.setExternalItemValue(selectedExternalItemDto);
        }
        dsdAttributeInstanceDto.setValue(attributeValueDto);

        return dsdAttributeInstanceDto;
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDimensionOrGroupLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        if (getItem(DsdAttributeInstanceDS.VALUE) != null && getItem(DsdAttributeInstanceDS.VALUE) instanceof SearchExternalItemSimpleItem) {
            ((SearchExternalItemSimpleItem) getItem(DsdAttributeInstanceDS.VALUE)).setResources(externalItemDtos, firstResult, totalResults);
        }
    }

    private SearchExternalItemSimpleItem createEnumeratedValueItem(String name, String title) {
        return new SearchExternalItemSimpleItem(name, title, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                getUiHandlers().retrieveItemsFromItemSchemeForDimensionOrGroupLevelAttribute(dsdAttributeDto.getAttributeRepresentation(), firstResult, maxResults, webCriteria);
            }
        };
    }
}
