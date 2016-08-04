package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.IdentifiableResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class IdentifiableResourceIdentifiersEditionForm extends GroupDynamicForm {

    public IdentifiableResourceIdentifiersEditionForm() {
        super(getConstants().formIdentifiers());

        ViewTextItem identifier = new ViewTextItem(IdentifiableResourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
        ViewTextItem urn = new ViewTextItem(IdentifiableResourceDS.URN, getConstants().identifiableStatisticalResourceURN());

        setFields(identifier, urn);
    }

    public void setIdentifiableStatisticalResourceDto(IdentifiableStatisticalResourceDto identifiableStatisticalResourceDto) {
        setValue(IdentifiableResourceDS.CODE, identifiableStatisticalResourceDto.getCode());
        setValue(IdentifiableResourceDS.URN, identifiableStatisticalResourceDto.getUrn());
    }

    public IdentifiableStatisticalResourceDto getIdentifiableStatisticalResourceDto(IdentifiableStatisticalResourceDto identifiableStatisticalResourceDto) {
        return identifiableStatisticalResourceDto;
    }
}
