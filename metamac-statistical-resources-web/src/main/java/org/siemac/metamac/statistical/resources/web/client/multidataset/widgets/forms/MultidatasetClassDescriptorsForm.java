package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds.MultidatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.SiemacMetadataClassDescriptorsForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class MultidatasetClassDescriptorsForm extends SiemacMetadataClassDescriptorsForm {

    public MultidatasetClassDescriptorsForm() {

        ViewTextItem formatExtentResources = new ViewTextItem(MultidatasetDS.FORMAT_EXTENT_RESOURCES, getConstants().multidatasetFormatExtentResources());

        addFields(formatExtentResources);
    }

    public void setMultidatasetDto(MultidatasetVersionDto multidatasetDto) {
        setSiemacMetadataStatisticalResourceDto(multidatasetDto);
        setValue(MultidatasetDS.FORMAT_EXTENT_RESOURCES, multidatasetDto.getFormatExtentResources() != null ? multidatasetDto.getFormatExtentResources().toString() : StringUtils.EMPTY);
    }
}
