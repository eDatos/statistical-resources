package org.siemac.metamac.statistical_resources.rest.external;

import org.siemac.metamac.rest.api.constants.RestApiConstants;

public class RestExternalConstants extends RestApiConstants {

    public static String API_NAME              = "statisticalResources";
    public static String API_VERSION_1_0       = "v1.0";

    public static String PARAMETER_AGENCY_ID   = "agencyID";
    public static String PARAMETER_RESOURCE_ID = "resourceID";
    public static String PARAMETER_VERSION     = "version";

    public static String KIND_DATASETS         = API_NAME + KIND_SEPARATOR + "datasets";
    public static String KIND_DATASET          = API_NAME + KIND_SEPARATOR + "dataset";

    public static String LINK_SUBPATH_DATASETS= "datasets";
}
