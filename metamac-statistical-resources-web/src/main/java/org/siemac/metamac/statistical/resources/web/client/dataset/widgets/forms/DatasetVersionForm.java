package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasetVersionForm extends LifeCycleResourceVersionForm {

    public DatasetVersionForm() {
        super();

        ViewTextItem dateNextUpdate = new ViewTextItem(DatasetDS.DATE_NEXT_UPDATE, getConstants().datasetDateNextUpdate());
        ExternalItemLinkItem updateFrequency = new ExternalItemLinkItem(DatasetDS.UPDATE_FRECUENCY, getConstants().datasetUpdateFrequency());

        addFields(dateNextUpdate, updateFrequency);
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetDto) {
        setLifeCycleStatisticalResourceDto(datasetDto);
        setValue(DatasetDS.DATE_NEXT_UPDATE, datasetDto.getDateNextUpdate());
        setValue(DatasetDS.UPDATE_FRECUENCY, datasetDto.getUpdateFrequency());
    }
}
