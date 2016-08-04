package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.IdentifiableResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

public class DatasourceResourceIdentifiersEditionForm extends GroupDynamicForm {

    public DatasourceResourceIdentifiersEditionForm() {
        super(getConstants().formIdentifiers());

        RequiredTextItem identifier = new RequiredTextItem(IdentifiableResourceDS.CODE, getConstants().identifiableStatisticalResourceCode());

        setFields(identifier);
    }

    public void setDatasourceDto(DatasourceDto datasourceDto) {
        setValue(IdentifiableResourceDS.CODE, datasourceDto.getCode());
    }

    public DatasourceDto getDatasourceDto(DatasourceDto datasourceDto) {
        datasourceDto.setCode(getValueAsString(IdentifiableResourceDS.CODE));
        return datasourceDto;
    }
}
