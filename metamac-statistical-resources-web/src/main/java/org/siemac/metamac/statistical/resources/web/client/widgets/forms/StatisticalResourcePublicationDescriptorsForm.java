package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class StatisticalResourcePublicationDescriptorsForm extends GroupDynamicForm {

    public StatisticalResourcePublicationDescriptorsForm() {
        super(getConstants().formPublicationDescriptors());

        ExternalItemListItem publisher = new ExternalItemListItem(StatisticalResourceDS.PUBLISHER, getConstants().siemacMetadataStatisticalResourcePublisher(), false);
        ExternalItemListItem publisherContributor = new ExternalItemListItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourcePublisherContributor(), false);
        ExternalItemListItem mediator = new ExternalItemListItem(StatisticalResourceDS.MEDIATOR, getConstants().siemacMetadataStatisticalResourceMediator(), false);
        ViewTextItem dateNewnessUntil = new ViewTextItem(StatisticalResourceDS.NEWNESS_UNTIL_DATE, getConstants().siemacMetadataStatisticalResourceNewnessUntilDate());

        setFields(publisher, publisherContributor, mediator, dateNewnessUntil);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        ((ExternalItemListItem) getItem(StatisticalResourceDS.PUBLISHER)).setExternalItems(siemacMetadataStatisticalResourceDto.getPublisher());
        ((ExternalItemListItem) getItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR)).setExternalItems(siemacMetadataStatisticalResourceDto.getPublisherContributor());
        ((ExternalItemListItem) getItem(StatisticalResourceDS.MEDIATOR)).setExternalItems(siemacMetadataStatisticalResourceDto.getMediator());
        setValue(StatisticalResourceDS.NEWNESS_UNTIL_DATE, siemacMetadataStatisticalResourceDto.getNewnessUntilDate());
    }
}
