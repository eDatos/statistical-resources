package org.siemac.metamac.statistical.resources.core.publication.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;


public interface PublicationDto2DoMapper {
    public PublicationVersion publicationVersionDtoToDo(PublicationDto source) throws MetamacException;
}
