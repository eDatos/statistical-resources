package org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class PublicationIdentifiersForm extends NameableResourceIdentifiersForm {

    public PublicationIdentifiersForm() {

        ViewTextItem publicationStreamStatus = new ViewTextItem(LifeCycleResourceDS.PUBLICATION_STREAM_STATUS, getConstants().lifeCycleStatisticalResourceStreamMsgStatus());
        publicationStreamStatus.setWidth(20);
        addFields(publicationStreamStatus);
    }

    public void setPublicationVersionDto(PublicationVersionDto publicationDto) {
        setNameableStatisticalResourceDto(publicationDto);
        getItem(LifeCycleResourceDS.PUBLICATION_STREAM_STATUS).setIcons(CommonUtils.getPublicationStreamStatusIcon(publicationDto.getPublicationStreamStatus()));
    }
}
