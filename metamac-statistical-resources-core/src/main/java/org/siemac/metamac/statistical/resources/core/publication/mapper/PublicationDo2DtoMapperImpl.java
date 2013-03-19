package org.siemac.metamac.statistical.resources.core.publication.mapper;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.springframework.stereotype.Component;

@Component("publicationDo2DtoMapper")
public class PublicationDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements PublicationDo2DtoMapper {

    @Override
    public PublicationDto publicationVersionDoToDto(PublicationVersion source) {
        if (source == null) {
            return null;
        }
        
        PublicationDto target = new PublicationDto();
        publicationVersionDtoToDto(source, target);
        return target;
    }

    @Override
    public List<PublicationDto> publicationVersionDoListToDtoList(List<PublicationVersion> sources) {
        List<PublicationDto> targets = new ArrayList<PublicationDto>();
        for (PublicationVersion source : sources) {
            targets.add(publicationVersionDoToDto(source));
        }
        return targets;
    }
    
    private PublicationDto publicationVersionDtoToDto(PublicationVersion source, PublicationDto target) {
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

}
