package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.NameableResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;

import com.smartgwt.client.widgets.form.fields.FormItem;

public class NameableResourceIdentifiersForm extends IdentifiableResourceIdentifiersForm {

    public NameableResourceIdentifiersForm() {

        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(NameableResourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());

        FormItem[] fields = new FormItem[getFields().length + 1];
        System.arraycopy(getFields(), 0, fields, 0, getFields().length);
        fields[fields.length - 1] = title;
        setFields(fields);
    }

    public void setNameableStatisticalResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        setIdentifiableStatisticalResourceDto(nameableStatisticalResourceDto);
        setValue(NameableResourceDS.TITLE, RecordUtils.getInternationalStringRecord(nameableStatisticalResourceDto.getTitle()));
    }
}
