package org.siemac.metamac.statistical.resources.core.dataset.checks;

import org.siemac.metamac.statistical.resources.core.base.checks.MetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesVersionSharedUtils;


public class DatasetMetadataEditionChecks extends MetadataEditionChecks {

    public static boolean canDsdBeEdited(Long datasetId, ProcStatusEnum procStatus) {
        if (datasetId == null) {
            return true;
        } else {
            if (ProcStatusEnum.DRAFT.equals(procStatus) ||
                    ProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                return true;
            }
            return false;
        }
    }
    
    public static boolean canDsdBeReplacedByAnyOtherDsd(Long datasetId, String datasetVersionLogic, ProcStatusEnum procStatus) {
        if (canDsdBeEdited(datasetId, procStatus)) {
            return StatisticalResourcesVersionSharedUtils.isInitialVersion(datasetVersionLogic);
        }
        return false;
    }

    public static boolean canDateNextUpdateBeEdited() {
        //FIXME: When can his be edited?, only when there is no px in
        return false;
    }


}
