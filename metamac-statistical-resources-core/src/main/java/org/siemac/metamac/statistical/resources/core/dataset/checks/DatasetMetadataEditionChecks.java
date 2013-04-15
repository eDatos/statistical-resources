package org.siemac.metamac.statistical.resources.core.dataset.checks;

import org.siemac.metamac.statistical.resources.core.base.checks.SiemacMetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;


public class DatasetMetadataEditionChecks extends SiemacMetadataEditionChecks {

    public static boolean canDsdBeEdited(StatisticalResourceProcStatusEnum procStatus) {
        if (StatisticalResourceProcStatusEnum.DRAFT.equals(procStatus) ||
                StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
            return true;
        }
        return false;
    }

    public static boolean canDateNextUpdateBeEdited() {
        //FIXME: When can his be edited?, only when there is no px in
        return false;
    }
}
