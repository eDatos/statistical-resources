package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.shared.utils.RelatedResourceUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class QueryProductionDescriptorsForm extends GroupDynamicForm {

    public QueryProductionDescriptorsForm() {
        super(getConstants().formProductionDescriptors());

        ViewTextItem datasetVersion = new ViewTextItem(QueryDS.RELATED_DATASET_VERSION, getConstants().queryDatasetVersion());

        setFields(datasetVersion);
    }

    public void setQueryDto(QueryVersionDto queryDto) {
        setValue(QueryDS.RELATED_DATASET_VERSION, RelatedResourceUtils.getRelatedResourceName(queryDto.getRelatedDatasetVersion()));
    }
}
