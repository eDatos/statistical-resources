package org.siemac.metamac.statistical.resources.core.io.utils;

import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;

public class DbImportDatasetUtils {

    private DbImportDatasetUtils() {
        // NOTHING TO DO HERE
    }

    public static void setDatasetVersionDescription(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getDescription() == null) {
            // @formatter:off
            InternationalStringDto description = InternationalStringDtoBuilder.builder()
                    .withText(LocalisedStringDtoBuilder.builder()
                            .withLabel("prueba")
                            .withLocale("es")
                            .build())
                    .build();            
            // @formatter:on
            datasetVersionDto.setDescription(description);
        }
    }

    public static void setDatasetVersionNextVersion(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getNextVersion() == null) {
            datasetVersionDto.setNextVersion(NextVersionTypeEnum.SCHEDULED_UPDATE);
        }
    }

    public static void setDatasetVersionCreator(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getCreator() == null) {

            // @formatter:off
            ExternalItemDto creator = ExternalItemDtoBuilder.builder()
                    .withCode("A05002844")
                    .withManagementAppUrl("http://estadisticas.arte-consultores.com/structural-resources-internal/#structuralResources/organisationSchemes/organisationScheme;type=ORGANISATION_UNIT_SCHEME;id=ISTAC:UNIDADES_GOBCAN(03.000)/organisation;id=A05002844")
                    .withUri("http://estadisticas.arte-consultores.com/structural-resources-internal/apis/structural-resources-internal/latest/organisationunitschemes/ISTAC/UNIDADES_GOBCAN/03.000/organisationunits/A05002844")
                    .withUrn("urn:sdmx:org.sdmx.infomodel.base.OrganisationUnit=ISTAC:UNIDADES_GOBCAN(03.000).A05002844")
                    .withUrnProvider(null)
                    .withType(TypeExternalArtefactsEnum.ORGANISATION_UNIT)
                    .withTitle(InternationalStringDtoBuilder.builder()
                            .withText(LocalisedStringDtoBuilder.builder()
                                    .withLabel("Presidencia del Gobierno")
                                    .withLocale("es")
                                    .build())
                            .build())
                    .build();
            // @formatter:on

            datasetVersionDto.setCreator(creator);
        }
    }

    public static void setDatasetVersionPublisher(DatasetVersionDto datasetVersionDto) {
        if (CollectionUtils.isEmpty(datasetVersionDto.getPublisher())) {
            // @formatter:off
            ExternalItemDto publisherElement = ExternalItemDtoBuilder.builder()
                    .withCode("A05002844")
                    .withManagementAppUrl("http://estadisticas.arte-consultores.com/structural-resources-internal/#structuralResources/organisationSchemes/organisationScheme;type=ORGANISATION_UNIT_SCHEME;id=ISTAC:UNIDADES_GOBCAN(03.000)/organisation;id=A05002844")
                    .withType(TypeExternalArtefactsEnum.ORGANISATION_UNIT)
                    .withUri("http://estadisticas.arte-consultores.com/structural-resources-internal/apis/structural-resources-internal/latest/organisationunitschemes/ISTAC/UNIDADES_GOBCAN/03.000/organisationunits/A05002844")
                    .withUrn("urn:sdmx:org.sdmx.infomodel.base.OrganisationUnit=ISTAC:UNIDADES_GOBCAN(03.000).A05002844")
                    .withTitle(InternationalStringDtoBuilder.builder()
                            .withText(LocalisedStringDtoBuilder.builder()
                                    .withLabel("Presidencia del Gobierno")
                                    .withLocale("es")
                                    .build())
                            .build())
                    .build();
            // @formatter:on

            datasetVersionDto.addPublisher(publisherElement);
        }
    }

    public static void setDatasetVersionCommonMetadata(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getCommonMetadata() == null) {
            // @formatter:off
            ExternalItemDto commonMetadata = ExternalItemDtoBuilder.builder()
                    .withCode("ISTAC")
                    .withType(TypeExternalArtefactsEnum.CONFIGURATION)
                    .withUri("http://estadisticas.arte-consultores.com/cmetadata/latest/configurations/ISTAC")
                    .withUrn("urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=ISTAC")
                    .withManagementAppUrl("http://estadisticas.arte-consultores.com/common-metadata-internal/#configurations/configuration;id=ISTAC")
                    .build();
            // @formatter:on

            datasetVersionDto.setCommonMetadata(commonMetadata);
        }
    }

    public static void setDatasetVersionGeographicGranularities(DatasetVersionDto datasetVersionDto) {
        if (CollectionUtils.isEmpty(datasetVersionDto.getGeographicGranularities())) {
            // @formatter:off
            ExternalItemDto geographicGranularityElement = ExternalItemDtoBuilder.builder()
                    .withCode("COUNTIES")
                    .withType(TypeExternalArtefactsEnum.CODE)
                    .withUri("http://estadisticas.arte-consultores.com/structural-resources-internal/apis/structural-resources-internal/latest/codelists/ISTAC/CL_GEO_GRANULARITIES/01.006/codes/COUNTIES")
                    .withUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_GEO_GRANULARITIES(01.006).COUNTIES")
                    .withManagementAppUrl("http://estadisticas.arte-consultores.com/structural-resources-internal/#structuralResources/codelists/codelist;id=ISTAC:CL_GEO_GRANULARITIES(01.006)/code;id=COUNTIES")
                    .withTitle(InternationalStringDtoBuilder.builder()
                            .withText(LocalisedStringDtoBuilder.builder()
                                    .withLabel("Comarcas")
                                    .withLocale("es")
                                    .build())
                            .build())
                    .build();
            // @formatter:on        

            datasetVersionDto.addGeographicGranularity(geographicGranularityElement);
        }
    }

    public static void setDatasetVersionTemporalGranularities(DatasetVersionDto datasetVersionDto) {
        if (CollectionUtils.isEmpty(datasetVersionDto.getTemporalGranularities())) {
            // @formatter:off
            ExternalItemDto temporalGranularityElement = ExternalItemDtoBuilder.builder()
                    .withCode("A")
                    .withType(TypeExternalArtefactsEnum.CODE)
                    .withUri("http://estadisticas.arte-consultores.com/structural-resources-internal/apis/structural-resources-internal/latest/codelists/ISTAC/CL_FREQ/01.000/codes/A")
                    .withUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_FREQ(01.000).A")
                    .withManagementAppUrl("http://estadisticas.arte-consultores.com/structural-resources-internal/#structuralResources/codelists/codelist;id=ISTAC:CL_FREQ(01.000)/code;id=A")
                    .withTitle(InternationalStringDtoBuilder.builder()
                            .withText(LocalisedStringDtoBuilder.builder()
                                    .withLabel("Anual")
                                    .withLocale("es")
                                    .build())
                            .build())
                    .build();
            // @formatter:on   

            datasetVersionDto.addTemporalGranularity(temporalGranularityElement);
        }
    }

    public static void setDatasetVersionUpdateFrecuency(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getUpdateFrequency() == null) {
            // @formatter:off
            ExternalItemDto updateFrequency = ExternalItemDtoBuilder.builder()
                    .withCode("A")
                    .withType(TypeExternalArtefactsEnum.CODE)
                    .withUri("http://estadisticas.arte-consultores.com/structural-resources-internal/apis/structural-resources-internal/latest/codelists/ISTAC/CL_FREQ/01.000/codes/A")
                    .withUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_FREQ(01.000).A")
                    .withManagementAppUrl("http://estadisticas.arte-consultores.com/structural-resources-internal/#structuralResources/codelists/codelist;id=ISTAC:CL_FREQ(01.000)/code;id=A")
                    .withTitle(InternationalStringDtoBuilder.builder()
                            .withText(LocalisedStringDtoBuilder.builder()
                                    .withLabel("Anual")
                                    .withLocale("es")
                                    .build())
                            .build())
                    .build();
            // @formatter:on

            datasetVersionDto.setUpdateFrequency(updateFrequency);
        }
    }

    public static void setDatasetVersionStatisticOficiality(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getStatisticOfficiality() == null) {
            // @formatter:off
            StatisticOfficialityDto statisticOfficiality = StatisticOfficialityDtoBuilder.builder()
                    .withIdentifier("OFFICIAL")
                    .withDescription(InternationalStringDtoBuilder.builder()
                            .withText(LocalisedStringDtoBuilder.builder()
                                    .withLabel("Estadística oficial")
                                    .withLocale("es")
                                    .build())
                            .build())
                    .withVersion(Long.valueOf(0))
                    .withId(Long.valueOf(1))
                    .build();
            // @formatter:off

            datasetVersionDto.setStatisticOfficiality(statisticOfficiality);
        }
    }

    public static void setDatasetVersionVersionRationaleType(DatasetVersionDto datasetVersionDto) {
        if (CollectionUtils.isEmpty(datasetVersionDto.getVersionRationaleTypes())) {
            VersionRationaleTypeDto versionRationaleTypeDto = new VersionRationaleTypeDto(VersionRationaleTypeEnum.MINOR_DATA_UPDATE);
            datasetVersionDto.getVersionRationaleTypes().add(versionRationaleTypeDto);
        }
    }

    public static void setDatasetVersionNextVersionDate(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getNextVersionDate() == null) {
            datasetVersionDto.setNextVersionDate(new Date());
        }
        
    }

    public static void setDatasetVersionDateNextUpdate(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getDateNextUpdate() == null) {
            datasetVersionDto.setDateNextUpdate(new Date());
            
        }
    }

}
