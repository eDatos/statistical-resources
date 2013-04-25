package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class StatisticalResourceLanguageForm extends GroupDynamicForm {

    public StatisticalResourceLanguageForm() {
        super(getConstants().formLanguages());

        ViewTextItem language = new ViewTextItem(StatisticalResourceDS.LANGUAGE, getConstants().siemacMetadataStatisticalResourceLanguage());
        ExternalItemListItem languages = new ExternalItemListItem(StatisticalResourceDS.LANGUAGES, getConstants().siemacMetadataStatisticalResourceLanguages(), false);

        setFields(language, languages);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(StatisticalResourceDS.LANGUAGE, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getLanguage()));
        ((ExternalItemListItem) getItem(StatisticalResourceDS.LANGUAGES)).setExternalItems(siemacMetadataStatisticalResourceDto.getLanguages());
    }
}