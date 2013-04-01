package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;

public class DatasetProductionDescriptorsForm extends StatisticalResourceProductionDescriptorsForm {

    public DatasetProductionDescriptorsForm() {
        // TODO add fields
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        // TODO set dataset fields
    }
}
