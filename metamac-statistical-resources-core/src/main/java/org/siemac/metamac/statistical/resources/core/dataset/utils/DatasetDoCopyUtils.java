package org.siemac.metamac.statistical.resources.core.dataset.utils;

import static org.siemac.metamac.statistical.resources.core.base.utils.BaseDoCopyUtils.copySiemacMetadataStatisticalResource;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public class DatasetDoCopyUtils {

    /**
     * Create a new {@link DatasetVersion} copying values from a source.
     */
    public static DatasetVersion copyDatasetVersion(DatasetVersion source) {
        DatasetVersion target = new DatasetVersion();
        copyDatasetVersion(source, target);
        return target;
    }

    /**
     * Copy values from a {@link DatasetVersion}
     */
    public static void copyDatasetVersion(DatasetVersion source, DatasetVersion target) {
        copySiemacMetadataStatisticalResource(source.getSiemacMetadataStatisticalResource(), target.getSiemacMetadataStatisticalResource());
//        copyDataset(source.getSiemacMetadataStatisticalResource(), target.getSiemacMetadataStatisticalResource());
//        copyDatasources(source, target);
    }

}