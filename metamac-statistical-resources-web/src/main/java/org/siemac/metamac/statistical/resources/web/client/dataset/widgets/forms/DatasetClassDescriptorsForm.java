package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceClassDescriptorsForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasetClassDescriptorsForm extends StatisticalResourceClassDescriptorsForm {

    public DatasetClassDescriptorsForm() {

        ViewTextItem formatExtentObservations = new ViewTextItem(DatasetDS.FORMAT_EXTENT_OBSERVATIONS, getConstants().datasetFormatExtentObservations());
        ViewTextItem formatExtentDimensions = new ViewTextItem(DatasetDS.FORMAT_EXTENT_DIMENSIONS, getConstants().datasetFormatExtentDimensions());

        addFields(formatExtentObservations, formatExtentDimensions);
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        setValue(DatasetDS.FORMAT_EXTENT_OBSERVATIONS, datasetDto.getFormatExtentObservations() != null ? datasetDto.getFormatExtentObservations().toString() : StringUtils.EMPTY);
        setValue(DatasetDS.FORMAT_EXTENT_DIMENSIONS, datasetDto.getFormatExtentDimensions() != null ? datasetDto.getFormatExtentDimensions().toString() : StringUtils.EMPTY);
    }
}
