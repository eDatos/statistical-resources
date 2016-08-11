package org.siemac.metamac.statistical.resources.core.publication.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public interface PublicationDo2DtoMapper extends BaseDo2DtoMapper {

    // Publication
    public RelatedResourceDto publicationVersionDoToPublicationRelatedResourceDto(PublicationVersion source) throws MetamacException;

    // Publication Version
    public RelatedResourceDto publicationVersionDoToPublicationVersionRelatedResourceDto(PublicationVersion source);
    public PublicationVersionDto publicationVersionDoToDto(PublicationVersion source) throws MetamacException;
    public PublicationVersionBaseDto publicationVersionDoToBaseDto(PublicationVersion source) throws MetamacException;
    public List<PublicationVersionBaseDto> publicationVersionDoListToDtoList(List<PublicationVersion> expected) throws MetamacException;

    // Publication structure
    public PublicationStructureDto publicationVersionStructureDoToDto(PublicationVersion publicationVersion) throws MetamacException;
    public ChapterDto chapterDoToDto(Chapter source) throws MetamacException;
    public CubeDto cubeDoToDto(Cube cube) throws MetamacException;
    public List<ElementLevelDto> elementsLevelDoListToDtoList(List<ElementLevel> sources) throws MetamacException;
}
