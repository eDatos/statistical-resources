package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;

public class StatisticalResourceContentDescriptorsEditionForm extends LifeCycleResourceContentDescriptorsEditionForm {

    public StatisticalResourceContentDescriptorsEditionForm() {

        MultiLanguageTextItem subtitle = new MultiLanguageTextItem(StatisticalResourceDS.SUBTITLE, getConstants().siemacMetadataStatisticalResourceSubtitle());
        MultiLanguageTextItem titleAlternative = new MultiLanguageTextItem(StatisticalResourceDS.TITLE_ALTERNATIVE, getConstants().siemacMetadataStatisticalResourceTitleAlternative());
        MultiLanguageTextItem abstractLogic = new MultiLanguageTextItem(StatisticalResourceDS.ABSTRACT, getConstants().siemacMetadataStatisticalResourceAbstractLogic());
        // TODO keywords

        addFields(subtitle, titleAlternative, abstractLogic);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
        setValue(StatisticalResourceDS.SUBTITLE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getSubtitle()));
        setValue(StatisticalResourceDS.TITLE_ALTERNATIVE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getTitleAlternative()));
        setValue(StatisticalResourceDS.ABSTRACT, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getAbstractLogic()));
        // TODO keywords
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto = (SiemacMetadataStatisticalResourceDto) getLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
        siemacMetadataStatisticalResourceDto.setSubtitle((InternationalStringDto) getValue(StatisticalResourceDS.SUBTITLE));
        siemacMetadataStatisticalResourceDto.setTitleAlternative((InternationalStringDto) getValue(StatisticalResourceDS.TITLE_ALTERNATIVE));
        siemacMetadataStatisticalResourceDto.setAbstractLogic((InternationalStringDto) getValue(StatisticalResourceDS.ABSTRACT));
        // TODO keywords
        return siemacMetadataStatisticalResourceDto;
    }
}
