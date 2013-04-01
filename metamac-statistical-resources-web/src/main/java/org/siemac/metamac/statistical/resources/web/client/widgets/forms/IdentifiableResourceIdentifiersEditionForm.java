package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.IdentifiableResourceDS;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class IdentifiableResourceIdentifiersEditionForm extends GroupDynamicForm {

    public IdentifiableResourceIdentifiersEditionForm() {
        this(getConstants().formIdentifiers());
    }

    public IdentifiableResourceIdentifiersEditionForm(String groupTitle) {
        super(groupTitle);

        RequiredSelectItem identifier = new RequiredSelectItem(IdentifiableResourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
        identifier.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        ViewTextItem uri = new ViewTextItem(IdentifiableResourceDS.URI, getConstants().identifiableStatisticalResourceURI());
        ViewTextItem urn = new ViewTextItem(IdentifiableResourceDS.URN, getConstants().identifiableStatisticalResourceURN());

        setFields(identifier, uri, urn);
    }

    public void setIdentifiableStatisticalResourceDto(IdentifiableStatisticalResourceDto identifiableStatisticalResourceDto) {
        setValue(IdentifiableResourceDS.CODE, identifiableStatisticalResourceDto.getCode());
        setValue(IdentifiableResourceDS.URI, identifiableStatisticalResourceDto.getUri());
        setValue(IdentifiableResourceDS.URN, identifiableStatisticalResourceDto.getUrn());
    }

    public IdentifiableStatisticalResourceDto getIdentifiableStatisticalResourceDto(IdentifiableStatisticalResourceDto identifiableStatisticalResourceDto) {
        identifiableStatisticalResourceDto.setCode(getValueAsString(IdentifiableResourceDS.CODE));
        return identifiableStatisticalResourceDto;
    }
}
