package org.siemac.metamac.statistical.resources.core.dataset.utils;

import static org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants.DATASET_REPOSITORY_TABLE_NAME_PREFIX;
import static org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants.DATASET_REPOSITORY_TABLE_NAME_SEPARATOR;

import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;

public class DatasetVersionUtils {

    public static String generateDatasetRepositoryTableName(SiemacMetadataStatisticalResource datasetVersion) {
        StringBuilder tableName = new StringBuilder();
        tableName.append(DATASET_REPOSITORY_TABLE_NAME_PREFIX);
        tableName.append(datasetVersion.getCode());
        tableName.append(DATASET_REPOSITORY_TABLE_NAME_SEPARATOR);
        tableName.append(VersionUtil.getVersionWithoutDot(datasetVersion.getVersionLogic()));
        return tableName.toString();
    }

}
