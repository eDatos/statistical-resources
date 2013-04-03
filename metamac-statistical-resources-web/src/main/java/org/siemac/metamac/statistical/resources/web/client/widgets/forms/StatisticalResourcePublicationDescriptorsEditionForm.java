package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemListItem;

public class StatisticalResourcePublicationDescriptorsEditionForm extends GroupDynamicForm {

    public StatisticalResourcePublicationDescriptorsEditionForm() {
        super(getConstants().formPublicationDescriptors());

        // TODO make this three fields editable!
        ExternalItemListItem publisher = new ExternalItemListItem(StatisticalResourceDS.PUBLISHER, getConstants().siemacMetadataStatisticalResourcePublisher(), false);
        ExternalItemListItem publisherContributor = new ExternalItemListItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourcePublisherContributor(), false);
        ExternalItemListItem mediator = new ExternalItemListItem(StatisticalResourceDS.MEDIATOR, getConstants().siemacMetadataStatisticalResourceMediator(), false);

        CustomDateItem newnessUntilDate = new CustomDateItem(StatisticalResourceDS.NEWNESS_UNTIL_DATE, getConstants().siemacMetadataStatisticalResourceNewnessUntilDate());

        setFields(publisher, publisherContributor, mediator, newnessUntilDate);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        ((ExternalItemListItem) getItem(StatisticalResourceDS.PUBLISHER)).setExternalItems(siemacMetadataStatisticalResourceDto.getPublisher());
        ((ExternalItemListItem) getItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR)).setExternalItems(siemacMetadataStatisticalResourceDto.getPublisherContributor());
        ((ExternalItemListItem) getItem(StatisticalResourceDS.MEDIATOR)).setExternalItems(siemacMetadataStatisticalResourceDto.getMediator());
        setValue(StatisticalResourceDS.NEWNESS_UNTIL_DATE, siemacMetadataStatisticalResourceDto.getNewnessUntilDate());
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        // TODO publisher
        // TODO publisher contributor
        // TODO mediator
        siemacMetadataStatisticalResourceDto.setNewnessUntilDate(((CustomDateItem) getItem(StatisticalResourceDS.NEWNESS_UNTIL_DATE)).getValueAsDate());
        return siemacMetadataStatisticalResourceDto;
    }
}
