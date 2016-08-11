package org.siemac.metamac.statistical.resources.core.common.mapper;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Annotation;
import org.siemac.metamac.statistical.resources.core.dto.constraint.AnnotationDto;

@org.springframework.stereotype.Component("commonRest2DtoMapper")
public class CommonRest2DtoMapperImpl implements CommonRest2DtoMapper {

    @Override
    public InternationalStringDto internationalStringRestToDto(InternationalString source, InternationalStringDto target) throws MetamacException {
        if (source == null) {
            return null;
        }

        if (target == null) {
            target = new InternationalStringDto();
        }

        for (LocalisedString localisedString : source.getTexts()) {
            LocalisedStringDto localisedStringRestToDto = localisedStringRestToDto(localisedString);
            if (localisedStringRestToDto == null) {
                target.addText(localisedStringRestToDto);
            }
        }

        return target;
    }

    public AnnotationDto annotationRestToDto(Annotation source, AnnotationDto target) throws MetamacException {

        if (target == null) {
            target = new AnnotationDto();
        }

        target.setCode(source.getId());
        target.setTitle(source.getTitle());
        target.setType(source.getType());
        target.setUrl(source.getUrl());
        target.setText(internationalStringRestToDto(source.getText(), target.getText()));

        return target;
    }

    private LocalisedStringDto localisedStringRestToDto(LocalisedString source) throws MetamacException {
        if (source == null) {
            return null;
        }

        LocalisedStringDto target = new LocalisedStringDto();
        target.setLabel(source.getValue());
        target.setLocale(source.getLang());

        return target;
    }
}
