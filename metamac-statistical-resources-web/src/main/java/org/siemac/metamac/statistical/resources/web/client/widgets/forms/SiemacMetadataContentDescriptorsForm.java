package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;

public class SiemacMetadataContentDescriptorsForm extends LifeCycleResourceContentDescriptorsForm {

    public SiemacMetadataContentDescriptorsForm() {

        ViewMultiLanguageTextItem subtitle = new ViewMultiLanguageTextItem(SiemacMetadataDS.SUBTITLE, getConstants().siemacMetadataStatisticalResourceSubtitle());
        ViewMultiLanguageTextItem titleAlternative = new ViewMultiLanguageTextItem(SiemacMetadataDS.TITLE_ALTERNATIVE, getConstants().siemacMetadataStatisticalResourceTitleAlternative());
        ViewMultiLanguageTextItem abstractLogic = new ViewMultiLanguageTextItem(SiemacMetadataDS.ABSTRACT, getConstants().siemacMetadataStatisticalResourceAbstractLogic());
        ViewMultiLanguageTextItem keywords = new ViewMultiLanguageTextItem(SiemacMetadataDS.KEYWORDS, getConstants().siemacMetadataStatisticalResourceKeywords());

        addFields(subtitle, titleAlternative, abstractLogic, keywords);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setLifeCycleResource(siemacMetadataStatisticalResourceDto);
        setValue(SiemacMetadataDS.SUBTITLE, siemacMetadataStatisticalResourceDto.getSubtitle());
        setValue(SiemacMetadataDS.TITLE_ALTERNATIVE, siemacMetadataStatisticalResourceDto.getTitleAlternative());
        setValue(SiemacMetadataDS.ABSTRACT, siemacMetadataStatisticalResourceDto.getAbstractLogic());
        setValue(SiemacMetadataDS.KEYWORDS, siemacMetadataStatisticalResourceDto.getKeywords());
    }
}
