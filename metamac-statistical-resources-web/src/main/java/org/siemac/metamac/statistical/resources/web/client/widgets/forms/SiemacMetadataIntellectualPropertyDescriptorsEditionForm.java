package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class SiemacMetadataIntellectualPropertyDescriptorsEditionForm extends GroupDynamicForm {

    public SiemacMetadataIntellectualPropertyDescriptorsEditionForm() {
        super(getConstants().formIntellectualPropertyDescriptors());

        MultiLanguageTextItem accessRights = new MultiLanguageTextItem(SiemacMetadataDS.ACCESS_RIGHTS, getConstants().siemacMetadataStatisticalResourceAccessRights());
        ViewTextItem copyrightDate = new ViewTextItem(SiemacMetadataDS.COPYRIGHT_DATE, getConstants().siemacMetadataStatisticalResourceCopyrightedDate());

        addFields(copyrightDate, accessRights);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setValue(SiemacMetadataDS.ACCESS_RIGHTS, RecordUtils.getInternationalStringRecord(dto.getAccessRights()));
        setValue(SiemacMetadataDS.COPYRIGHT_DATE, dto.getCopyrightedDate());
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        dto.setAccessRights((InternationalStringDto) getValue(SiemacMetadataDS.ACCESS_RIGHTS));
        return dto;
    }
}