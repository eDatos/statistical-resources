package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;

public class StatisticalResourceThematicContentClassifiersEditionForm extends GroupDynamicForm {

    protected String statisticalOperationCode;

    public StatisticalResourceThematicContentClassifiersEditionForm() {
        super(getConstants().formThematicContentClassifiers());

        ExternalItemLinkItem statisticalOperation = new ExternalItemLinkItem(SiemacMetadataDS.STATISTICAL_OPERATION, getConstants().siemacMetadataStatisticalResourceStatisticalOperation());

        setFields(statisticalOperation);
    }

    public void setStatisticalResourceDto(StatisticalResourceDto dto) {
        statisticalOperationCode = dto.getStatisticalOperation().getCode();
        setExternalItemValue(getItem(SiemacMetadataDS.STATISTICAL_OPERATION), dto.getStatisticalOperation());
    }

    public StatisticalResourceDto getStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        return dto;
    }
}
