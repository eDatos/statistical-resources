package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class SiemacMetadataClassDescriptorsEditionForm extends GroupDynamicForm {

    public SiemacMetadataClassDescriptorsEditionForm() {
        super(getConstants().formClassDescriptors());

        ViewTextItem type = new ViewTextItem(SiemacMetadataDS.TYPE, getConstants().siemacMetadataStatisticalResourceType());
        // TODO format

        setFields(type);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(SiemacMetadataDS.TYPE, CommonUtils.getStatisticalResourceTypeName(siemacMetadataStatisticalResourceDto.getType()));
        // TODO format
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        // TODO format
        return siemacMetadataStatisticalResourceDto;
    }
}
