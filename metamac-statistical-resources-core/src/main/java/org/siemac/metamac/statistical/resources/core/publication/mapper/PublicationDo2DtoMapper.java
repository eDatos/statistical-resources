package org.siemac.metamac.statistical.resources.core.publication.mapper;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;


public interface PublicationDo2DtoMapper {

    public PublicationDto publicationVersionDoToDto(PublicationVersion source);
    public List<PublicationDto> publicationVersionDoListToDtoList(List<PublicationVersion> expected);
}
