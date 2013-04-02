package org.siemac.metamac.statistical.resources.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;

public class RelatedResourceUtils {

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
    // PUBLICATIONS
    // -------------------------------------------------------------------------------------------------------------

    public static RelatedResourceDto getPublicationDtoAsRelatedResourceDto(PublicationDto publicationDto) {
        RelatedResourceDto relatedResourceDto = getIdentifiableResourceDtoAsRelatedResourceDto(publicationDto);
        relatedResourceDto.setTitle(publicationDto.getTitle());
        return relatedResourceDto;
    }

    public static List<RelatedResourceDto> getPublicationDtosAsRelatedResourceDtos(List<PublicationDto> publicationDtos) {
        List<RelatedResourceDto> relatedResourceDtos = new ArrayList<RelatedResourceDto>(publicationDtos.size());
        for (PublicationDto publicationDto : publicationDtos) {
            relatedResourceDtos.add(getPublicationDtoAsRelatedResourceDto(publicationDto));
        }
        return relatedResourceDtos;
    }
}
