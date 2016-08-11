package org.siemac.metamac.statistical.resources.core.dataset.checks;

import org.siemac.metamac.statistical.resources.core.base.checks.MetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesVersionSharedUtils;

public class DatasetMetadataEditionChecks extends MetadataEditionChecks {

    public static boolean canDsdBeEdited(Long datasetId, ProcStatusEnum procStatus) {
        if (datasetId == null) {
            return true;
        } else {
            return isDraftOrValidationRejected(procStatus);
        }
    }

    public static boolean canDsdBeReplacedByAnyOtherDsd(Long datasetId, String datasetVersionLogic, ProcStatusEnum procStatus) {
        if (canDsdBeEdited(datasetId, procStatus)) {
            return StatisticalResourcesVersionSharedUtils.isInitialVersion(datasetVersionLogic);
        }
        return false;
    }

    public static boolean canAlterDatasources(ProcStatusEnum procStatus) {
        return isDraftOrValidationRejected(procStatus);
    }

}
