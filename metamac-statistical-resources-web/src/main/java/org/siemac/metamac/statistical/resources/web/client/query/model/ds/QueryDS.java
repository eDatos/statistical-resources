package org.siemac.metamac.statistical.resources.web.client.query.model.ds;

import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;

public class QueryDS extends LifeCycleResourceDS {

    public static final String ID                      = "query-id";
    public static final String TYPE                    = "query-type";
    public static final String STATUS                  = "query-status";

    public static String       RELATED_DATASET_VERSION = "query-dataset-version";
    public static String       SELECTION               = "query-dataset-version";
    public static String       LATEST_N_DATA           = "query-latest-n-data";

    public QueryDS() {
        super();
    }
}
