package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.NameableResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;

public class NameableResourceIdentifiersEditionForm extends IdentifiableResourceIdentifiersEditionForm {

    public NameableResourceIdentifiersEditionForm() {

        MultiLanguageTextItem title = new MultiLanguageTextItem(NameableResourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        title.setRequired(true);

        addFields(title);
    }

    public void setNameableStatisticalResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        setIdentifiableStatisticalResourceDto(nameableStatisticalResourceDto);
        setValue(NameableResourceDS.TITLE, nameableStatisticalResourceDto.getTitle());
    }

    public NameableStatisticalResourceDto getNameableStatisticalResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        nameableStatisticalResourceDto = (NameableStatisticalResourceDto) getIdentifiableStatisticalResourceDto(nameableStatisticalResourceDto);
        nameableStatisticalResourceDto.setTitle(getValueAsInternationalStringDto(NameableResourceDS.TITLE));
        return nameableStatisticalResourceDto;
    }
}
