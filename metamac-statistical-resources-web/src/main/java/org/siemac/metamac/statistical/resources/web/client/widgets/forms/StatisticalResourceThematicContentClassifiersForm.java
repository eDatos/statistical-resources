package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class StatisticalResourceThematicContentClassifiersForm extends GroupDynamicForm {

    public StatisticalResourceThematicContentClassifiersForm() {
        super(getConstants().formThematicContentClassifiers());

        ViewTextItem statisticalOperation = new ViewTextItem(StatisticalResourceDS.STATISTICAL_OPERATION, getConstants().siemacMetadataStatisticalResourceStatisticalOperation());
        // TODO instance or instances?

        setFields(statisticalOperation);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(StatisticalResourceDS.STATISTICAL_OPERATION, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getStatisticalOperation()));
        // TODO instance or instances?
    }
}
