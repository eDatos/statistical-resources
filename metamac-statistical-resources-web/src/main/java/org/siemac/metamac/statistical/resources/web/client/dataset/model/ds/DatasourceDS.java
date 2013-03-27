package org.siemac.metamac.statistical.resources.web.client.dataset.model.ds;

import com.smartgwt.client.data.DataSource;

public class DatasourceDS extends DataSource {

    public static final String ID        = "dsource-id";
    // STATISTICAL RESOURCE
    public static final String CODE      = "dsource-code";
    public static final String CODE_VIEW = "dsource-code-view";
    public static final String URI       = "dsource-uri";
    public static final String URN       = "dsource-urn";
    public static final String TITLE     = "dsource-title";

    public static String       DTO       = "dsource-dto";

    public DatasourceDS() {
        super();
    }

}
