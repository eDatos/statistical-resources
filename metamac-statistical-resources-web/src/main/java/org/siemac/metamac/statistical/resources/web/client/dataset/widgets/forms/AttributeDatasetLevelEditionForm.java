package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeDS;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;

public class AttributeDatasetLevelEditionForm extends AttributeDatasetLevelBaseForm {

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        // TODO 
    }

    @Override
    protected void buildNonEnumeratedRepresentationForm(List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        CustomTextItem value = new CustomTextItem(DsdAttributeDS.VALUE, getConstants().datasetAttributeValue());
        if (dsdAttributeInstanceDtos != null && !dsdAttributeInstanceDtos.isEmpty()) {
            value.setValue(dsdAttributeInstanceDtos.get(0).getValue());
        }
        setFields(value);
    }
}
