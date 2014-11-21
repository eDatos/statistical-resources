package org.siemac.metamac.statistical.resources.core.dataset.utils;

import static org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants.DATASET_REPOSITORY_TABLE_NAME_PREFIX;
import static org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants.DATASET_REPOSITORY_TABLE_NAME_SEPARATOR;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.time.TimeSdmx;
import org.siemac.metamac.core.common.util.TimeSdmxComparator;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;

public class DatasetVersionUtils {

    private static final String DIMENSION_REPRESENTATION_SEPARATOR_CHARACTER = "#";
    private static final String COMMA                                        = ",";

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

    public static String dimensionRepresentationMapToString(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey()).append(DIMENSION_REPRESENTATION_SEPARATOR_CHARACTER).append(entry.getValue()).append(COMMA);
        }
        return StringUtils.removeEnd(builder.toString(), COMMA);
    }

    public static Map<String, String> dimensionRepresentationMapFromString(String mapping) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(mapping)) {
            String[] pairs = StringUtils.split(mapping, COMMA);
            for (String pair : pairs) {
                String[] splitItem = pair.split(DIMENSION_REPRESENTATION_SEPARATOR_CHARACTER);
                if (splitItem != null && splitItem.length == 2) {
                    map.put(splitItem[0].trim(), splitItem[1].trim());
                }
            }
        }
        return map;
    }
}
