package org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourcesValue;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.SiemacMetadataResourceRelationDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;

public class PublicationResourceRelationDescriptorsForm extends SiemacMetadataResourceRelationDescriptorsForm {

    public PublicationResourceRelationDescriptorsForm() {
        RelatedResourceListItem hasPart = new RelatedResourceListItem(PublicationDS.HAS_PART, getConstants().siemacMetadataStatisticalResourceHasPart(), false, getRecordNavigationHandler());

        addFields(hasPart);
    }

    public void setPublicationVersionDto(PublicationVersionDto dto) {
        setSiemacMetadataStatisticalResourceDto(dto);

        setRelatedResourcesValue(getItem(PublicationDS.HAS_PART), dto.getHasPart());
    }

}
