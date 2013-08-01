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

    public static boolean canKeywordsBeEdited(ProcStatusEnum procStatus) {
        return true;
    }

    public static boolean canNextVersionDateBeEdited(NextVersionTypeEnum nextVersion) {
        if (NextVersionTypeEnum.SCHEDULED_UPDATE.equals(nextVersion)) {
            return true;
        }
        return false;
    }

    public static boolean canVersionRationaleTypesBeEdited(String versionLogic) {
        if (StatisticalResourcesVersionUtils.INITIAL_VERSION.equals(versionLogic)) {
            return false;
        }
        return true;
    }
}
