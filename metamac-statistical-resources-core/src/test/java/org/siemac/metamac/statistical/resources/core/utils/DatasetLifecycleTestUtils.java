package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class DatasetLifecycleTestUtils {

    private static StatisticalResourcesPersistedDoMocks persistedMocks = StatisticalResourcesPersistedDoMocks.getInstance();

    public static void prepareToProductionValidation(DatasetVersion datasetVersion) {
        LifecycleTestUtils.prepareToProductionValidationSiemac(datasetVersion);
        prepareToLifecycleCommonDatasetVersion(datasetVersion);
    }

    public static void fillAsProductionValidation(DatasetVersion datasetVersion) {
        LifecycleTestUtils.fillAsProductionValidationSiemac(datasetVersion);
    }

    public static void prepareToDiffusionValidation(DatasetVersion datasetVersion) {
        prepareToProductionValidation(datasetVersion);
        LifecycleTestUtils.prepareToDiffusionValidationSiemac(datasetVersion);
    }

    public static void fillAsDiffusionValidation(DatasetVersion datasetVersion) {
        LifecycleTestUtils.fillAsDiffusionValidationSiemac(datasetVersion);
    }

    public static void prepareToPublished(DatasetVersion datasetVersion) {
        prepareToDiffusionValidation(datasetVersion);
        LifecycleTestUtils.prepareToPublishingSiemac(datasetVersion);
    }

    public static void fillAsPublished(DatasetVersion datasetVersion) {
        LifecycleTestUtils.fillAsPublishedSiemac(datasetVersion);
    }

    public static void prepareToVersioning(DatasetVersion datasetVersion) {
        prepareToPublished(datasetVersion);
        LifecycleTestUtils.prepareToVersioningSiemac(datasetVersion);
    }

    public static void fillAsVersioned(DatasetVersion datasetVersion) {
        LifecycleTestUtils.fillAsVersionedSiemac(datasetVersion);
    }

    public static void prepareToValidationRejected(DatasetVersion datasetVersion) {
        prepareToProductionValidation(datasetVersion);
        LifecycleTestUtils.prepareToValidationRejectedFromProductionValidationSiemac(datasetVersion);
    }

    public static void fillAsValidationRejected(DatasetVersion datasetVersion) {
        LifecycleTestUtils.fillAsValidationRejectedFromProductionValidationSiemac(datasetVersion);
    }

    public static void prepareToLifecycleCommonDatasetVersion(DatasetVersion datasetVersion) {
        ExternalItem geoGranularity = StatisticalResourcesPersistedDoMocks.mockCodeExternalItem();
        datasetVersion.addGeographicGranularity(geoGranularity);

        ExternalItem timeGranularity = StatisticalResourcesPersistedDoMocks.mockCodeExternalItem();
        datasetVersion.addTemporalGranularity(timeGranularity);

        ExternalItem dsd = StatisticalResourcesPersistedDoMocks.mockDsdExternalItem();
        datasetVersion.setRelatedDsd(dsd);

        datasetVersion.setDateNextUpdate(new DateTime().plusDays(10));

        ExternalItem codeUpdateFreq = StatisticalResourcesPersistedDoMocks.mockCodeExternalItem();
        datasetVersion.setUpdateFrequency(codeUpdateFreq);

        StatisticOfficiality officiality = persistedMocks.mockStatisticOfficiality("officiality");
        datasetVersion.setStatisticOfficiality(officiality);

        if (datasetVersion.getDatasources().isEmpty()) {
            Datasource datasource = DatasourceMockFactory.generateSimpleDatasource();
            datasource.setDatasetVersion(datasetVersion);
            datasetVersion.addDatasource(datasource);
        }

        // Coverages
        fillDimensionCoverages(datasetVersion);
    }

    private static void fillDimensionCoverages(DatasetVersion datasetVersion) {
        datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2012", "2012"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2011", "2011"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010", "2010"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES", "España"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES61", "Andalucia"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES70", "Canarias"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES45", "Cataluña"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C01", "Concept 01"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C02", "Concept 02"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C03", "Concept 03"));

        datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2012", "2012"));
        datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2011", "2011"));
        datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2010", "2010"));

        datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES"));
        datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES61"));
        datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES70"));
        datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES45"));

        datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C01"));
        datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C02"));
        datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C03"));

        // Relations
        for (CodeDimension code : datasetVersion.getDimensionsCoverage()) {
            code.setDatasetVersion(datasetVersion);
        }
    }
}
