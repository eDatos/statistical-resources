package org.siemac.metamac.statistical.resources.web.client.base.checks;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class DatasetMetadataShowChecks {

    public static boolean canCoveragesBeShown(ProcStatusEnum procStatus) {
        if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
            return true;
        }
        return false;
    }
}
