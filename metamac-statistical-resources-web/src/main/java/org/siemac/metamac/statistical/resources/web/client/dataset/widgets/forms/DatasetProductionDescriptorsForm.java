package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsForm;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasetProductionDescriptorsForm extends StatisticalResourceProductionDescriptorsForm {

    public DatasetProductionDescriptorsForm() {

        ViewTextItem relatedDsd = new ViewTextItem(DatasetDS.RELATED_DSD, getConstants().datasetRelatedDSD());

        addFields(relatedDsd);
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        setValue(DatasetDS.RELATED_DSD, ExternalItemUtils.getExternalItemName(datasetDto.getRelatedDsd()));
    }
}
