package org.siemac.metamac.statistical.resources.core.dataset.utils;

import static org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils.copyIdentifiableStatisticalResource;
import static org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils.copySiemacMetadataStatisticalResource;
import static org.siemac.metamac.statistical.resources.core.common.utils.CommonVersioningCopyUtils.copyExternalItem;
import static org.siemac.metamac.statistical.resources.core.common.utils.CommonVersioningCopyUtils.copyListExternalItem;

import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;

public class DatasetVersioningCopyUtils {

    /**
     * Create a new {@link DatasetVersion} copying values from a source.
     */
    public static DatasetVersion copyDatasetVersion(DatasetVersion source) {
        DatasetVersion target = new DatasetVersion();
        target.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        copyDatasetVersion(source, target);
        return target;
    }

    /**
     * Copy values from a {@link DatasetVersion}
     */
    public static void copyDatasetVersion(DatasetVersion source, DatasetVersion target) {
        target.setSiemacMetadataStatisticalResource(copySiemacMetadataStatisticalResource(source.getSiemacMetadataStatisticalResource(), target.getSiemacMetadataStatisticalResource()));
               
        target.getGeographicGranularities().clear();
        target.getGeographicGranularities().addAll(copyListExternalItem(source.getGeographicGranularities()));
        
        target.getTemporalGranularities().clear();
        target.getTemporalGranularities().addAll(copyListExternalItem(source.getTemporalGranularities()));
        
        target.getStatisticalUnit().clear();
        target.getStatisticalUnit().addAll(copyListExternalItem(source.getStatisticalUnit()));
        
        target.setRelatedDsd(copyExternalItem(source.getRelatedDsd()));
        
        target.setUpdateFrequency(copyExternalItem(source.getUpdateFrequency()));
        target.setStatisticOfficiality(source.getStatisticOfficiality());
        
        target.setDataset(source.getDataset());
        
        target.getDatasources().clear();
        for (Datasource datasource : source.getDatasources()) {
            Datasource newDatasource = new Datasource();
            copyDatasource(datasource, newDatasource);
            target.addDatasource(newDatasource);
        }
    }
    
    private static void copyDatasource(Datasource source, Datasource target) {
        target.setIdentifiableStatisticalResource(copyIdentifiableStatisticalResource(source.getIdentifiableStatisticalResource(), target.getIdentifiableStatisticalResource()));
    }

}