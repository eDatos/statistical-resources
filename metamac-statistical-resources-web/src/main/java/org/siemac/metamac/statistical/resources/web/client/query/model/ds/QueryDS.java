package org.siemac.metamac.statistical.resources.web.client.query.model.ds;

import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;

public class QueryDS extends LifeCycleResourceDS {

    public static final String ID                  = "query-id";
    public static final String TYPE                = "query-type";
    public static final String DTO                 = "query-dto";

    public static String       DATASET_VERSION_URN = "query-dataset-version-urn";

    public QueryDS() {
        super();
    }

}
