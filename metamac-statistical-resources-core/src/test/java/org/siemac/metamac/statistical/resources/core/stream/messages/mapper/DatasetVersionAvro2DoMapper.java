package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.AttributeValueAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.CategorisationAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;

public class DatasetVersionAvro2DoMapper {

    protected DatasetVersionAvro2DoMapper() {
    }

    public static DatasetVersion avro2Do(DatasetVersionAvro source) throws MetamacException {
        DatasetVersion target = new DatasetVersion();
        target.setSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResourceAvro2DoMapper.avro2Do(source.getSiemacMetadataStatisticalResource()));
        target.setDateStart(DateTimeAvro2DoMapper.avro2Do(source.getDateStart()));
        target.setDateEnd(DateTimeAvro2DoMapper.avro2Do(source.getDateEnd()));
        target.setDatasetRepositoryId(source.getDatasetRepositoryId());
        target.setFormatExtentDimensions(source.getFormatExtentDimensions());
        target.setDateNextUpdate(DateTimeAvro2DoMapper.avro2Do(source.getDateNextUpdate()));
        target.setUserModifiedDateNextUpdate(source.getUserModifiedDateNextUpdate());
        target.setDataset(DatasetAvro2DoMapper.avro2Do(source.getDataset()));
        target.setRelatedDsd(ExternalItemAvro2DoMapper.avro2Do(source.getRelatedDsd()));
        target.setUpdateFrequency(ExternalItemAvro2DoMapper.avro2Do(source.getUpdateFrequency()));
        target.setStatisticOfficiality(StatisticOfficialityAvro2DoMapper.avro2Do(source.getStatisticOfficiality()));
        target.setBibliographicCitation(InternationalStringAvro2DoMapper.avro2Do(source.getBibliographicCitation()));
        datasourcesListAvro2Do(source, target);
        dimensionsCoverageListAvro2Do(source, target);
        attributesCoverageListAvro2Do(source, target);
        categorisationsListAvro2Do(source, target);
        geographicCoverageListAvro2Do(source, target);
        temporalCoverageListAvro2Do(source, target);
        measureCoverageListAvro2Do(source, target);
        geographicGranularitiesListAvro2Do(source, target);
        temporalGranularities(source, target);
        statisticalUnitListAvro2Do(source, target);
        return target;
    }

    private static void statisticalUnitListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getStatisticalUnit()) {
            target.addStatisticalUnit(ExternalItemAvro2DoMapper.avro2Do(item));
        }
    }

    private static void temporalGranularities(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getTemporalGranularities()) {
            target.addTemporalGranularity(ExternalItemAvro2DoMapper.avro2Do(item));
        }
    }

    private static void geographicGranularitiesListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getGeographicGranularities()) {
            target.addGeographicGranularity(ExternalItemAvro2DoMapper.avro2Do(item));
        }
    }

    private static void measureCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getMeasureCoverage()) {
            target.addMeasureCoverage(ExternalItemAvro2DoMapper.avro2Do(item));
        }
    }

    private static void temporalCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (TemporalCodeAvro item : source.getTemporalCoverage()) {
            target.addTemporalCoverage(TemporalCodeAvro2DoMapper.avro2Do(item));
        }
    }

    private static void geographicCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getGeographicCoverage()) {
            target.addGeographicCoverage(ExternalItemAvro2DoMapper.avro2Do(item));
        }
    }

    private static void categorisationsListAvro2Do(DatasetVersionAvro source, DatasetVersion target) throws MetamacException {
        for (CategorisationAvro item : source.getCategorisations()) {
            target.addCategorisation(CategorisationAvro2DoMapper.avro2Do(item));
        }
    }

    private static void attributesCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) throws MetamacException {
        for (AttributeValueAvro item : source.getAttributesCoverage()) {
            target.addAttributesCoverage(AttributeValueAvro2DoMapper.avro2Do(item));
        }
    }

    private static void dimensionsCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) throws MetamacException {
        for (CodeDimensionAvro item : source.getDimensionsCoverage()) {
            target.addDimensionsCoverage(CodeDimensionAvro2DoMapper.avro2Do(item));
        }
    }

    private static void datasourcesListAvro2Do(DatasetVersionAvro source, DatasetVersion target) throws MetamacException {
        for (DatasourceAvro item : source.getDatasources()) {
            target.addDatasource(DatasourceAvro2DoMapper.avro2Do(item));
        }
    }
}
