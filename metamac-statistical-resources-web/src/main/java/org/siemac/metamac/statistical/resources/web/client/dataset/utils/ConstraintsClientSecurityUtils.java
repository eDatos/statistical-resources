package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedConstraintsSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.base.utils.BaseClientSecurityUtils;

public class ConstraintsClientSecurityUtils extends BaseClientSecurityUtils {

    public static boolean canFindContentConstraintsForArtefact() {
        return SharedConstraintsSecurityUtils.canFindContentConstraintsForArtefact(getMetamacPrincipal());
    }

    public static boolean canCreateContentConstraint(DatasetVersionDto datasetVersionDto) {
        if (BooleanUtils.isTrue(datasetVersionDto.getIsTaskInBackground())) {
            return false;
        }
        return SharedConstraintsSecurityUtils.canCreateContentConstraint(getMetamacPrincipal(), datasetVersionDto.getStatisticalOperation().getCode(), datasetVersionDto.getProcStatus());
    }

    public static boolean canDeleteContentConstraint(DatasetVersionDto datasetVersionDto) {
        if (BooleanUtils.isTrue(datasetVersionDto.getIsTaskInBackground())) {
            return false;
        }
        return SharedConstraintsSecurityUtils.canDeleteContentConstraint(getMetamacPrincipal(), datasetVersionDto.getStatisticalOperation().getCode(), datasetVersionDto.getProcStatus());
    }

    public static boolean canSaveForContentConstraint(DatasetVersionDto datasetVersionDto) {
        if (BooleanUtils.isTrue(datasetVersionDto.getIsTaskInBackground())) {
            return false;
        }
        return SharedConstraintsSecurityUtils.canSaveForContentConstraint(getMetamacPrincipal(), datasetVersionDto.getStatisticalOperation().getCode(), datasetVersionDto.getProcStatus());
    }
}
