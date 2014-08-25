package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedConstraintsSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.base.utils.BaseClientSecurityUtils;

public class ConstraintsClientSecurityUtils extends BaseClientSecurityUtils {

    public static boolean canFindContentConstraintsForArtefact() {
        return SharedConstraintsSecurityUtils.canFindContentConstraintsForArtefact(getMetamacPrincipal());
    }

    public static boolean canCreateContentConstraint(String operationCode) {
        return SharedConstraintsSecurityUtils.canCreateContentConstraint(getMetamacPrincipal(), operationCode);
    }

    public static boolean canDeleteContentConstraint(String operationCode, ProcStatusEnum procStatus) {
        return SharedConstraintsSecurityUtils.canDeleteContentConstraint(getMetamacPrincipal(), operationCode, procStatus);
    }

    public static boolean canSaveForContentConstraint(String operationCode, ProcStatusEnum procStatus) {
        return SharedConstraintsSecurityUtils.canSaveForContentConstraint(getMetamacPrincipal(), operationCode, procStatus);
    }
}
