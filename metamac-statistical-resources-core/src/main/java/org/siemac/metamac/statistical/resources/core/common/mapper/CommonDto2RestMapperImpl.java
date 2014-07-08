package org.siemac.metamac.statistical.resources.core.common.mapper;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Annotation;
import org.siemac.metamac.statistical.resources.core.dto.constraint.AnnotationDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

@org.springframework.stereotype.Component("commonDto2RestMapper")
public class CommonDto2RestMapperImpl implements CommonDto2RestMapper {

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------
    @Override
    public org.siemac.metamac.rest.common.v1_0.domain.InternationalString internationalStringDtoToRest(InternationalStringDto source,
            org.siemac.metamac.rest.common.v1_0.domain.InternationalString target, String metadataName) throws MetamacException {
        // Check it is valid
        // checkInternationalStringDtoValid(source, metadataName);

        // Transform
        if (ValidationUtils.isEmpty(source)) {
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, metadataName);
        }

        if (target == null) {
            target = new org.siemac.metamac.rest.common.v1_0.domain.InternationalString();
        }

        List<org.siemac.metamac.rest.common.v1_0.domain.LocalisedString> localisedStringEntities = localisedStringDtoToRest(source.getTexts(), target.getTexts(), target);
        target.getTexts().clear();
        target.getTexts().addAll(localisedStringEntities);

        return target;
    }

    @Override
    public Annotation annotationDtoToRest(AnnotationDto source, Annotation target, String metadataName) throws MetamacException {

        if (target == null) {
            target = new Annotation();
        }

        target.setId(source.getCode());
        target.setTitle(source.getTitle());
        target.setType(source.getType());
        target.setUrl(source.getUrl());
        target.setText(internationalStringDtoToRest(source.getText(), target.getText(), addParameter(metadataName, ServiceExceptionParameters.TEXT)));

        return target;
    }

    private List<org.siemac.metamac.rest.common.v1_0.domain.LocalisedString> localisedStringDtoToRest(Set<LocalisedStringDto> sources,
            List<org.siemac.metamac.rest.common.v1_0.domain.LocalisedString> targets, org.siemac.metamac.rest.common.v1_0.domain.InternationalString internationalStringTarget) {

        List<org.siemac.metamac.rest.common.v1_0.domain.LocalisedString> targetsBefore = targets;
        targets = new LinkedList<org.siemac.metamac.rest.common.v1_0.domain.LocalisedString>();

        for (LocalisedStringDto source : sources) {
            boolean existsBefore = false;
            for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString target : targetsBefore) {
                if (source.getLocale().equals(target.getLang())) {
                    targets.add(localisedStringDtoToRest(source, target, internationalStringTarget));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                targets.add(localisedStringDtoToRest(source, internationalStringTarget));
            }
        }
        return targets;
    }

    private org.siemac.metamac.rest.common.v1_0.domain.LocalisedString localisedStringDtoToRest(LocalisedStringDto source,
            org.siemac.metamac.rest.common.v1_0.domain.InternationalString internationalStringTarget) {
        org.siemac.metamac.rest.common.v1_0.domain.LocalisedString target = new org.siemac.metamac.rest.common.v1_0.domain.LocalisedString();
        localisedStringDtoToRest(source, target, internationalStringTarget);
        return target;
    }

    private org.siemac.metamac.rest.common.v1_0.domain.LocalisedString localisedStringDtoToRest(LocalisedStringDto source, org.siemac.metamac.rest.common.v1_0.domain.LocalisedString target,
            org.siemac.metamac.rest.common.v1_0.domain.InternationalString internationalStringTarget) {
        target.setValue(source.getLabel());
        target.setValue(source.getLocale());
        return target;
    }

}
