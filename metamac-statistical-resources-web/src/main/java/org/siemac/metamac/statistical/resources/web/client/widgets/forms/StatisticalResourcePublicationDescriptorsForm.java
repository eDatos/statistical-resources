package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.*;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;

public class StatisticalResourcePublicationDescriptorsForm extends GroupDynamicForm {

    public StatisticalResourcePublicationDescriptorsForm() {
        super(getConstants().formPublicationDescriptors());

        ExternalItemListItem publisher = new ExternalItemListItem(StatisticalResourceDS.PUBLISHER, getConstants().siemacMetadataStatisticalResourcePublisher(), false); 
        
        ExternalItemListItem publisherContributor = new ExternalItemListItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourcePublisherContributor(), false);
        
        ExternalItemListItem mediator = new ExternalItemListItem(StatisticalResourceDS.MEDIATOR, getConstants().siemacMetadataStatisticalResourceMediator(), false);
        
        ViewTextItem dateNewnessUntil = new ViewTextItem(StatisticalResourceDS.NEWNESS_UNTIL_DATE, getConstants().siemacMetadataStatisticalResourceNewnessUntilDate());

        setFields(publisher, publisherContributor, mediator, dateNewnessUntil );
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setExternalItemsValue(getItem(StatisticalResourceDS.PUBLISHER), dto.getPublisher());
        setExternalItemsValue(getItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR), dto.getPublisherContributor());
        setExternalItemsValue(getItem(StatisticalResourceDS.MEDIATOR), dto.getMediator());
        setValue(StatisticalResourceDS.NEWNESS_UNTIL_DATE, dto.getNewnessUntilDate());
    }
}
