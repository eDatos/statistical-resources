package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.base.checks.SiemacMetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;

public class StatisticalResourceContentDescriptorsEditionForm extends LifeCycleResourceContentDescriptorsEditionForm {
    private ViewMultiLanguageTextItem keywordsView;
    private MultiLanguageTextAreaItem keywords; 
    
    public StatisticalResourceContentDescriptorsEditionForm() {

        MultiLanguageTextItem subtitle = new MultiLanguageTextItem(StatisticalResourceDS.SUBTITLE, getConstants().siemacMetadataStatisticalResourceSubtitle());
        MultiLanguageTextItem titleAlternative = new MultiLanguageTextItem(StatisticalResourceDS.TITLE_ALTERNATIVE, getConstants().siemacMetadataStatisticalResourceTitleAlternative());
        MultiLanguageTextAreaItem abstractLogic = new MultiLanguageTextAreaItem(StatisticalResourceDS.ABSTRACT, getConstants().siemacMetadataStatisticalResourceAbstractLogic());
        keywordsView = new ViewMultiLanguageTextItem(StatisticalResourceDS.KEYWORDS_VIEW, getConstants().siemacMetadataStatisticalResourceKeywords());
        keywords = new MultiLanguageTextAreaItem(StatisticalResourceDS.KEYWORDS, getConstants().siemacMetadataStatisticalResourceKeywords());
        
        addFields(subtitle, titleAlternative, abstractLogic, keywordsView, keywords);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
        setValue(StatisticalResourceDS.SUBTITLE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getSubtitle()));
        setValue(StatisticalResourceDS.TITLE_ALTERNATIVE, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getTitleAlternative()));
        setValue(StatisticalResourceDS.ABSTRACT, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getAbstractLogic()));
        setValue(StatisticalResourceDS.KEYWORDS_VIEW, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getKeywords()));
        keywordsView.setShowIfCondition(getStaticKeywordsFormItemIfFunction(siemacMetadataStatisticalResourceDto));
        setValue(StatisticalResourceDS.KEYWORDS, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getKeywords()));
        keywords.setShowIfCondition(getKeywordsFormItemIfFunction(siemacMetadataStatisticalResourceDto));
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto = (SiemacMetadataStatisticalResourceDto) getLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
        siemacMetadataStatisticalResourceDto.setSubtitle((InternationalStringDto) getValue(StatisticalResourceDS.SUBTITLE));
        siemacMetadataStatisticalResourceDto.setTitleAlternative((InternationalStringDto) getValue(StatisticalResourceDS.TITLE_ALTERNATIVE));
        siemacMetadataStatisticalResourceDto.setAbstractLogic((InternationalStringDto) getValue(StatisticalResourceDS.ABSTRACT));
        siemacMetadataStatisticalResourceDto.setKeywords((InternationalStringDto) getValue(StatisticalResourceDS.KEYWORDS));
        return siemacMetadataStatisticalResourceDto;
    }
    
    private FormItemIfFunction getKeywordsFormItemIfFunction(final SiemacMetadataStatisticalResourceDto resource) {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return SiemacMetadataEditionChecks.canKeywordsBeEdited(resource.getProcStatus());
            }
        };
    }
    private FormItemIfFunction getStaticKeywordsFormItemIfFunction(final SiemacMetadataStatisticalResourceDto resource) {
        return new FormItemIfFunction() {
            
            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return !SiemacMetadataEditionChecks.canKeywordsBeEdited(resource.getProcStatus());
            }
        };
    }
}
