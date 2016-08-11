package org.siemac.metamac.statistical.resources.core.common.mapper;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Annotation;
import org.siemac.metamac.statistical.resources.core.dto.constraint.AnnotationDto;

public interface CommonRest2DtoMapper {

    public InternationalStringDto internationalStringRestToDto(InternationalString source, InternationalStringDto target) throws MetamacException;

    public AnnotationDto annotationRestToDto(Annotation source, AnnotationDto target) throws MetamacException;
}
