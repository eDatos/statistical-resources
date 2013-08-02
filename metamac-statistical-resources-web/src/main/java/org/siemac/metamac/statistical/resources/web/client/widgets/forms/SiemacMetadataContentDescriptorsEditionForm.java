package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;

public class SiemacMetadataContentDescriptorsEditionForm extends LifeCycleResourceContentDescriptorsEditionForm {

    public SiemacMetadataContentDescriptorsEditionForm() {

        MultiLanguageTextItem subtitle = new MultiLanguageTextItem(SiemacMetadataDS.SUBTITLE, getConstants().siemacMetadataStatisticalResourceSubtitle());
        MultiLanguageTextItem titleAlternative = new MultiLanguageTextItem(SiemacMetadataDS.TITLE_ALTERNATIVE, getConstants().siemacMetadataStatisticalResourceTitleAlternative());
        MultilanguageRichTextEditorItem abstractLogic = new MultilanguageRichTextEditorItem(SiemacMetadataDS.ABSTRACT, getConstants().siemacMetadataStatisticalResourceAbstractLogic());
        MultiLanguageTextAreaItem keywords = createKeywordsItem();

        addFields(subtitle, titleAlternative, abstractLogic, keywords);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
        setValue(SiemacMetadataDS.SUBTITLE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getSubtitle()));
        setValue(SiemacMetadataDS.TITLE_ALTERNATIVE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getTitleAlternative()));
        setValue(SiemacMetadataDS.ABSTRACT, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getAbstractLogic()));
        setValue(SiemacMetadataDS.KEYWORDS, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getKeywords()));
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto = (SiemacMetadataStatisticalResourceDto) getLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
        siemacMetadataStatisticalResourceDto.setSubtitle((InternationalStringDto) getValue(SiemacMetadataDS.SUBTITLE));
        siemacMetadataStatisticalResourceDto.setTitleAlternative((InternationalStringDto) getValue(SiemacMetadataDS.TITLE_ALTERNATIVE));
        siemacMetadataStatisticalResourceDto.setAbstractLogic((InternationalStringDto) getValue(SiemacMetadataDS.ABSTRACT));
        siemacMetadataStatisticalResourceDto.setKeywords((InternationalStringDto) getValue(SiemacMetadataDS.KEYWORDS));
        return siemacMetadataStatisticalResourceDto;
    }

    private MultiLanguageTextAreaItem createKeywordsItem() {
        FormItemIcon infoIcon = new FormItemIcon();
        infoIcon.setSrc(GlobalResources.RESOURCE.info().getURL());
        infoIcon.setPrompt(StatisticalResourcesWeb.getMessages().siemacMetadataStatisticalResourceKeywordsInfo());
        MultiLanguageTextAreaItem keywords = new MultiLanguageTextAreaItem(SiemacMetadataDS.KEYWORDS, getConstants().siemacMetadataStatisticalResourceKeywords());
        keywords.setIcons(infoIcon);
        return keywords;
    }

}
