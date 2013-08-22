package org.siemac.metamac.statistical.resources.web.shared.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.shared.RelatedResourceBaseUtils;

public class RelatedResourceUtils extends RelatedResourceBaseUtils {

    // -------------------------------------------------------------------------------------------------------------
    // IDENTIFIABLE RESOURCE
    // -------------------------------------------------------------------------------------------------------------

    public static RelatedResourceDto getIdentifiableResourceDtoAsRelatedResourceDto(IdentifiableStatisticalResourceDto identifiableStatisticalResourceDto) {
        RelatedResourceDto relatedResourceDto = new RelatedResourceDto();
        relatedResourceDto.setCode(identifiableStatisticalResourceDto.getCode());
        relatedResourceDto.setUrn(identifiableStatisticalResourceDto.getUrn());
        relatedResourceDto.setStatisticalOperationUrn(identifiableStatisticalResourceDto.getStatisticalOperation() != null
                ? identifiableStatisticalResourceDto.getStatisticalOperation().getUrn()
                : null);
        return relatedResourceDto;
    }

    // -------------------------------------------------------------------------------------------------------------
    // NAMEABLE RESOURCE
    // -------------------------------------------------------------------------------------------------------------

    public static RelatedResourceDto getNameableResourceDtoAsRelatedResourceDto(NameableStatisticalResourceDto nameableStatisticalResourceDto) {
        RelatedResourceDto relatedResourceDto = getIdentifiableResourceDtoAsRelatedResourceDto(nameableStatisticalResourceDto);
        relatedResourceDto.setTitle(nameableStatisticalResourceDto.getTitle());
        return relatedResourceDto;
    }

    // -------------------------------------------------------------------------------------------------------------
    // PUBLICATIONS
    // -------------------------------------------------------------------------------------------------------------

    public static RelatedResourceDto getPublicationDtoAsRelatedResourceDto(PublicationVersionDto publicationDto) {
        RelatedResourceDto relatedResourceDto = getNameableResourceDtoAsRelatedResourceDto(publicationDto);
        relatedResourceDto.setType(TypeRelatedResourceEnum.PUBLICATION_VERSION);
        return relatedResourceDto;
    }

    public static List<RelatedResourceDto> getPublicationDtosAsRelatedResourceDtos(List<PublicationVersionDto> publicationDtos) {
        List<RelatedResourceDto> relatedResourceDtos = new ArrayList<RelatedResourceDto>(publicationDtos.size());
        for (PublicationVersionDto publicationDto : publicationDtos) {
            relatedResourceDtos.add(getPublicationDtoAsRelatedResourceDto(publicationDto));
        }
        return relatedResourceDtos;
    }

    // -------------------------------------------------------------------------------------------------------------
    // DATASETS
    // -------------------------------------------------------------------------------------------------------------

    public static RelatedResourceDto getDatasetVersionDtoAsRelatedResourceDto(DatasetVersionDto DatasetVersionDto) {
        RelatedResourceDto relatedResourceDto = getNameableResourceDtoAsRelatedResourceDto(DatasetVersionDto);
        relatedResourceDto.setType(TypeRelatedResourceEnum.DATASET_VERSION);
        return relatedResourceDto;
    }

    public static List<RelatedResourceDto> getDatasetVersionDtosAsRelatedResourceDtos(List<DatasetVersionDto> DatasetVersionDtos) {
        List<RelatedResourceDto> relatedResourceDtos = new ArrayList<RelatedResourceDto>(DatasetVersionDtos.size());
        for (DatasetVersionDto DatasetVersionDto : DatasetVersionDtos) {
            relatedResourceDtos.add(getDatasetVersionDtoAsRelatedResourceDto(DatasetVersionDto));
        }
        return relatedResourceDtos;
    }

    public static String getRelatedResourceName(RelatedResourceDto relatedResourceDto) {
        return relatedResourceDto != null ? CommonWebUtils.getElementName(relatedResourceDto.getCode(), relatedResourceDto.getTitle()) : StringUtils.EMPTY;
    }

    public static RelatedResourceDto getRelatedResourceFromExternalItemDto(ExternalItemDto externalItem) {
        RelatedResourceDto relatedResource = new RelatedResourceDto();
        relatedResource.setId(externalItem.getId());
        relatedResource.setCode(externalItem.getCode());
        relatedResource.setTitle(externalItem.getTitle());
        relatedResource.setUrn(externalItem.getUrn());
        relatedResource.setUuid(externalItem.getUuid());
        relatedResource.setVersion(externalItem.getVersion());

        return relatedResource;
    }

    public static List<RelatedResourceDto> getRelatedResourceDtosFromExternalItemDtos(List<ExternalItemDto> externalItems) {
        List<RelatedResourceDto> relatedResources = new ArrayList<RelatedResourceDto>();
        if (externalItems != null) {
            for (ExternalItemDto externalItem : externalItems) {
                relatedResources.add(getRelatedResourceFromExternalItemDto(externalItem));
            }
        }
        return relatedResources;
    }
}
