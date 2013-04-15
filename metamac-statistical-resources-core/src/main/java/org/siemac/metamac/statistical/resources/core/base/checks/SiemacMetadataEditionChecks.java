package org.siemac.metamac.statistical.resources.core.base.checks;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;


public class SiemacMetadataEditionChecks {

    public static boolean canStatisticalOperationBeEdited(Long id) {
        if (id == null) {
            return true;
        }
        return false;
    }
    
    public static boolean canKeywordsBeEdited(StatisticalResourceProcStatusEnum procStatus) {
        if (StatisticalResourceProcStatusEnum.DRAFT.equals(procStatus)) {
            return false;
        }
        return true;
    }

    public static boolean canNextVersionDateBeEdited(StatisticalResourceNextVersionEnum nextVersion) {
        if (StatisticalResourceNextVersionEnum.SCHEDULED_UPDATE.equals(nextVersion)) {
            return true;
        }
        return false;
    }
}
