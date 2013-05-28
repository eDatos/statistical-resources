package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class QueryProductionDescriptorsForm extends GroupDynamicForm {

    //FIXME
    public QueryProductionDescriptorsForm() {
        super(getConstants().formProductionDescriptors());

        ViewTextItem datasetVersion = new ViewTextItem(QueryDS.DATASET_VERSION_URN, getConstants().queryDatasetVersion());

        setFields(datasetVersion);
    }

    public void setQueryDto(QueryDto queryDto) {
        setValue(QueryDS.DATASET_VERSION_URN, queryDto.getDatasetVersion());
    }
}
