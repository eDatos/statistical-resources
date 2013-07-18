package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;

public class DatasetProductionDescriptorsForm extends StatisticalResourceProductionDescriptorsForm {

    public DatasetProductionDescriptorsForm() {

        ExternalItemLinkItem relatedDsd = new ExternalItemLinkItem(DatasetDS.RELATED_DSD, getConstants().datasetRelatedDSD());

        addFields(relatedDsd);
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        StatisticalResourcesFormUtils.setExternalItemValue(getItem(DatasetDS.RELATED_DSD), datasetDto.getRelatedDsd());
    }
}
