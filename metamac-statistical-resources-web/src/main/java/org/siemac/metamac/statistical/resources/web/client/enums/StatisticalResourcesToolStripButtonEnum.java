package org.siemac.metamac.statistical.resources.web.client.enums;

import com.smartgwt.client.types.ValueEnum;

public enum StatisticalResourcesToolStripButtonEnum implements ValueEnum {

    DATASETS("datasets_button"), PUBLICATIONS("publications_button"), QUERIES("queries_button"), MULTIDATASETS("multidatasets_button");

    private String value;

    StatisticalResourcesToolStripButtonEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
