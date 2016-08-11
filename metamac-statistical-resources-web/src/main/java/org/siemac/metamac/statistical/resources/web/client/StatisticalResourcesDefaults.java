package org.siemac.metamac.statistical.resources.web.client;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.DtoCopyUtils;

public class StatisticalResourcesDefaults {

    public static ExternalItemDto  defaultAgency;
    public static ExternalItemDto  defaultLanguage;

    private static ExternalItemDto selectedStatisticalOperation;

    public static ExternalItemDto getSelectedStatisticalOperation() {
        // Return a copy of the original ExternalItem, to preserve the original values of the ExternalItem
        return DtoCopyUtils.copy(selectedStatisticalOperation);
    }

    public static String getSelectedStatisticalOperationCode() {
        return selectedStatisticalOperation != null ? selectedStatisticalOperation.getCode() : null;
    }

    public static void setSelectedStatisticalOperation(ExternalItemDto selectedStatisticalOperation) {
        StatisticalResourcesDefaults.selectedStatisticalOperation = selectedStatisticalOperation;
    }
}
