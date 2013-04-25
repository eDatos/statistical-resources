package org.siemac.metamac.statistical.resources.core.dataset.checks;

import org.siemac.metamac.statistical.resources.core.base.checks.SiemacMetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;


public class DatasetMetadataEditionChecks extends SiemacMetadataEditionChecks {

    public static boolean canDsdBeEdited(ProcStatusEnum procStatus) {
        if (ProcStatusEnum.DRAFT.equals(procStatus) ||
                ProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
            return true;
        }
        return false;
    }

    public static boolean canDateNextUpdateBeEdited() {
        //FIXME: When can his be edited?, only when there is no px in
        return false;
    }
}
