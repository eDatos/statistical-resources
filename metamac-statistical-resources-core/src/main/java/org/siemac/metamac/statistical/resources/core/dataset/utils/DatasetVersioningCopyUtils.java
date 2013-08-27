package org.siemac.metamac.statistical.resources.core.dataset.utils;

import static org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils.copyIdentifiableStatisticalResource;
import static org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils.copySiemacMetadataStatisticalResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.common.utils.CommonVersioningCopyUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;

public class DatasetVersioningCopyUtils extends CommonVersioningCopyUtils {

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
        // Metadata
        copyMetadata(source, target);
        copyCoverages(source, target);
        
        // Relations
        target.setDataset(source.getDataset());
        copyDatasources(source, target);
    }

    private static void copyDatasources(DatasetVersion source, DatasetVersion target) {
        target.getDatasources().clear();
        for (Datasource datasource : source.getDatasources()) {
            Datasource newDatasource = new Datasource();
            copyDatasource(datasource, newDatasource);
            target.addDatasource(newDatasource);
        }
    }
    
    private static void copyCoverages(DatasetVersion source, DatasetVersion target) {
        target.getDimensionsCoverage().clear();
        for (CodeDimension codeDimension : source.getDimensionsCoverage()) {
            CodeDimension newCodeDimension = new CodeDimension();
            copyCodeDimension(codeDimension, newCodeDimension);
            target.addDimensionsCoverage(newCodeDimension);
        }
    }

    private static void copyMetadata(DatasetVersion source, DatasetVersion target) {
        target.setSiemacMetadataStatisticalResource(copySiemacMetadataStatisticalResource(source.getSiemacMetadataStatisticalResource(), target.getSiemacMetadataStatisticalResource()));
               
        target.getGeographicCoverage().clear();
        target.getGeographicCoverage().addAll(copyListExternalItem(source.getGeographicCoverage()));
        
        target.getTemporalCoverage().clear();
        target.getTemporalCoverage().addAll(copyListTemporalCode(source.getTemporalCoverage()));
        
        target.getMeasureCoverage().clear();
        target.getMeasureCoverage().addAll(copyListExternalItem(source.getMeasureCoverage()));
        
        target.getGeographicGranularities().clear();
        target.getGeographicGranularities().addAll(copyListExternalItem(source.getGeographicGranularities()));
        
        target.getTemporalGranularities().clear();
        target.getTemporalGranularities().addAll(copyListExternalItem(source.getTemporalGranularities()));
        
        target.setDateStart(source.getDateStart());
        
        target.setDateEnd(source.getDateEnd());
        
        target.getStatisticalUnit().clear();
        target.getStatisticalUnit().addAll(copyListExternalItem(source.getStatisticalUnit()));
        
        target.setRelatedDsd(copyExternalItem(source.getRelatedDsd()));
        
        target.setFormatExtentObservations(source.getFormatExtentObservations());
        
        target.setFormatExtentDimensions(source.getFormatExtentDimensions());
        
        target.setUpdateFrequency(copyExternalItem(source.getUpdateFrequency()));
        
        target.setStatisticOfficiality(source.getStatisticOfficiality());
    }
    
    private static Collection<TemporalCode> copyListTemporalCode(List<TemporalCode> source) {
        if (source.isEmpty()) {
            return new ArrayList<TemporalCode>();
        }

        List<TemporalCode> target = new ArrayList<TemporalCode>();
        for (TemporalCode item : source) {
            target.add(copyTemporalCode(item));
        }
        return target;
    }
    
    public static TemporalCode copyTemporalCode(TemporalCode source) {
        if (source == null) {
            return null;
        }
        TemporalCode target = new TemporalCode();
        target.setIdentifier(source.getIdentifier());
        target.setTitle(source.getTitle());
        return target;
    }

    private static void copyDatasource(Datasource source, Datasource target) {
        target.setIdentifiableStatisticalResource(copyIdentifiableStatisticalResource(source.getIdentifiableStatisticalResource(), target.getIdentifiableStatisticalResource()));
        target.setFilename(source.getFilename());
        target.setDateNextUpdate(source.getDateNextUpdate());
    }
    
    public static CodeDimension copyCodeDimension(CodeDimension source, CodeDimension target) {
        target.setIdentifier(source.getIdentifier());
        target.setTitle(source.getTitle());
        target.setDsdComponentId(source.getDsdComponentId());
        return target;
    }

}