package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.IdentifiableResourceDS;
import org.siemac.metamac.statistical.resources.web.client.model.ds.NameableResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.SemanticIdentifiersUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

public class QueryIdentifiersCreationForm extends GroupDynamicForm {

    public QueryIdentifiersCreationForm() {
        super(getConstants().formIdentifiers());

        RequiredTextItem identifier = new RequiredTextItem(IdentifiableResourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
        identifier.setValidators(SemanticIdentifiersUtils.getQueryIdentifierCustomValidator());

        MultiLanguageTextItem title = new MultiLanguageTextItem(NameableResourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        title.setRequired(true);

        setFields(identifier, title);
    }

    public void setNameableStatisticalResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        setValue(IdentifiableResourceDS.CODE, nameableStatisticalResourceDto.getCode());
        setValue(NameableResourceDS.TITLE, nameableStatisticalResourceDto.getTitle());
    }

    public NameableStatisticalResourceDto getNameableStatisticalResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        nameableStatisticalResourceDto.setCode(getValueAsString(IdentifiableResourceDS.CODE));
        nameableStatisticalResourceDto.setTitle(getValueAsInternationalStringDto(NameableResourceDS.TITLE));
        return nameableStatisticalResourceDto;
    }
}
