package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class SiemacMetadataClassDescriptorsForm extends GroupDynamicForm {

    public SiemacMetadataClassDescriptorsForm() {
        super(getConstants().formClassDescriptors());

        ViewTextItem type = new ViewTextItem(SiemacMetadataDS.TYPE, getConstants().siemacMetadataStatisticalResourceType());
        
        setFields(type);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(SiemacMetadataDS.TYPE, CommonUtils.getStatisticalResourceTypeName(siemacMetadataStatisticalResourceDto.getType()));
    }
}
