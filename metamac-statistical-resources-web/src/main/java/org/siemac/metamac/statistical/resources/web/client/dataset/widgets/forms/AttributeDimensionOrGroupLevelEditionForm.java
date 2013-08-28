package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;
import java.util.Set;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DimensionCoverageValuesSelectionItem;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class AttributeDimensionOrGroupLevelEditionForm extends AttributeBaseForm {

    private DatasetAttributesTabUiHandlers uiHandlers;

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        DimensionCoverageValuesSelectionItem dimensionCoverageValuesSelectionItem = createDimensionValuesSelectionItem(DsdAttributeInstanceDS.DIMENSION_SELECTION_VALUES, getConstants()
                .datasetAttributeDimensionValuesSelection(), dsdAttributeInstanceDto);
        SearchExternalItemSimpleItem value = createEnumeratedValueItem();
        // TODO set values
        setFields(dimensionCoverageValuesSelectionItem, value);
    }

    @Override
    protected void buildNonEnumeratedRepresentationForm(DsdAttributeInstanceDto dsdAttributeInstanceDto) {
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

    @Override
    public DatasetAttributesTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    @Override
    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDimensionOrGroupLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        if (getItem(DsdAttributeInstanceDS.VALUE) != null && getItem(DsdAttributeInstanceDS.VALUE) instanceof SearchExternalItemSimpleItem) {
            ((SearchExternalItemSimpleItem) getItem(DsdAttributeInstanceDS.VALUE)).setResources(externalItemDtos, firstResult, totalResults);
        }
    }

    private SearchExternalItemSimpleItem createEnumeratedValueItem() {
        return new SearchExternalItemSimpleItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                getUiHandlers().retrieveItemsFromItemSchemeForDimensionOrGroupLevelAttribute(dsdAttributeDto.getAttributeRepresentation(), firstResult, maxResults, webCriteria);
            }
        };
    }
}
