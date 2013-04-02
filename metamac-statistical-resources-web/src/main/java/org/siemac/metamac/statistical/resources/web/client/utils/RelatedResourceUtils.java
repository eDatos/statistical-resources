package org.siemac.metamac.statistical.resources.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.model.record.RelatedResourceRecord;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;

public class RelatedResourceUtils {

    public static String getRelatedResourceName(RelatedResourceDto relatedResourceDto) {
        return relatedResourceDto != null ? CommonWebUtils.getElementName(relatedResourceDto.getCode(), relatedResourceDto.getTitle()) : StringUtils.EMPTY;
    }

    public static String getRelatedResourcesNames(List<RelatedResourceDto> relatedResourceDtos) {
        List<String> names = new ArrayList<String>(relatedResourceDtos.size());
        for (RelatedResourceDto relatedResourceDto : relatedResourceDtos) {
            names.add(getRelatedResourceName(relatedResourceDto));
        }
        return CommonWebUtils.getStringListToString(names);
    }

    public static RelatedResourceRecord getRelatedResourceRecord(RelatedResourceDto relatedResourceDto) {
        return new RelatedResourceRecord(relatedResourceDto.getCode(), relatedResourceDto.getUrn(), relatedResourceDto.getTitle(), relatedResourceDto);
    }

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
