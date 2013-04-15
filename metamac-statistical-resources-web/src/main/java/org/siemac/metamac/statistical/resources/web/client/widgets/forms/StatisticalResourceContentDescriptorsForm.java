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
        ViewMultiLanguageTextItem keywords = new ViewMultiLanguageTextItem(StatisticalResourceDS.KEYWORDS, getConstants().siemacMetadataStatisticalResourceKeywords());

        addFields(subtitle, titleAlternative, abstractLogic, keywords);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setLifeCycleResource(siemacMetadataStatisticalResourceDto);
        setValue(StatisticalResourceDS.SUBTITLE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getSubtitle()));
        setValue(StatisticalResourceDS.TITLE_ALTERNATIVE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getTitleAlternative()));
        setValue(StatisticalResourceDS.ABSTRACT, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getAbstractLogic()));
        setValue(StatisticalResourceDS.KEYWORDS, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getKeywords()));
    }
}
