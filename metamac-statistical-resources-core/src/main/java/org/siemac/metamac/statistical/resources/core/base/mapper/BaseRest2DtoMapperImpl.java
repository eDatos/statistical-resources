package org.siemac.metamac.statistical.resources.core.base.mapper;

import java.util.Iterator;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Annotation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.MaintainableArtefact;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonRest2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.constraint.AnnotationDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.MaintainableArtefactDto;

@org.springframework.stereotype.Component("baseRest2DtoMapper")
public class BaseRest2DtoMapperImpl extends CommonRest2DtoMapperImpl implements BaseRest2DtoMapper {

    @Override
    public void maintainableArtefactRestToDto(MaintainableArtefact source, MaintainableArtefactDto target) throws MetamacException {
        // Hierarchy
        versionableArtefactRestToDto(source, target);

        // Metadata
        target.setIsExternalReference(source.isIsExternalReference());
        target.setStructureURL(source.getStructureUrl());
        target.setStructureURL(source.getServiceUrl());

        target.setFinalLogic(source.isIsFinal());

        target.setAgencyID(source.getAgencyID());
    }

    private void versionableArtefactRestToDto(MaintainableArtefact source, MaintainableArtefactDto target) throws MetamacException {
        // Hierarchy
        nameableArtefactRestToDto(source, target);

        target.setVersionLogic(source.getVersion());
        target.setCreatedDate(source.getResourceCreatedDate());
        // TODO ZEBEN target.setVersionCreatedDate(source.get)
        target.setValidFrom(source.getValidFrom());
        target.setValidTo(source.getValidTo());
        target.setReplaceToVersion(source.getReplaceToVersion());
        target.setReplacedByVersion(source.getReplacedByVersion());
    }

    private void nameableArtefactRestToDto(MaintainableArtefact source, MaintainableArtefactDto target) throws MetamacException {
        // Hierarchy
        identifiableStatisticalResourceRestToDto(source, target);

        // Attributes modifiable
        target.setName(internationalStringRestToDto(source.getName(), target.getName()));
        target.setDescription(internationalStringRestToDto(source.getDescription(), target.getDescription()));
        target.setComment(internationalStringRestToDto(source.getComment(), target.getComment()));
    }

    private void identifiableStatisticalResourceRestToDto(MaintainableArtefact source, MaintainableArtefactDto target) throws MetamacException {
        annotableArtefactRestToDto(source, target);

        target.setCode(source.getId());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setUriProvider(source.getUri());
    }

    private void annotableArtefactRestToDto(MaintainableArtefact source, MaintainableArtefactDto target) throws MetamacException {

        if (source.getAnnotations() == null) {
            return;
        }

        Iterator<Annotation> itAnnotation = source.getAnnotations().getAnnotations().iterator();
        while (itAnnotation.hasNext()) {
            Annotation annotation = itAnnotation.next();
            AnnotationDto annotationDto = annotationRestToDto(annotation, null);
            if (annotation != null) {
                target.getAnnotations().add(annotationDto);
            }
        }
    }

    @Override
    public ExternalItemDto externalItemToExternalItemDto(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal source, ExternalItemDto target) throws MetamacException {

        if (target == null) {
            // New
            target = new ExternalItemDto();
        }

        target.setCode(source.getId());
        target.setCodeNested(source.getNestedId());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(TypeExternalArtefactsEnum.fromValue(source.getKind()));

        // Without managementAppUrl and selfLink

        target.setTitle(internationalStringRestToDto(source.getName(), target.getTitle()));

        return target;
    }

}
