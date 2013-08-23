package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;

public abstract class AttributeDatasetLevelBaseForm extends AttributeBaseForm {

    public void setDsdAttributeDto(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        clearForm();
        setGroupTitle(dsdAttributeDto.getIdentifier());

        if (CommonUtils.hasEnumeratedRepresentation(dsdAttributeDto)) {
            buildEnumeratedRepresentationForm(dsdAttributeDto, dsdAttributeInstanceDtos);
        } else if (CommonUtils.hasNonEnumeratedRepresentation(dsdAttributeDto)) {
            buildNonEnumeratedRepresentationForm(dsdAttributeInstanceDtos);
        }
    }

    protected abstract void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos);
    protected abstract void buildNonEnumeratedRepresentationForm(List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos);
}
