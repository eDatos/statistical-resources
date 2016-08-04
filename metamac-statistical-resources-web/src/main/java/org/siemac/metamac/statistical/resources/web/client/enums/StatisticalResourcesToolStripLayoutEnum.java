package org.siemac.metamac.statistical.resources.web.client.enums;

import com.smartgwt.client.types.ValueEnum;

public enum StatisticalResourcesToolStripLayoutEnum implements ValueEnum {

    STATISTIC_DESKTOP("statistic_desktop"), OPERATION_RESOURCES("operation_resources");

    private String value;

    StatisticalResourcesToolStripLayoutEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
