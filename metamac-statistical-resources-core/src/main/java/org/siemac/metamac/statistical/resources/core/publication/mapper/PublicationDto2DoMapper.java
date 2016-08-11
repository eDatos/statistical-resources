package org.siemac.metamac.statistical.resources.core.publication.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public interface PublicationDto2DoMapper extends BaseDto2DoMapper {

    public void checkOptimisticLocking(PublicationVersionBaseDto publicationVersionDto) throws MetamacException;

    public PublicationVersion publicationVersionDtoToDo(PublicationVersionDto source) throws MetamacException;

    // Structure
    public Chapter chapterDtoToDo(ChapterDto source) throws MetamacException;

    public Cube cubeDtoToDo(CubeDto source) throws MetamacException;

}
