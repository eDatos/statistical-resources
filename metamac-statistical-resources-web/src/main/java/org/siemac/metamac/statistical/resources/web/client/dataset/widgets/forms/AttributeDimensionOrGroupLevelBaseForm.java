package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DimensionCoverageValuesSelectionItem;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;

public abstract class AttributeDimensionOrGroupLevelBaseForm extends AttributeBaseForm {

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        DimensionCoverageValuesSelectionItem dimensionCoverageValuesSelectionItem = createDimensionValuesSelectionItem(DsdAttributeInstanceDS.DIMENSION_SELECTION_VALUES, getConstants()
                .datasetAttributeDimensionValuesSelection(), dsdAttributeInstanceDto);

        ExternalItemLinkItem value = createEnumeratedValueItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());

        setFields(dimensionCoverageValuesSelectionItem, value);

        setDimensionValues(dsdAttributeDto, dsdAttributeInstanceDto);

        if (dsdAttributeInstanceDto.getValue() != null) {
            setExternalItemValue(getItem(DsdAttributeInstanceDS.VALUE), dsdAttributeInstanceDto.getValue().getExternalItemValue());
        }
    }

    protected abstract void setDimensionValues(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto);

    protected abstract ExternalItemLinkItem createEnumeratedValueItem(String value, String datasetAttributeValue);

    protected abstract DimensionCoverageValuesSelectionItem createDimensionValuesSelectionItem(String dimensionSelectionValues, String datasetAttributeDimensionValuesSelection,
            DsdAttributeInstanceDto dsdAttributeInstanceDto);

}
