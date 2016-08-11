package org.siemac.metamac.statistical.resources.core.common.mapper;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Annotation;
import org.siemac.metamac.statistical.resources.core.dto.constraint.AnnotationDto;

public interface CommonDto2RestMapper {

    public org.siemac.metamac.rest.common.v1_0.domain.InternationalString internationalStringDtoToRest(InternationalStringDto source,
            org.siemac.metamac.rest.common.v1_0.domain.InternationalString target, String metadataName) throws MetamacException;

    public Annotation annotationDtoToRest(AnnotationDto source, Annotation target, String metadataName) throws MetamacException;
}
