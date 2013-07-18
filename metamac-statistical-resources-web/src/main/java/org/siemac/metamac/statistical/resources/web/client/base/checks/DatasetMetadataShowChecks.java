package org.siemac.metamac.statistical.resources.web.client.base.checks;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class DatasetMetadataShowChecks {

    public static boolean canCoveragesBeShown(Object procStatusObj) {
        if (procStatusObj != null) {
            try {
                ProcStatusEnum procStatus = ProcStatusEnum.valueOf(procStatusObj.toString());
                if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
                    return true;
                }
            } catch (Exception e) {
                // Should never happen
            }
        }
        return false;
    }

}
