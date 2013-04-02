package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsForm;

public class DatasetProductionDescriptorsForm extends StatisticalResourceProductionDescriptorsForm {

    public DatasetProductionDescriptorsForm() {
        // TODO add fields
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        // TODO set dataset fields
    }
}
