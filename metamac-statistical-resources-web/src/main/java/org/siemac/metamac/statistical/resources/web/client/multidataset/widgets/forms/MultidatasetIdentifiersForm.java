package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class MultidatasetIdentifiersForm extends NameableResourceIdentifiersForm {

    public MultidatasetIdentifiersForm() {

        ViewTextItem multidatasetStreamStatus = new ViewTextItem(LifeCycleResourceDS.PUBLICATION_STREAM_STATUS, getConstants().lifeCycleStatisticalResourceStreamMsgStatus());
        multidatasetStreamStatus.setWidth(20);
        addFields(multidatasetStreamStatus);
    }

    public void setMultidatasetVersionDto(MultidatasetVersionDto multidatasetDto) {
        setNameableStatisticalResourceDto(multidatasetDto);
        getItem(LifeCycleResourceDS.PUBLICATION_STREAM_STATUS).setIcons(CommonUtils.getPublicationStreamStatusIcon(multidatasetDto.getPublicationStreamStatus()));
    }
}
