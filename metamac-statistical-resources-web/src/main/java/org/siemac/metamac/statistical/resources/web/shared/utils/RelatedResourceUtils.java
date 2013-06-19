package org.siemac.metamac.statistical.resources.web.shared.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
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

    public static RelatedResourceDto getPublicationDtoAsRelatedResourceDto(PublicationDto publicationDto) {
        RelatedResourceDto relatedResourceDto = getNameableResourceDtoAsRelatedResourceDto(publicationDto);
        relatedResourceDto.setType(TypeRelatedResourceEnum.PUBLICATION_VERSION);
        return relatedResourceDto;
    }

    public static List<RelatedResourceDto> getPublicationDtosAsRelatedResourceDtos(List<PublicationDto> publicationDtos) {
        List<RelatedResourceDto> relatedResourceDtos = new ArrayList<RelatedResourceDto>(publicationDtos.size());
        for (PublicationDto publicationDto : publicationDtos) {
            relatedResourceDtos.add(getPublicationDtoAsRelatedResourceDto(publicationDto));
        }
        return relatedResourceDtos;
    }

    // -------------------------------------------------------------------------------------------------------------
    // DATASETS
    // -------------------------------------------------------------------------------------------------------------

    public static RelatedResourceDto getDatasetDtoAsRelatedResourceDto(DatasetDto datasetDto) {
        RelatedResourceDto relatedResourceDto = getNameableResourceDtoAsRelatedResourceDto(datasetDto);
        relatedResourceDto.setType(TypeRelatedResourceEnum.DATASET_VERSION);
        return relatedResourceDto;
    }

    public static List<RelatedResourceDto> getDatasetDtosAsRelatedResourceDtos(List<DatasetDto> datasetDtos) {
        List<RelatedResourceDto> relatedResourceDtos = new ArrayList<RelatedResourceDto>(datasetDtos.size());
        for (DatasetDto datasetDto : datasetDtos) {
            relatedResourceDtos.add(getDatasetDtoAsRelatedResourceDto(datasetDto));
        }
        return relatedResourceDtos;
    }
    
    public static String getRelatedResourceName(RelatedResourceDto relatedResourceDto) {
        return relatedResourceDto != null ? CommonWebUtils.getElementName(relatedResourceDto.getCode(), relatedResourceDto.getTitle()) : StringUtils.EMPTY;
    }

}
