package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsEditionForm;

public class DatasetProductionDescriptorsEditionForm extends StatisticalResourceProductionDescriptorsEditionForm {

    public DatasetProductionDescriptorsEditionForm() {
        // TODO add fields
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        // TODO set dataset fields
    }

    public DatasetDto getDatasetDto(DatasetDto datasetDto) {
        datasetDto = (DatasetDto) getSiemacMetadataStatisticalResourceDto(datasetDto);
        // TODO set dataset fields
        return datasetDto;
    }
}
