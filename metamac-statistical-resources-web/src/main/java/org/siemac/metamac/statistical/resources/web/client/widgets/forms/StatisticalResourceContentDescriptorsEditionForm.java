package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;

public class StatisticalResourceContentDescriptorsEditionForm extends LifeCycleResourceContentDescriptorsEditionForm {

    public StatisticalResourceContentDescriptorsEditionForm() {

        MultiLanguageTextItem subtitle = new MultiLanguageTextItem(StatisticalResourceDS.SUBTITLE, getConstants().siemacMetadataStatisticalResourceSubtitle());
        MultiLanguageTextItem titleAlternative = new MultiLanguageTextItem(StatisticalResourceDS.TITLE_ALTERNATIVE, getConstants().siemacMetadataStatisticalResourceTitleAlternative());
        MultilanguageRichTextEditorItem abstractLogic = new MultilanguageRichTextEditorItem(StatisticalResourceDS.ABSTRACT, getConstants().siemacMetadataStatisticalResourceAbstractLogic());
        MultiLanguageTextAreaItem keywords = createKeywordsItem();

        addFields(subtitle, titleAlternative, abstractLogic, keywords);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
        setValue(StatisticalResourceDS.SUBTITLE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getSubtitle()));
        setValue(StatisticalResourceDS.TITLE_ALTERNATIVE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getTitleAlternative()));
        setValue(StatisticalResourceDS.ABSTRACT, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getAbstractLogic()));
        setValue(StatisticalResourceDS.KEYWORDS, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getKeywords()));
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto = (SiemacMetadataStatisticalResourceDto) getLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
        siemacMetadataStatisticalResourceDto.setSubtitle((InternationalStringDto) getValue(StatisticalResourceDS.SUBTITLE));
        siemacMetadataStatisticalResourceDto.setTitleAlternative((InternationalStringDto) getValue(StatisticalResourceDS.TITLE_ALTERNATIVE));
        siemacMetadataStatisticalResourceDto.setAbstractLogic((InternationalStringDto) getValue(StatisticalResourceDS.ABSTRACT));
        siemacMetadataStatisticalResourceDto.setKeywords((InternationalStringDto) getValue(StatisticalResourceDS.KEYWORDS));
        return siemacMetadataStatisticalResourceDto;
    }
    
    private MultiLanguageTextAreaItem createKeywordsItem() {
        FormItemIcon infoIcon = new FormItemIcon();
        infoIcon.setSrc(GlobalResources.RESOURCE.info().getURL());
        infoIcon.setPrompt(StatisticalResourcesWeb.getMessages().siemacMetadataStatisticalResourceKeywordsInfo());
        MultiLanguageTextAreaItem keywords = new MultiLanguageTextAreaItem(StatisticalResourceDS.KEYWORDS, getConstants().siemacMetadataStatisticalResourceKeywords());
        keywords.setIcons(infoIcon);
        return keywords;
    }

}
