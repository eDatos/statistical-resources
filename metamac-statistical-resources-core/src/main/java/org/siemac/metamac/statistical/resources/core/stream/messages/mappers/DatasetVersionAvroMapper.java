package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.stream.messages.AttributeValueAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.CategorisationAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;

public class DatasetVersionAvroMapper {

    protected DatasetVersionAvroMapper() {
    }

    public static DatasetVersionAvro do2Avro(DatasetVersion source) throws MetamacException {
        List<DatasourceAvro> datasources = datasourcesList2Avro(source);
        List<CodeDimensionAvro> dimensions = dimensionsCoverage2Avro(source);
        List<AttributeValueAvro> coverageList = attributesCoverage2Avro(source);
        List<CategorisationAvro> categorisations = categorisations2Avro(source);
        List<ExternalItemAvro> geographicCoverageList = geographicCoverageList2Avro(source);
        List<TemporalCodeAvro> temporalCoverageList = temporalCoverageList2Avro(source);
        List<ExternalItemAvro> measureCoverageList = measureCoverageList2Avro(source);
        List<ExternalItemAvro> geoGranList = geoGranularitesList2Avro(source);
        List<ExternalItemAvro> temporalGranList = tempGranularitesList2Avro(source);
        List<ExternalItemAvro> statisticalUnitList = statisticalUnitList2Avro(source);

        DatasetVersionAvro target = DatasetVersionAvro.newBuilder()
                .setSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResourceAvroMapper.do2Avro(source.getSiemacMetadataStatisticalResource()))
                .setDateStart(DatetimeAvroMapper.do2Avro(source.getDateStart())).setDateEnd(DatetimeAvroMapper.do2Avro(source.getDateEnd()))
                .setRelatedDsdChanged(source.isRelatedDsdChanged())
                .setDatasetRepositoryId(source.getDatasetRepositoryId())
                .setFormatExtentDimensions(source.getFormatExtentDimensions())
                .setDateNextUpdate(DatetimeAvroMapper.do2Avro(source.getDateNextUpdate()))
                .setUserModifiedDateNextUpdate(source.getUserModifiedDateNextUpdate())
                .setVersion(source.getVersion())
                .setDataset(DatasetAvroMapper.do2Avro(source.getDataset()))
                .setRelatedDsd(ExternalItemAvroMapper.do2Avro(source.getRelatedDsd()))
                .setUpdateFrequency(ExternalItemAvroMapper.do2Avro(source.getUpdateFrequency()))
                .setStatisticOfficiality(StatisticOfficialityAvroMapper.do2Avro(source.getStatisticOfficiality()))
                .setBibliographicCitation(InternationalStringAvroMapper.do2Avro(source.getBibliographicCitation()))
                .setDatasources(datasources)
                .setDimensionsCoverage(dimensions )
                .setAttributesCoverage(coverageList )
                .setCategorisations(categorisations)
                .setGeographicCoverage(geographicCoverageList)
                .setTemporalCoverage(temporalCoverageList)
                .setMeasureCoverage(measureCoverageList)
                .setGeographicGranularities(geoGranList)
                .setTemporalGranularities(temporalGranList).setStatisticalUnit(statisticalUnitList)
                .build();
        return target;
    }


    protected static List<ExternalItemAvro> genericExternalItemList2Avro(Collection<ExternalItem> source) {
        List<ExternalItemAvro> measureCoverageList = new ArrayList<ExternalItemAvro>();
        for (ExternalItem externalItem : source) {
            ExternalItemAvro externalItemAvro = ExternalItemAvroMapper.do2Avro(externalItem);
            measureCoverageList.add(externalItemAvro);
        }
        return measureCoverageList;
    }

    protected static List<ExternalItemAvro> geoGranularitesList2Avro(DatasetVersion source) {
        return genericExternalItemList2Avro(source.getGeographicGranularities());
    }

    protected static List<ExternalItemAvro> tempGranularitesList2Avro(DatasetVersion source) {
        return genericExternalItemList2Avro(source.getTemporalGranularities());
    }

    protected static List<ExternalItemAvro> statisticalUnitList2Avro(DatasetVersion source) {
        return genericExternalItemList2Avro(source.getStatisticalUnit());
    }

    protected static List<ExternalItemAvro> measureCoverageList2Avro(DatasetVersion source) {
        return genericExternalItemList2Avro(source.getMeasureCoverage());
    }

    protected static List<TemporalCodeAvro> temporalCoverageList2Avro(DatasetVersion source) {
        List<TemporalCodeAvro> temporalCoverageList = new ArrayList<TemporalCodeAvro>();
        for (TemporalCode temporalCode : source.getTemporalCoverage()) {
            TemporalCodeAvro temporalCodeAvro = TemporalCodeAvroMapper.do2Avro(temporalCode);
            temporalCoverageList.add(temporalCodeAvro);
        }
        return temporalCoverageList;
    }

    protected static List<ExternalItemAvro> geographicCoverageList2Avro(DatasetVersion source) {
        return genericExternalItemList2Avro(source.getGeographicCoverage());
    }

    protected static List<AttributeValueAvro> attributesCoverage2Avro(DatasetVersion source) {
        List<AttributeValueAvro> coverageList = new ArrayList<AttributeValueAvro>();
        for (AttributeValue attribute : source.getAttributesCoverage()) {
            AttributeValueAvro attributeAvro = AttributeValueAvroMapper.do2Avro(attribute);
            coverageList.add(attributeAvro);
        }
        return coverageList;
    }

    protected static List<CodeDimensionAvro> dimensionsCoverage2Avro(DatasetVersion source) {
        List<CodeDimensionAvro> dimensions = new ArrayList<CodeDimensionAvro>();
        for (CodeDimension dimension : source.getDimensionsCoverage()) {
            CodeDimensionAvro dimensionAvro = CodeDimensionAvroMapper.do2Avro(dimension);
            dimensions.add(dimensionAvro);
        }
        return dimensions;
    }

    protected static List<DatasourceAvro> datasourcesList2Avro(DatasetVersion source) {
        List<DatasourceAvro> datasources = new ArrayList<DatasourceAvro>();
        for (Datasource datasource : source.getDatasources()) {
            DatasourceAvro datasourceAvro = DatasourceAvroMapper.do2Avro(datasource);
            datasources.add(datasourceAvro);
        }
        return datasources;
    }

    protected static List<CategorisationAvro> categorisations2Avro(DatasetVersion source) {
        List<CategorisationAvro> categorisations = new ArrayList<CategorisationAvro>();
        for (Categorisation categorisation : source.getCategorisations()) {
            CategorisationAvro categorisationAvro = CategorisationAvroMapper.do2Avro(categorisation);
            categorisations.add(categorisationAvro);
        }
        return categorisations;
    }



    public static DatasetVersion avro2Do(DatasetVersionAvro source) throws MetamacException {
        DatasetVersion target = new DatasetVersion();
        target.setSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResourceAvroMapper.avro2Do(source.getSiemacMetadataStatisticalResource()));
        target.setDateStart(DatetimeAvroMapper.avro2Do(source.getDateStart()));
        target.setDateEnd(DatetimeAvroMapper.avro2Do(source.getDateEnd()));
        target.setRelatedDsdChanged(source.getRelatedDsdChanged());
        target.setDatasetRepositoryId(source.getDatasetRepositoryId());
        target.setFormatExtentDimensions(source.getFormatExtentDimensions());
        target.setDateNextUpdate(DatetimeAvroMapper.avro2Do(source.getDateNextUpdate()));
        target.setUserModifiedDateNextUpdate(source.getUserModifiedDateNextUpdate());
        target.setVersion(source.getVersion());
        target.setDataset(DatasetAvroMapper.avro2Do(source.getDataset()));
        target.setRelatedDsd(ExternalItemAvroMapper.avro2Do(source.getRelatedDsd()));
        target.setUpdateFrequency(ExternalItemAvroMapper.avro2Do(source.getUpdateFrequency()));
        target.setStatisticOfficiality(StatisticOfficialityAvroMapper.avro2Do(source.getStatisticOfficiality()));
        target.setBibliographicCitation(InternationalStringAvroMapper.avro2Do(source.getBibliographicCitation()));
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
            target.addStatisticalUnit(ExternalItemAvroMapper.avro2Do(item));
        }
    }

    private static void temporalGranularities(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getTemporalGranularities()) {
            target.addTemporalGranularity(ExternalItemAvroMapper.avro2Do(item));
        }
    }

    private static void geographicGranularitiesListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getGeographicGranularities()) {
            target.addGeographicGranularity(ExternalItemAvroMapper.avro2Do(item));
        }
    }

    private static void measureCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getMeasureCoverage()) {
            target.addMeasureCoverage(ExternalItemAvroMapper.avro2Do(item));
        }
    }

    private static void temporalCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (TemporalCodeAvro item : source.getTemporalCoverage()) {
            target.addTemporalCoverage(TemporalCodeAvroMapper.avro2Do(item));
        }
    }

    private static void geographicCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) {
        for (ExternalItemAvro item : source.getGeographicCoverage()) {
            target.addGeographicCoverage(ExternalItemAvroMapper.avro2Do(item));
        }
    }

    private static void categorisationsListAvro2Do(DatasetVersionAvro source, DatasetVersion target) throws MetamacException {
        for (CategorisationAvro item : source.getCategorisations()) {
            target.addCategorisation(CategorisationAvroMapper.avro2Do(item));
        }
    }

    private static void attributesCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) throws MetamacException {
        for (AttributeValueAvro item : source.getAttributesCoverage()) {
            target.addAttributesCoverage(AttributeValueAvroMapper.avro2Do(item));
        }
    }

    private static void dimensionsCoverageListAvro2Do(DatasetVersionAvro source, DatasetVersion target) throws MetamacException {
        for (CodeDimensionAvro item : source.getDimensionsCoverage()) {
            target.addDimensionsCoverage(CodeDimensionAvroMapper.avro2Do(item));
        }
    }

    private static void datasourcesListAvro2Do(DatasetVersionAvro source, DatasetVersion target) throws MetamacException {
        for (DatasourceAvro item : source.getDatasources()) {
            target.addDatasource(DatasourceAvroMapper.avro2Do(item));
        }
    }


}
