package org.siemac.metamac.statistical.resources.web.client.base.utils;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;

public class BaseClientSecurityUtils {

    protected static MetamacPrincipal getMetamacPrincipal() {
        return StatisticalResourcesWeb.getCurrentUser();
    }
}
