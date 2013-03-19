package org.siemac.metamac.statistical.resources.core.publication.mapper;

import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.publication.exception.PublicationVersionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("publicationDto2DoMapper")
public class PublicationDto2DoMapperImpl extends BaseDto2DoMapperImpl implements PublicationDto2DoMapper {

    @Autowired
    private PublicationVersionRepository publicationVersionRepository;

    @Override
    public PublicationVersion publicationVersionDtoToDo(PublicationDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        PublicationVersion target = null;
        if (source.getId() == null) {
            target = new PublicationVersion();
            target.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        } else {
            try {
                target = publicationVersionRepository.findById(source.getId());
            } catch (PublicationVersionNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND)
                        .withMessageParameters(source.getUrn()).withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }

        datasetVersionDtoToDo(source, target);

        return target;

    }

    private PublicationVersion datasetVersionDtoToDo(PublicationDto source, PublicationVersion target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.PUBLICATION_VERSION).build();
        }
        
        // Hierarchy
        siemacMetadataStatisticalResourceDtoToDo(source, target.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE);
        
        // Other
        target.setFormatExtentResources(source.getFormatExtentResources());
        
        return target;
    }

}
