package org.siemac.metamac.rest.statistical_resources.v1_0.dataset.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class DatasetsRestDoMocks {

    public StatisticalResourcesPersistedDoMocks coreDoMocks;

    public DatasetsRestDoMocks(StatisticalResourcesPersistedDoMocks coreDoMocks) {
        this.coreDoMocks = coreDoMocks;
    }

    public DatasetVersion mockDatasetVersionBasic(String agencyID, String resourceID, String version) {
        DatasetVersion target = coreDoMocks.mockDatasetVersion();
        target.getSiemacMetadataStatisticalResource().setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=" + agencyID + ":" + resourceID + "(" + version + ")");
        target.getSiemacMetadataStatisticalResource().getMaintainer().setCodeNested(agencyID);
        target.getSiemacMetadataStatisticalResource().setCode(resourceID);
        target.getSiemacMetadataStatisticalResource().setVersionLogic(version);
        return target;
    }

    public DatasetVersion mockDatasetVersion(String agencyID, String resourceID, String version) {
        DatasetVersion target = mockDatasetVersionBasic(agencyID, resourceID, version);
        // TODO Robert: ¿puedo pasar algo al mock del core?

        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("GEO_DIM-codelist01-code01"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("GEO_DIM-codelist01-code02"));
        target.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2012", "Y2012"));
        target.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2013", "Y2013"));
        target.addMeasureCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("code-d2-1"));
        target.addMeasureCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("code-d2-2"));
        target.addGeographicGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem("municipalities"));
        target.addGeographicGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem("countries"));
        target.addTemporalGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem("yearly"));
        target.addTemporalGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem("monthly"));
        target.setDateStart(new DateTime(2013, 1, 2, 3, 4, 5, 0));
        target.setDateEnd(new DateTime(2013, 3, 5, 6, 7, 8, 0));
        target.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem("statisticalUnit1"));
        target.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem("statisticalUnit2"));
        target.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem("statisticalUnit3"));
        target.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem("DSD01"));
        target.setFormatExtentObservations(Integer.valueOf(3));
        target.setFormatExtentDimensions(Integer.valueOf(5));
        target.setDateNextUpdate(new DateTime(2013, 12, 2, 3, 4, 5, 0));
        target.setUpdateFrequency(StatisticalResourcesDoMocks.mockCodeExternalItem("updateFrequency01"));
        target.setStatisticOfficiality(coreDoMocks.mockStatisticOfficiality("statisticOfficiality01"));
        mockSiemacMetadataStatisticalResource(agencyID, resourceID, version, target.getSiemacMetadataStatisticalResource());
        return target;
    }

    public RelatedResource mockDatasetRelatedResource(String agencyID, String resourceID, String version) {
        DatasetVersion dataset = mockDatasetVersionBasic(agencyID, resourceID, version);
        return StatisticalResourcesDoMocks.mockDatasetVersionRelated(dataset);
    }

    public CodeDimension mockCodeDimension(String componentId, String id) {
        CodeDimension codeDimension = new CodeDimension();
        codeDimension.setDsdComponentId(componentId);
        codeDimension.setIdentifier(id);
        codeDimension.setTitle(codeDimension.getIdentifier() + " en Español");
        return codeDimension;
    }

    private void mockSiemacMetadataStatisticalResource(String agencyID, String resourceID, String version, SiemacMetadataStatisticalResource target) {
        target.addStatisticalOperationInstance(StatisticalResourcesDoMocks.mockStatisticalOperationInstanceExternalItem("instance01"));
        target.addStatisticalOperationInstance(StatisticalResourcesDoMocks.mockStatisticalOperationInstanceExternalItem("instance02"));
        target.setResourceCreatedDate(new DateTime(2012, 1, 2, 3, 4, 5, 0));
        target.setLastUpdate(new DateTime(2013, 1, 2, 3, 4, 5, 0));
        target.setNewnessUntilDate(new DateTime(2013, 9, 2, 15, 4, 5, 0));
        target.setReplaces(mockDatasetRelatedResource(agencyID, "replace01", "01.000"));
        target.setIsReplacedBy(mockDatasetRelatedResource(agencyID, "replacedBy01", "02.000"));
        target.addRequire(mockDatasetRelatedResource(agencyID, "require01", "01.000"));
        target.addRequire(mockDatasetRelatedResource(agencyID, "require02", "01.000"));
        target.addIsRequiredBy(mockDatasetRelatedResource(agencyID, "isRequiredBy01", "01.000"));
        target.addIsRequiredBy(mockDatasetRelatedResource(agencyID, "isRequiredBy02", "01.000"));
        target.addHasPart(mockDatasetRelatedResource(agencyID, "hasPart01", "01.000"));
        target.addHasPart(mockDatasetRelatedResource(agencyID, "hasPart02", "01.000"));
        target.addIsPartOf(mockDatasetRelatedResource(agencyID, "isPartOf01", "01.000"));
        target.addIsPartOf(mockDatasetRelatedResource(agencyID, "isPartOf02", "01.000"));
        target.setCopyrightedDate(new DateTime(2014, 2, 15, 3, 4, 12, 0));
        mockLifeCycleStatisticalResource(agencyID, resourceID, version, target);
    }

    private void mockLifeCycleStatisticalResource(String agencyID, String resourceID, String version, LifeCycleStatisticalResource target) {
        target.setPublicationDate(new DateTime(2013, 1, 1, 3, 4, 13, 0));
        target.setReplacesVersion(mockDatasetRelatedResource(agencyID, version, "00.001"));
        target.setIsReplacedByVersion(mockDatasetRelatedResource(agencyID, version, "02.000"));
        mockVersionableStatisticalResource(agencyID, resourceID, version, target);
    }

    private void mockVersionableStatisticalResource(String agencyID, String resourceID, String version, VersionableStatisticalResource target) {
        target.setVersionRationale(StatisticalResourcesDoMocks.mockInternationalStringMetadata(resourceID, "versionRationale"));
        target.addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_CATEGORIES));
        target.addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MINOR_METADATA));
        target.setValidFrom(new DateTime(2013, 1, 1, 3, 4, 12, 0));
        target.setValidTo(new DateTime(2013, 2, 16, 14, 4, 12, 0));
    }
}