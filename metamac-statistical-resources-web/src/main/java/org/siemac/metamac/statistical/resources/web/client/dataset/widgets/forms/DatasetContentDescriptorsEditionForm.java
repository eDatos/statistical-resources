package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceContentDescriptorsEditionForm;

public class DatasetContentDescriptorsEditionForm extends StatisticalResourceContentDescriptorsEditionForm {

    public DatasetContentDescriptorsEditionForm() {
        // TODO add fields
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        // TODO set dataset fields
    }

    public DatasetDto getDatasetDto(DatasetDto datasetDto) {
        // TODO set fields
        datasetDto = (DatasetDto) getSiemacMetadataStatisticalResourceDto(datasetDto);
        return datasetDto;
    }
}
