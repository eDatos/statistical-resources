package org.siemac.metamac.statistical_resources.rest.external;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.statistical_resources.rest.common.StatisticalResourcesRestConstants;

public class StatisticalResourcesRestExternalConstants extends StatisticalResourcesRestConstants {

    public static final ServiceContext SERVICE_CONTEXT          = new ServiceContext("restExternal", "restExternal", "restExternal");

    public static final String         PORTAL_PATH_DATASETS     = "datasets";
    public static final String         KEY_DIMENSIONS_SEPARATOR = "#";

    // TODO Latest (METAMAC-1785)cambiar el codigo que se ejecuta en cada api
    public static boolean              IS_INTERNAL_API          = false;

}
