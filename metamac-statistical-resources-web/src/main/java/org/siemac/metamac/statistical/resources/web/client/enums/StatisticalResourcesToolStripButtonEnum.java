package org.siemac.metamac.statistical.resources.web.client.enums;

import com.smartgwt.client.types.ValueEnum;

public enum StatisticalResourcesToolStripButtonEnum implements ValueEnum {

    DATASETS("datasets_button"), COLLECTIONS("collections_button"), QUERIES("queries_button");

    private String value;

    StatisticalResourcesToolStripButtonEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

}
