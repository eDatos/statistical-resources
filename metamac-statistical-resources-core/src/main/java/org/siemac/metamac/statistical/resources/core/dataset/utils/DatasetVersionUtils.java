package org.siemac.metamac.statistical.resources.core.dataset.utils;

import static org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants.DATASET_REPOSITORY_TABLE_NAME_PREFIX;
import static org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants.DATASET_REPOSITORY_TABLE_NAME_SEPARATOR;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.siemac.metamac.core.common.time.TimeSdmx;
import org.siemac.metamac.core.common.util.TimeSdmxComparator;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;

public class DatasetVersionUtils {

    public static String generateDatasetRepositoryTableName(SiemacMetadataStatisticalResource datasetVersion) {
        StringBuilder tableName = new StringBuilder();
        tableName.append(DATASET_REPOSITORY_TABLE_NAME_PREFIX);
        tableName.append(datasetVersion.getCode());
        tableName.append(DATASET_REPOSITORY_TABLE_NAME_SEPARATOR);
        tableName.append(VersionUtil.getVersionWithoutDot(datasetVersion.getVersionLogic()));
        return tableName.toString();
    }

    public static void sortTemporalCodeDimensions(List<CodeDimension> codes) {
        Collections.sort(codes, new Comparator<CodeDimension>() {

            private final TimeSdmxComparator sdmxComparator = new TimeSdmxComparator();

            @Override
            public int compare(CodeDimension o1, CodeDimension o2) {
                return sdmxComparator.compare(new TimeSdmx(o2.getIdentifier()), new TimeSdmx(o1.getIdentifier()));
            }
        });
    }

}
