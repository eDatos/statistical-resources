package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedDatasetsSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;

// TODO: Add real rules not only "true"
public class DatasetClientSecurityUtils {

    // TODO Remove these attributes!!!
    private static StatisticalResourceProcStatusEnum procStatus = StatisticalResourceProcStatusEnum.DRAFT;
    private static StatisticalResourceTypeEnum       type       = StatisticalResourceTypeEnum.DATASET;
    private static String                            operationCode;

    public static boolean canCreateDataset() {
        return true;
    }

    public static boolean canUpdateDataset() {
        return true;
    }

    public static boolean canDeleteDataset() {
        return true;
    }

    public static boolean canSendDatasetToProductionValidation() {
        return true;
    }

    public static boolean canSendDatasetToDiffusionValidation() {
        return true;
    }

    public static boolean canRejectDatasetValidation() {
        return true;
    }

    public static boolean canVersioningDataset() {
        return true;
    }

}
