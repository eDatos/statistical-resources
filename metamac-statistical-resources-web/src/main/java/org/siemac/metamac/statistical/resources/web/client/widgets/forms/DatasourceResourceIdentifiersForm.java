package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.IdentifiableResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasourceResourceIdentifiersForm extends GroupDynamicForm {

    public DatasourceResourceIdentifiersForm() {
        super(getConstants().formIdentifiers());

        ViewTextItem identifier = new ViewTextItem(IdentifiableResourceDS.CODE, getConstants().identifiableStatisticalResourceCode());

        setFields(identifier);
    }

    public void setDatasourceDto(DatasourceDto datasourceDto) {
        setValue(IdentifiableResourceDS.CODE, datasourceDto.getCode());
    }
}
