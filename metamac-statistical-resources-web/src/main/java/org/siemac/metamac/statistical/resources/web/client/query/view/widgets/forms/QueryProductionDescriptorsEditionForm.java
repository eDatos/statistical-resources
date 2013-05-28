package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

public class QueryProductionDescriptorsEditionForm extends GroupDynamicForm {

    //FIXME add selection
    public QueryProductionDescriptorsEditionForm() {
        super(getConstants().formProductionDescriptors());

        RequiredTextItem datasetVersion = new RequiredTextItem(QueryDS.DATASET_VERSION_URN, getConstants().queryDatasetVersion());

        setFields(datasetVersion);
    }

    public void setQueryDto(QueryDto queryDto) {
        setValue(QueryDS.DATASET_VERSION_URN, queryDto.getDatasetVersion());
    }
    
    public QueryDto populateQueryDto(QueryDto queryDto) {
        queryDto.setDatasetVersion(getValueAsString(QueryDS.DATASET_VERSION_URN));
        Map<String, Set<String>> selection = new HashMap<String, Set<String>>();
        selection.put("SEX", new HashSet<String>(Arrays.asList("code1")));
        queryDto.setSelection(selection);
        queryDto.setType(QueryTypeEnum.FIXED);
        return queryDto;
    }
}
