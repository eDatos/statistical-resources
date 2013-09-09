package org.siemac.metamac.sdmx.data.rest.external.v2_1.utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DimensionTypeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.SimpleDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TimeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeRelationshipType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.PrimaryMeasureType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleDataStructureRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.UsageStatusType;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceObservationDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;

public class SdmxDataCoreMocks extends BaseJaxbMocks {

    public StatisticalResourcesPersistedDoMocks coreDoMocks;

    public SdmxDataCoreMocks(StatisticalResourcesPersistedDoMocks coreDoMocks) {
        this.coreDoMocks = coreDoMocks;
    }

    public static Map<String, ObservationExtendedDto> mockObservations() {

        Map<String, ObservationExtendedDto> observationsMap = new HashMap<String, ObservationExtendedDto>();

        // OBS: 2010-08#M#EUR#SP00#E#CHF
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "CHF"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-08"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("1.3413");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-08#M#EUR#SP00#E#JPY
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "JPY"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-08"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("110.04");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-08#M#EUR#SP00#E#GBP
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "GBP"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-08"));

            observationExtendedDto.setPrimaryMeasure("0.82363");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-08#M#EUR#SP00#E#USD
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "USD"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-08"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("1.2894");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-09#M#EUR#SP00#E#CHF
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "CHF"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-09"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("1.3089");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-09#M#EUR#SP00#E#GBP
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "GBP"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-09"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("0.83987");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-09#M#EUR#SP00#E#JPY
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "JPY"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-09"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("110.26");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-09#M#EUR#SP00#E#USD
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "USD"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-09"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("1.3067");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-10#M#EUR#SP00#E#CHF
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "CHF"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-10"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("1.3452");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-10#M#EUR#SP00#E#GBP
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "GBP"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-10"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("0.87637");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-10#M#EUR#SP00#E#JPY
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "JPY"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-10"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("113.67");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-10#M#EUR#SP00#E#USD
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "USD"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-10"));

            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("OBS_STATUS", "A"));
            observationExtendedDto.addAttribute(createAttributeInstanceObservationDto("CONF_STATUS_OBS", "F"));

