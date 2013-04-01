package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;

public class StatisticalResourceContentDescriptorsForm extends LifeCycleResourceContentDescriptorsForm {

    public StatisticalResourceContentDescriptorsForm() {

        ViewMultiLanguageTextItem subtitle = new ViewMultiLanguageTextItem(StatisticalResourceDS.SUBTITLE, getConstants().siemacMetadataStatisticalResourceSubtitle());
        ViewMultiLanguageTextItem titleAlternative = new ViewMultiLanguageTextItem(StatisticalResourceDS.TITLE_ALTERNATIVE, getConstants().siemacMetadataStatisticalResourceTitleAlternative());
        ViewMultiLanguageTextItem abstractLogic = new ViewMultiLanguageTextItem(StatisticalResourceDS.ABSTRACT, getConstants().siemacMetadataStatisticalResourceAbstractLogic());
        // TODO keywords

        addFields(subtitle, titleAlternative, abstractLogic);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setLifeCycleResource(siemacMetadataStatisticalResourceDto);
        setValue(StatisticalResourceDS.SUBTITLE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getSubtitle()));
        setValue(StatisticalResourceDS.TITLE_ALTERNATIVE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getTitleAlternative()));
        setValue(StatisticalResourceDS.ABSTRACT, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getAbstractLogic()));
        // TODO keywords
    }
}
