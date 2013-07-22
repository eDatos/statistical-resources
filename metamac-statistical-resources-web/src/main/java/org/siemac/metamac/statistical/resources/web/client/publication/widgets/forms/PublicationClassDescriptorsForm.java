package org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceClassDescriptorsForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class PublicationClassDescriptorsForm extends StatisticalResourceClassDescriptorsForm {

    public PublicationClassDescriptorsForm() {

        ViewTextItem formatExtentResources = new ViewTextItem(PublicationDS.FORMAT_EXTENT_RESOURCES, getConstants().publicationFormatExtentResources());

        addFields(formatExtentResources);
    }

    public void setPublicationDto(PublicationVersionDto publicationDto) {
        setSiemacMetadataStatisticalResourceDto(publicationDto);
        setValue(PublicationDS.FORMAT_EXTENT_RESOURCES, publicationDto.getFormatExtentResources() != null ? publicationDto.getFormatExtentResources().toString() : StringUtils.EMPTY);
    }
}
