package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasetIdentifiersForm extends NameableResourceIdentifiersForm {

    public DatasetIdentifiersForm() {

        ViewTextItem datasetRepositoryId = new ViewTextItem(DatasetDS.DATASET_REPOSITORY_ID, getConstants().datasetRepositoryId());

        addFields(datasetRepositoryId);
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetDto) {
        setNameableStatisticalResourceDto(datasetDto);
        setValue(DatasetDS.DATASET_REPOSITORY_ID, datasetDto.getDatasetRepositoryId());
    }
}
