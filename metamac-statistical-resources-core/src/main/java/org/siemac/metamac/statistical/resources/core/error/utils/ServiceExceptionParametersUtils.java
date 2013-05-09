package org.siemac.metamac.statistical.resources.core.error.utils;


public class ServiceExceptionParametersUtils {

    public static String addParameter(String actualParameter, String newParameter) {
        return actualParameter.concat(".").concat(newParameter);
    }
}
