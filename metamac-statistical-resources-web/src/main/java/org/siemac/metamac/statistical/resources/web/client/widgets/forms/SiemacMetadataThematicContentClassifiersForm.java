package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemsValue;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;

public class SiemacMetadataThematicContentClassifiersForm extends GroupDynamicForm {

    public SiemacMetadataThematicContentClassifiersForm() {
        super(getConstants().formThematicContentClassifiers());

        ExternalItemLinkItem statisticalOperation = new ExternalItemLinkItem(StatisticalResourceDS.STATISTICAL_OPERATION, getConstants().siemacMetadataStatisticalResourceStatisticalOperation());
        ExternalItemListItem instances = new ExternalItemListItem(StatisticalResourceDS.STATISTICAL_OPERATION_INSTANCE, getConstants().siemacMetadataStatisticalResourceStatisticalOperationInstance(),
                false);

        setFields(statisticalOperation, instances);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setExternalItemValue(getItem(StatisticalResourceDS.STATISTICAL_OPERATION), dto.getStatisticalOperation());
        setExternalItemsValue(getItem(StatisticalResourceDS.STATISTICAL_OPERATION_INSTANCE), dto.getStatisticalOperationInstances());
    }
}
