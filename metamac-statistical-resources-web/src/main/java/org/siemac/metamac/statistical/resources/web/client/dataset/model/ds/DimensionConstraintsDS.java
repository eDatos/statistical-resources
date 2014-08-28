package org.siemac.metamac.statistical.resources.web.client.dataset.model.ds;

import com.smartgwt.client.data.DataSource;

public class DimensionConstraintsDS extends DataSource {

    public static final String DIMENSION_ID      = "dim-cons-id";
    public static final String INCLUSION_TYPE    = "dim-cons-in-type";
    public static final String VALUES            = "dim-cons-values";
    public static final String DSD_DIMENSION_DTO = "dim-cons-dto";

    public DimensionConstraintsDS() {
        super();
    }
}