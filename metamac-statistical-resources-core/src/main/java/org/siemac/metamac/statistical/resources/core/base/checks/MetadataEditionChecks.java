package org.siemac.metamac.statistical.resources.core.base.checks;

import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;

public class MetadataEditionChecks {

    public static boolean canStatisticalOperationBeEdited(Long id) {
        if (id == null) {
            return true;
        }
        return false;
    }

    public static boolean canMaintainerBeEdited(Long id) {
        if (id == null) {
            return true;
        }
        return false;
    }

    public static boolean canLanguageBeEdited(Long id) {
        if (id == null) {
            return true;
        }
        return false;
    }

    public static boolean canDateNextUpdateBeEdited(NextVersionTypeEnum nextVersion) {
        return canNextDatesBeFilled(nextVersion);
    }

    public static boolean canNextVersionDateBeEdited(NextVersionTypeEnum nextVersion) {
        return canNextDatesBeFilled(nextVersion);
    }

    private static boolean canNextDatesBeFilled(NextVersionTypeEnum nextVersion) {
        if (NextVersionTypeEnum.SCHEDULED_UPDATE.equals(nextVersion)) {
            return true;
        }
        return false;
    }

    public static boolean canVersionRationaleTypesBeEdited(String versionLogic) {
        if (StatisticalResourcesVersionUtils.getInitialVersion().equals(versionLogic)) {
            return false;
        }
        return true;
    }

    public static boolean isDraftOrValidationRejected(ProcStatusEnum procStatus) {
        if (isAnyStatus(procStatus, ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED)) {
            return true;
        }
        return false;
    }

    public static boolean isAnyStatus(ProcStatusEnum status, ProcStatusEnum... posibleStatus) {
        for (ProcStatusEnum posible : posibleStatus) {
            if (status.equals(posible)) {
                return true;
            }
        }
        return false;
    }
}
