package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.NameableResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;

import com.smartgwt.client.widgets.form.fields.FormItem;

public class NameableResourceIdentifiersEditionForm extends IdentifiableResourceIdentifiersEditionForm {

    public NameableResourceIdentifiersEditionForm() {

        MultiLanguageTextItem title = new MultiLanguageTextItem(NameableResourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        title.setRequired(true);

        FormItem[] fields = new FormItem[getFields().length + 1];
        System.arraycopy(getFields(), 0, fields, 0, getFields().length);
        fields[fields.length - 1] = title;
        setFields(fields);
    }

    public void setNameableStatisticalResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        setIdentifiableStatisticalResourceDto(nameableStatisticalResourceDto);
        setValue(NameableResourceDS.TITLE, RecordUtils.getInternationalStringRecord(nameableStatisticalResourceDto.getTitle()));
    }

    public NameableStatisticalResourceDto getNameableStatisticalResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        nameableStatisticalResourceDto = (NameableStatisticalResourceDto) getIdentifiableStatisticalResourceDto(nameableStatisticalResourceDto);
        nameableStatisticalResourceDto.setTitle((InternationalStringDto) getValue(NameableResourceDS.TITLE));
        return nameableStatisticalResourceDto;
    }
}
