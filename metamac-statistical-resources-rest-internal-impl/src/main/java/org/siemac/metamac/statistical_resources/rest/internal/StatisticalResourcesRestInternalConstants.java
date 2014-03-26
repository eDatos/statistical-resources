package org.siemac.metamac.statistical_resources.rest.internal;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.statistical_resources.rest.common.StatisticalResourcesRestConstants;

public class StatisticalResourcesRestInternalConstants extends StatisticalResourcesRestConstants {

    public static final ServiceContext SERVICE_CONTEXT          = new ServiceContext("restInternal", "restInternal", "restInternal");

    public static final String         PORTAL_PATH_DATASETS     = "datasets";
    public static final String         KEY_DIMENSIONS_SEPARATOR = "#";

    public static boolean              IS_INTERNAL_API          = true;

}
