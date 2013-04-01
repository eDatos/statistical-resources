package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.IdentifiableResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class IdentifiableResourceIdentifiersForm extends GroupDynamicForm {

    public IdentifiableResourceIdentifiersForm() {
        this(getConstants().formIdentifiers());
    }

    public IdentifiableResourceIdentifiersForm(String groupTitle) {
        super(groupTitle);

        ViewTextItem identifier = new ViewTextItem(IdentifiableResourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
        ViewTextItem uri = new ViewTextItem(IdentifiableResourceDS.URI, getConstants().identifiableStatisticalResourceURI());
        ViewTextItem urn = new ViewTextItem(IdentifiableResourceDS.URN, getConstants().identifiableStatisticalResourceURN());

        setFields(identifier, uri, urn);
    }

    public void setIdentifiableResource(IdentifiableStatisticalResourceDto identifiableStatisticalResourceDto) {
        setValue(IdentifiableResourceDS.CODE, identifiableStatisticalResourceDto.getCode());
        setValue(IdentifiableResourceDS.URI, identifiableStatisticalResourceDto.getUri());
        setValue(IdentifiableResourceDS.URN, identifiableStatisticalResourceDto.getUrn());
    }
}
