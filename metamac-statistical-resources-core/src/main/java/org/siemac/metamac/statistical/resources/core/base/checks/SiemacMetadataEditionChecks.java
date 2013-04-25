package org.siemac.metamac.statistical.resources.core.base.checks;

import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;


public class SiemacMetadataEditionChecks {

    public static boolean canStatisticalOperationBeEdited(Long id) {
        if (id == null) {
            return true;
        }
        return false;
    }
    
    public static boolean canKeywordsBeEdited(ProcStatusEnum procStatus) {
        if (ProcStatusEnum.DRAFT.equals(procStatus)) {
            return false;
        }
        return true;
    }

    public static boolean canNextVersionDateBeEdited(NextVersionTypeEnum nextVersion) {
        if (NextVersionTypeEnum.SCHEDULED_UPDATE.equals(nextVersion)) {
            return true;
        }
        return false;
    }
}
