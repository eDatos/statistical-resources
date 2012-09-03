package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedDatasetsSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;

public class DatasetClientSecurityUtils {

    // TODO Remove these attributes!!!
    private static StatisticalResourceProcStatusEnum procStatus = StatisticalResourceProcStatusEnum.DRAFT;
    private static StatisticalResourceTypeEnum       type       = StatisticalResourceTypeEnum.DATASET;
    private static String                            operationCode;

    // Schemes

    public static boolean canCreateDataset() {
        return SharedDatasetsSecurityUtils.canCreateDataset(StatisticalResourcesWeb.getCurrentUser());
    }

    public static boolean canUpdateDataset() {
        return SharedDatasetsSecurityUtils.canUpdateDataset(StatisticalResourcesWeb.getCurrentUser(), procStatus, type, operationCode);
    }

    public static boolean canDeleteDataset() {
        return SharedDatasetsSecurityUtils.canDeleteDataset(StatisticalResourcesWeb.getCurrentUser(), type, operationCode);
    }

    public static boolean canSendDatasetToProductionValidation() {
        return SharedDatasetsSecurityUtils.canSendDatasetToProductionValidation(StatisticalResourcesWeb.getCurrentUser(), type, operationCode);
    }

    public static boolean canSendDatasetToDiffusionValidation() {
        return SharedDatasetsSecurityUtils.canSendDatasetToDiffusionValidation(StatisticalResourcesWeb.getCurrentUser(), type, operationCode);
    }

    public static boolean canRejectDatasetValidation() {
        return SharedDatasetsSecurityUtils.canRejectDatasetValidation(StatisticalResourcesWeb.getCurrentUser(), procStatus, type, operationCode);
    }


    public static boolean canVersioningDataset() {
        return SharedDatasetsSecurityUtils.canVersioningDataset(StatisticalResourcesWeb.getCurrentUser(), type, operationCode);
    }

}