            observationExtendedDto.setPrimaryMeasure("1.3898");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }
        return observationsMap;
    }

    public static Set<String> mockObservationsKeys() {
        Set<String> set = new HashSet<String>();

        set.add("M#CHF#EUR#SP00#E#2010-08");
        set.add("M#JPY#EUR#SP00#E#2010-08");
        set.add("M#GBP#EUR#SP00#E#2010-08");
        set.add("M#USD#EUR#SP00#E#2010-08");

        set.add("M#CHF#EUR#SP00#E#2010-09");
        set.add("M#GBP#EUR#SP00#E#2010-09");
        set.add("M#JPY#EUR#SP00#E#2010-09");
        set.add("M#USD#EUR#SP00#E#2010-09");

        set.add("M#CHF#EUR#SP00#E#2010-10");
        set.add("M#GBP#EUR#SP00#E#2010-10");
        set.add("M#JPY#EUR#SP00#E#2010-10");
        set.add("M#USD#EUR#SP00#E#2010-10");

        return set;
    }

    public static AttributeInstanceObservationDto createAttributeInstanceObservationDto(String attributeId, String value) {
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel(value);
        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
        internationalStringDto.addText(localisedStringDto);

        return new AttributeInstanceObservationDto(attributeId, internationalStringDto);
    }

    private DatasetVersion mockDatasetVersionBasic(String agencyID, String resourceID, String version) {
        DatasetVersion target = coreDoMocks.mockDatasetVersion();
        target.getSiemacMetadataStatisticalResource().setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=" + agencyID + ":" + resourceID + "(" + version + ")");
        target.getSiemacMetadataStatisticalResource().getMaintainer().setCodeNested(agencyID);
        target.getSiemacMetadataStatisticalResource().setCode(resourceID);
        target.getSiemacMetadataStatisticalResource().setVersionLogic(version);
        target.getSiemacMetadataStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalStringMetadata(resourceID, "title"));
        return target;
    }

    public DatasetVersion mockDatasetVersion(String agencyID, String resourceID, String version) {
        DatasetVersion target = mockDatasetVersionBasic(agencyID, resourceID, version);
        target.setBibliographicCitation(new InternationalString("es", "bibliographicCitation. More info: #URI#"));
        target.setDatasetRepositoryId("datasetRepository01");

        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("santa-cruz-tenerife"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("tenerife"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("la-laguna"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("santa-cruz"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("la-palma"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("santa-cruz-la-palma"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("los-llanos-de-aridane"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("la-gomera"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("el-hierro"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("las-palmas-gran-canaria"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("gran-canaria"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("fuerteventura"));
        target.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("lanzarote"));

        target.addTemporalCoverage(mockTemporalCode("2014"));
        target.addTemporalCoverage(mockTemporalCode("2013"));
        target.addTemporalCoverage(mockTemporalCode("2012"));
        target.addTemporalCoverage(mockTemporalCode("2011"));
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
        target.setFormatExtentObservations(Long.valueOf(3));
        target.setFormatExtentDimensions(Integer.valueOf(5));
        target.setDateNextUpdate(new DateTime(2013, 12, 2, 3, 4, 5, 0));
        target.setUpdateFrequency(StatisticalResourcesDoMocks.mockCodeExternalItem("updateFrequency01"));
        target.setStatisticOfficiality(coreDoMocks.mockStatisticOfficiality("statisticOfficiality01"));
        mockSiemacMetadataStatisticalResource(agencyID, resourceID, version, target.getSiemacMetadataStatisticalResource());
        return target;
    }

    private TemporalCode mockTemporalCode(String code) {
        return StatisticalResourcesDoMocks.mockTemporalCode(code, code);
    }

    private void mockSiemacMetadataStatisticalResource(String agencyID, String resourceID, String version, SiemacMetadataStatisticalResource target) {
        target.addStatisticalOperationInstance(StatisticalResourcesDoMocks.mockStatisticalOperationInstanceExternalItem("instance01"));
        target.addStatisticalOperationInstance(StatisticalResourcesDoMocks.mockStatisticalOperationInstanceExternalItem("instance02"));
        target.setResourceCreatedDate(new DateTime(2012, 1, 2, 3, 4, 5, 0));
        target.setLastUpdate(new DateTime(2013, 1, 2, 3, 4, 5, 0));
        target.setNewnessUntilDate(new DateTime(2013, 9, 2, 15, 4, 5, 0));
        target.setReplaces(mockDatasetRelatedResource(agencyID, "replace01", "01.000"));
        target.addHasPart(mockDatasetRelatedResource(agencyID, "hasPart01", "01.000"));
        target.addHasPart(mockDatasetRelatedResource(agencyID, "hasPart02", "01.000"));
        target.addIsPartOf(mockDatasetRelatedResource(agencyID, "isPartOf01", "01.000"));
        target.addIsPartOf(mockDatasetRelatedResource(agencyID, "isPartOf02", "01.000"));
        target.setCopyrightedDate(2014);
        mockLifeCycleStatisticalResource(agencyID, resourceID, version, target);
    }

    public RelatedResource mockDatasetRelatedResource(String agencyID, String resourceID, String version) {
        DatasetVersion dataset = mockDatasetVersionBasic(agencyID, resourceID, version);
        return StatisticalResourcesDoMocks.mockDatasetVersionRelated(dataset);
    }

    private void mockLifeCycleStatisticalResource(String agencyID, String resourceID, String version, LifeCycleStatisticalResource target) {
        target.setPublicationDate(new DateTime(2013, 1, 1, 3, 4, 13, 0));
        target.setReplacesVersion(mockDatasetRelatedResource(agencyID, version, "00.001"));
        mockVersionableStatisticalResource(agencyID, resourceID, version, target);
    }

    private void mockVersionableStatisticalResource(String agencyID, String resourceID, String version, VersionableStatisticalResource target) {
        target.setVersionRationale(StatisticalResourcesDoMocks.mockInternationalStringMetadata(resourceID, "versionRationale"));
        target.addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_CATEGORIES));
        target.addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MINOR_METADATA));
        target.setValidFrom(new DateTime(2013, 1, 1, 3, 4, 12, 0));
        target.setValidTo(new DateTime(2013, 2, 16, 14, 4, 12, 0));
    }

    public DataStructureType mockDsd_ECB_EXR_RG() {
        DataStructureType dataStructureType = structureObjectFactory.createDataStructureType();
        dataStructureType.setAgencyID("ECB");
        dataStructureType.setId("ECB");
        dataStructureType.setVersion("1.0");

        // Components
        {
            DataStructureComponentsType dataStructureComponentsType = structureObjectFactory.createDataStructureComponentsType();

            // DimensionList
            {
                DimensionListType dimensionListType = structureObjectFactory.createDimensionListType();
                dimensionListType.setId("DimensionDescriptor");

                // FREQ
                dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(mockDimensionType("FREQ", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "FREQ"), null));

                // CURRENCY
                dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(
                        mockDimensionType("CURRENCY", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "CURRENCY"),
                                mockEnumerationCodelist(mockCodelistReferenceType("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)"))));

                // FREQ
                dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(
                        mockDimensionType("CURRENCY_DENOM", mockConceptReferenceType("ECB", "ECB_CONCEPTS", "1.0", "CURRENCY_DENOM"), null));

                // EXR_TYPE
                dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(mockDimensionType("EXR_TYPE", mockConceptReferenceType("ECB", "ECB_CONCEPTS", "1.0", "EXR_TYPE"), null));

                // EXR_VAR
                dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(mockDimensionType("EXR_VAR", mockConceptReferenceType("ECB", "ECB_CONCEPTS", "1.0", "EXR_VAR"), null));

                // TIME_PERIOD
                dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(
                        mockTimeDimensionType(mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "TIME_PERIOD"), TimeDataType.OBSERVATIONAL_TIME_PERIOD));

                dataStructureComponentsType.setDimensionList(dimensionListType);
            }

            // SiblingGroup
            {
                GroupType groupType = structureObjectFactory.createGroupType();

                groupType.setId("SiblingGroup");
                groupType.getGroupDimensions().add(mockGroupDimensionType("CURRENCY"));
                groupType.getGroupDimensions().add(mockGroupDimensionType("CURRENCY_DENOM"));
                groupType.getGroupDimensions().add(mockGroupDimensionType("EXR_TYPE"));
                groupType.getGroupDimensions().add(mockGroupDimensionType("EXR_VAR"));

                dataStructureComponentsType.getGroups().add(groupType);
            }

            // RateGroup
            {
                GroupType groupType = structureObjectFactory.createGroupType();

                groupType.setId("RateGroup");
                groupType.getGroupDimensions().add(mockGroupDimensionType("EXR_TYPE"));
                groupType.getGroupDimensions().add(mockGroupDimensionType("EXR_VAR"));

                dataStructureComponentsType.getGroups().add(groupType);
            }

            // AttributeList
            {
                AttributeListType attributeListType = structureObjectFactory.createAttributeListType();
                attributeListType.setId("AttributeDescriptor");

                {
                    // COLL_METHOD
                    attributeListType.getAttributesAndReportingYearStartDaies().add(
                            mockAttributeType("COLL_METHOD", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "COLL_METHOD"), UsageStatusType.CONDITIONAL,
                                    mockAttributeRelationshipGroupType("RateGroup"), null, (ConceptReferenceType[]) null));
                }

                {
                    // DECIMALS
                    attributeListType
                            .getAttributesAndReportingYearStartDaies()
                            .add(mockAttributeType("DECIMALS", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "DECIMALS"), UsageStatusType.MANDATORY,
                                    mockAttributeRelationshipDimensionType(Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"), Arrays.asList("SiblingGroup")), null, (ConceptReferenceType[]) null));
                }

                {
                    // UNIT_MEASURE
                    attributeListType.getAttributesAndReportingYearStartDaies().add(
                            mockAttributeType("UNIT_MEASURE", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "UNIT_MEASURE"), UsageStatusType.MANDATORY,
                                    mockAttributeRelationshipDimensionType(Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"), Arrays.asList("SiblingGroup")),
                                    mockEnumerationCodelist(mockCodelistReferenceType("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)")), (ConceptReferenceType[]) null));
                }

                {
                    // UNIT_MULT
                    attributeListType
                            .getAttributesAndReportingYearStartDaies()
                            .add(mockAttributeType("UNIT_MULT", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "UNIT_MULT"), UsageStatusType.MANDATORY,
                                    mockAttributeRelationshipDimensionType(Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"), Arrays.asList("SiblingGroup")), null, (ConceptReferenceType[]) null));
                }

                {
                    // CONF_STATUS_OBS
                    attributeListType.getAttributesAndReportingYearStartDaies().add(
                            mockAttributeType("CONF_STATUS_OBS", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "CONF_STATUS_OBS"), UsageStatusType.CONDITIONAL,
                                    mockAttributeRelationshipPrimaryMeasureType(), null, (ConceptReferenceType[]) null));
                }

                {
                    // OBS_STATUS
                    attributeListType.getAttributesAndReportingYearStartDaies().add(
                            mockAttributeType("OBS_STATUS", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "OBS_STATUS"), UsageStatusType.MANDATORY,
                                    mockAttributeRelationshipPrimaryMeasureType(), null, (ConceptReferenceType[]) null));
                }

                {
                    // TITLE
                    attributeListType.getAttributesAndReportingYearStartDaies().add(
                            mockAttributeType("TITLE", mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "TITLE"), UsageStatusType.MANDATORY,
                                    mockAttributeRelationshipGroupType("SiblingGroup"), null, (ConceptReferenceType[]) null));
                }

                dataStructureComponentsType.setAttributeList(attributeListType);
            }

            // MeasureList
            {
                MeasureListType measureListType = structureObjectFactory.createMeasureListType();
                measureListType.setPrimaryMeasure(mockPrimaryMeasureType(mockConceptReferenceType("SDMX", "CROSS_DOMAIN_CONCEPTS", "1.0", "OBS_VALUE"), null));
                dataStructureComponentsType.setMeasureList(measureListType);
            }

            dataStructureType.setDataStructureComponents(dataStructureComponentsType);
        }

        return dataStructureType;
    }

    public static DimensionType mockDimensionType(String code, ConceptReferenceType conceptIdentity, SimpleDataStructureRepresentationType localRepresentation, ConceptReferenceType... roles) {
        DimensionType dimensionType = structureObjectFactory.createDimensionType();
        dimensionType.setId(code);

        dimensionType.setConceptIdentity(conceptIdentity);
        dimensionType.setLocalRepresentation(localRepresentation);
        if (roles != null && roles.length > 0) {
            dimensionType.getConceptRoles().addAll(Arrays.asList(roles));
        }

        dimensionType.setType(DimensionTypeType.DIMENSION);
        return dimensionType;
    }

    public static TimeDimensionType mockTimeDimensionType(ConceptReferenceType conceptIdentity, TimeDataType timeDataType) {
        TimeDimensionType timeDimensionType = structureObjectFactory.createTimeDimensionType();

        timeDimensionType.setId("TIME_PERIOD");
        timeDimensionType.setConceptIdentity(conceptIdentity);
        timeDimensionType.setLocalRepresentation(mockTimeDimensionRepresentationType(timeDataType));

        timeDimensionType.setPosition(1);
        timeDimensionType.setType(DimensionTypeType.TIME_DIMENSION);

        return timeDimensionType;
    }

    public static TimeDimensionRepresentationType mockTimeDimensionRepresentationType(TimeDataType timeDataType) {
        TimeDimensionRepresentationType timeDimensionRepresentationType = structureObjectFactory.createTimeDimensionRepresentationType();

        TimeTextFormatType timeTextFormatType = new TimeTextFormatType();
        timeTextFormatType.setTextType(timeDataType);

        timeDimensionRepresentationType.setTextFormat(timeTextFormatType);

        return timeDimensionRepresentationType;
    }

    public static SimpleDataStructureRepresentationType mockEnumerationCodelist(CodelistReferenceType codelistReferenceType) {
        SimpleDataStructureRepresentationType simpleDataStructureRepresentationType = structureObjectFactory.createSimpleDataStructureRepresentationType();
        simpleDataStructureRepresentationType.setEnumeration(codelistReferenceType);

        return simpleDataStructureRepresentationType;
    }

    public static SimpleDataStructureRepresentationType mockSimpleDataStructureRepresentationType() {
        SimpleDataStructureRepresentationType simpleDataStructureRepresentationType = structureObjectFactory.createSimpleDataStructureRepresentationType();

        SimpleComponentTextFormatType simpleComponentTextFormatType = structureObjectFactory.createSimpleComponentTextFormatType();
        simpleComponentTextFormatType.setTextType(SimpleDataType.ALPHA_NUMERIC);
        simpleComponentTextFormatType.setMaxLength(new BigInteger("10"));

        simpleDataStructureRepresentationType.setTextFormat(simpleComponentTextFormatType);

        return simpleDataStructureRepresentationType;
    }

    public static GroupDimensionType mockGroupDimensionType(String dimensionID) {
        GroupDimensionType groupDimensionType = structureObjectFactory.createGroupDimensionType();
        groupDimensionType.setDimensionReference(mockLocalDimensionReferenceType(dimensionID));

        return groupDimensionType;
    }

    public static AttributeType mockAttributeType(String code, ConceptReferenceType conceptIdentity, UsageStatusType usageStatusType, AttributeRelationshipType attributeRelationshipType,
            SimpleDataStructureRepresentationType localRepresentation, ConceptReferenceType... roles) {
        AttributeType attributeType = structureObjectFactory.createAttributeType();

        attributeType.setId(code);

        attributeType.setConceptIdentity(conceptIdentity);
        attributeType.setLocalRepresentation(mockSimpleDataStructureRepresentationType());
        if (roles != null && roles.length > 0) {
            attributeType.getConceptRoles().addAll(Arrays.asList(roles));
        }

        attributeType.setAssignmentStatus(UsageStatusType.CONDITIONAL);
        attributeType.setAttributeRelationship(attributeRelationshipType);

        return attributeType;
    }

    public static AttributeRelationshipType mockAttributeRelationshipDimensionType(List<String> dimensionIDs, List<String> attachmentgroupIds) {
        AttributeRelationshipType attributeRelationshipType = structureObjectFactory.createAttributeRelationshipType();

        // Dimensions
        for (String dimensionID : dimensionIDs) {
            attributeRelationshipType.getDimensions().add(mockLocalDimensionReferenceType(dimensionID));
        }

        // Attachments groups
        for (String attachmentgroupId : attachmentgroupIds) {
            attributeRelationshipType.getAttachmentGroups().add(mockLocalGroupKeyDescriptorReferenceType(attachmentgroupId));
        }

        return attributeRelationshipType;
    }

    public static AttributeRelationshipType mockAttributeRelationshipGroupType(String groupID) {
        AttributeRelationshipType attributeRelationshipType = structureObjectFactory.createAttributeRelationshipType();

        attributeRelationshipType.setGroup(mockLocalGroupKeyDescriptorReferenceType(groupID));

        return attributeRelationshipType;
    }

    public static AttributeRelationshipType mockAttributeRelationshipPrimaryMeasureType() {
        AttributeRelationshipType attributeRelationshipType = structureObjectFactory.createAttributeRelationshipType();

        attributeRelationshipType.setPrimaryMeasure(mockLocalPrimaryMeasureReferenceType());

        return attributeRelationshipType;
    }

    public static PrimaryMeasureType mockPrimaryMeasureType(ConceptReferenceType conceptIdentity, SimpleDataStructureRepresentationType localRepresentation) {

        PrimaryMeasureType primaryMeasureType = structureObjectFactory.createPrimaryMeasureType();

        primaryMeasureType.setId("OBS_VALUE");
        primaryMeasureType.setConceptIdentity(conceptIdentity);
        primaryMeasureType.setLocalRepresentation(localRepresentation);

        return primaryMeasureType;
    }
}