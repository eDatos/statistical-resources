package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.NameableResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;

public class NameableResourceIdentifiersForm extends IdentifiableResourceIdentifiersForm {

    public NameableResourceIdentifiersForm() {

        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(NameableResourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());

        addFields(title);
    }

    public void setNameableStatisticalResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        setIdentifiableStatisticalResourceDto(nameableStatisticalResourceDto);
        setValue(NameableResourceDS.TITLE, nameableStatisticalResourceDto.getTitle());
    }
}
