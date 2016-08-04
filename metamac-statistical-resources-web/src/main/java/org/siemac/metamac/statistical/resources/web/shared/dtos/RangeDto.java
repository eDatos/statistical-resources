package org.siemac.metamac.statistical.resources.web.shared.dtos;

import java.io.Serializable;

public class RangeDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            fromValue;
    private String            toValue;

    public RangeDto() {
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(String toValue) {
        this.toValue = toValue;
    }
}