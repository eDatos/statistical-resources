package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class SiemacMetadataIntellectualPropertyDescriptorsForm extends GroupDynamicForm {

    public SiemacMetadataIntellectualPropertyDescriptorsForm() {
        super(getConstants().formIntellectualPropertyDescriptors());

        ViewMultiLanguageTextItem accessRights = new ViewMultiLanguageTextItem(StatisticalResourceDS.ACCESS_RIGHTS, getConstants().siemacMetadataStatisticalResourceAccessRights());
        ViewTextItem copyrightDate = new ViewTextItem(StatisticalResourceDS.COPYRIGHT_DATE, getConstants().siemacMetadataStatisticalResourceCopyrightedDate());

        addFields(copyrightDate, accessRights);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setValue(StatisticalResourceDS.ACCESS_RIGHTS, RecordUtils.getInternationalStringRecord(dto.getAccessRights()));
        setValue(StatisticalResourceDS.COPYRIGHT_DATE, dto.getCopyrightedDate());
    }
}
