package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DimensionCoverageValuesSelectionItem;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;

import com.smartgwt.client.widgets.form.fields.FormItem;

public abstract class AttributeDimensionOrGroupLevelBaseForm extends AttributeBaseForm {

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        DimensionCoverageValuesSelectionItem dimensionCoverageValuesSelectionItem = createDimensionValuesSelectionItem(DsdAttributeInstanceDS.DIMENSION_SELECTION_VALUES, getConstants()
                .datasetAttributeDimensionValuesSelection(), dsdAttributeInstanceDto);

        FormItem value = createEnumeratedValueItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());

        setFields(dimensionCoverageValuesSelectionItem, value);

        setDimensionValues(dsdAttributeDto, dsdAttributeInstanceDto);

        if (dsdAttributeInstanceDto.getValue() != null) {
            setValue(DsdAttributeInstanceDS.VALUE, RecordUtils.getExternalItemRecord(dsdAttributeInstanceDto.getValue().getExternalItemValue()));
        }
    }
    protected abstract void setDimensionValues(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto);

    protected abstract FormItem createEnumeratedValueItem(String value, String datasetAttributeValue);

    protected abstract DimensionCoverageValuesSelectionItem createDimensionValuesSelectionItem(String dimensionSelectionValues, String datasetAttributeDimensionValuesSelection,
            DsdAttributeInstanceDto dsdAttributeInstanceDto);

}
