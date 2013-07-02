package org.siemac.metamac.statistical.resources.core.publication.mapper;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.springframework.stereotype.Component;

@Component("publicationDo2DtoMapper")
public class PublicationDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements PublicationDo2DtoMapper {

    // --------------------------------------------------------------------------------------
    // PUBLICATION VERSION
    // --------------------------------------------------------------------------------------
    
    @Override
    public PublicationDto publicationVersionDoToDto(PublicationVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        
        PublicationDto target = new PublicationDto();
        publicationVersionDoToDto(source, target);
        return target;
    }

    @Override
    public List<PublicationDto> publicationVersionDoListToDtoList(List<PublicationVersion> sources) throws MetamacException {
        List<PublicationDto> targets = new ArrayList<PublicationDto>();
        for (PublicationVersion source : sources) {
            targets.add(publicationVersionDoToDto(source));
        }
        return targets;
    }
    
    private PublicationDto publicationVersionDoToDto(PublicationVersion source, PublicationDto target) throws MetamacException {
        if (source == null) {
            return null;
        }
        
        // Hierarchy
        siemacMetadataStatisticalResourceDoToDto(source.getSiemacMetadataStatisticalResource(), target);
        
        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());
        
        // Other
        target.setFormatExtentResources(source.getFormatExtentResources());
        return target;
    }

    // --------------------------------------------------------------------------------------
    // CHAPTER
    // --------------------------------------------------------------------------------------
    
    @Override
    public ChapterDto chapterDoToDto(Chapter source) throws MetamacException {
        if (source == null) {
            return null;
        }
        
        ChapterDto target = new ChapterDto();
        chapterDoToDto(source, target);
        return target;
    }
    

    private ChapterDto chapterDoToDto(Chapter source, ChapterDto target) {
        if (source == null) {
            return null;
        }
        
        // Hierarchy
        nameableStatisticalResourceDoToDto(source.getNameableStatisticalResource(), target);
        
        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());
        
        // Other
        target.setParentChapterUrn(source.getElementLevel().getParentUrn());
        target.setOrderInLevel(source.getElementLevel().getOrderInLevel());
        return target;
    }

    // --------------------------------------------------------------------------------------
    // CUBE
    // --------------------------------------------------------------------------------------
    @Override
    public CubeDto cubeDoToDto(Cube source) throws MetamacException {
        if (source == null) {
            return null;
        }
        
        CubeDto target = new CubeDto();
        cubeDoToDto(source, target);
        return target;
    }
    
    private CubeDto cubeDoToDto(Cube source, CubeDto target) {
        if (source == null) {
            return null;
        }
        
        // Hierarchy
        nameableStatisticalResourceDoToDto(source.getNameableStatisticalResource(), target);
        
        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());
        
        // Other
        target.setParentChapterUrn(source.getElementLevel().getParentUrn());
        target.setOrderInLevel(source.getElementLevel().getOrderInLevel());
        target.setQueryUrn(source.getQueryUrn());
        target.setDatasetUrn(source.getDatasetUrn());
        return target;
    }

    // --------------------------------------------------------------------------------------
    // ELEMENT LEVELS
    // --------------------------------------------------------------------------------------
    
    @Override
    public List<ElementLevelDto> elementsLevelDoListToDtoList(List<ElementLevel> sources) throws MetamacException {
        List<ElementLevelDto> targets = new ArrayList<ElementLevelDto>();
        for (ElementLevel source : sources) {
            targets.add(elementLevelDoToDto(source));
        }
        return targets;
    }

    private ElementLevelDto elementLevelDoToDto(ElementLevel source) throws MetamacException {
        ElementLevelDto target = new ElementLevelDto();
        if (source.getChapter() != null) {
            target.setChapter(chapterDoToDto(source.getChapter()));
        } else if (source.getCube() != null) {
            target.setCube(cubeDoToDto(source.getCube()));
        }
        
        for (ElementLevel child : source.getChildren()) {
            target.addSubelement(elementLevelDoToDto(child));
        }
        return target;
    }
}
