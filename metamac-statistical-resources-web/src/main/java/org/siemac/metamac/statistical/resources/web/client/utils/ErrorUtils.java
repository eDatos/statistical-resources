package org.siemac.metamac.statistical.resources.web.client.utils;

import java.util.List;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.web.common.client.utils.CommonErrorUtils;

public class ErrorUtils extends CommonErrorUtils {

    public static List<String> getErrorMessages(Throwable caught, String alternativeMessage) {
        return getErrorMessages(StatisticalResourcesWeb.getCoreMessages(), caught, alternativeMessage);
    }
}
