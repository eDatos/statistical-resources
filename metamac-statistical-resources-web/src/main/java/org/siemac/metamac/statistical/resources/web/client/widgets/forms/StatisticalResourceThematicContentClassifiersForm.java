package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;

import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;

public class StatisticalResourceThematicContentClassifiersForm extends GroupDynamicForm {

    public StatisticalResourceThematicContentClassifiersForm() {

        super(getConstants().formThematicContentClassifiers());

        ExternalItemLinkItem statisticalOperation = new ExternalItemLinkItem(StatisticalResourceDS.STATISTICAL_OPERATION, getConstants().siemacMetadataStatisticalResourceStatisticalOperation());

        setFields(statisticalOperation);
    }

    public void setStatisticalResourceDto(StatisticalResourceDto dto) {
        setExternalItemValue(getItem(StatisticalResourceDS.STATISTICAL_OPERATION), dto.getStatisticalOperation());
    }
}
