package org.siemac.metamac.statistical.resources.web.client.base.utils;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;

public class BaseClientSecurityUtils {

    protected static ProcStatusEnum[] publishedStatus = new ProcStatusEnum[]{ProcStatusEnum.PUBLISHED, ProcStatusEnum.PUBLISHED_NOT_VISIBLE};

    protected static MetamacPrincipal getMetamacPrincipal() {
        return StatisticalResourcesWeb.getCurrentUser();
    }

    protected static boolean isPublished(ProcStatusEnum procStatus) {
        return isAnyStatus(procStatus, publishedStatus);
    }

    public static boolean isDraftOrValidationRejected(ProcStatusEnum procStatus) {
        if (isAnyStatus(procStatus, ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED)) {
            return true;
        }
        return false;
    }

    protected static boolean isAnyStatus(ProcStatusEnum status, ProcStatusEnum... posibleStatus) {
        for (ProcStatusEnum posible : posibleStatus) {
            if (status.equals(posible)) {
                return true;
            }
        }
        return false;
    }
}
