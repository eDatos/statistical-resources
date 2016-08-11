package org.siemac.metamac.statistical.resources.core.base.mapper;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Annotation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Annotations;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.MaintainableArtefact;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDto2RestMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.constraint.AnnotationDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.MaintainableArtefactDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;

@org.springframework.stereotype.Component("baseDto2RestMapper")
public class BaseDto2RestMapperImpl extends CommonDto2RestMapperImpl implements BaseDto2RestMapper {

    @Override
    public void maintainableArtefactDtoToRest(MaintainableArtefactDto source, MaintainableArtefact target, String metadataName) throws MetamacException {
        // Hierarchy
        versionableArtefactDtoToRest(source, target, metadataName);

        // Metadata modifiable
        target.setIsExternalReference(BooleanUtils.toBoolean(source.getIsExternalReference()));
        target.setStructureUrl(source.getStructureURL());
        target.setServiceUrl(source.getServiceURL());
        target.setIsFinal(BooleanUtils.toBoolean(source.getFinalLogic()));

        // Related entities
        target.setAgencyID(source.getAgencyID());
    }

    private void versionableArtefactDtoToRest(MaintainableArtefactDto source, MaintainableArtefact target, String metadataName) throws MetamacException {
        // Hierarchy
        nameableArtefactDtoToRest(source, target, metadataName);

        target.setVersion(source.getVersionLogic());
        target.setResourceCreatedDate(source.getCreatedDate());
        // TODO ZEBEN target.setVersionCreatedDate(source.get)
        target.setValidFrom(source.getValidFrom());
        target.setValidTo(source.getValidTo());
        target.setReplaceToVersion(source.getReplaceToVersion());
        target.setReplacedByVersion(source.getReplacedByVersion());
    }

    private void nameableArtefactDtoToRest(MaintainableArtefactDto source, MaintainableArtefact target, String metadataName) throws MetamacException {
        // Hierarchy
        identifiableStatisticalResourceDtoToRest(source, target, metadataName);

        // Attributes modifiable
        target.setName(internationalStringDtoToRest(source.getName(), target.getName(), addParameter(metadataName, ServiceExceptionSingleParameters.TITLE)));
        target.setDescription(internationalStringDtoToRest(source.getDescription(), target.getDescription(), addParameter(metadataName, ServiceExceptionSingleParameters.DESCRIPTION)));
        target.setComment(internationalStringDtoToRest(source.getComment(), target.getComment(), addParameter(metadataName, ServiceExceptionSingleParameters.COMMENT)));
        // parameter
        // name
    }

    private void identifiableStatisticalResourceDtoToRest(MaintainableArtefactDto source, MaintainableArtefact target, String metadataName) throws MetamacException {
        annotableArtefactDtoToRest(source, target, metadataName);

        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setUri(source.getUriProvider());
    }

    private void annotableArtefactDtoToRest(MaintainableArtefactDto source, MaintainableArtefact target, String metadataName) throws MetamacException {

        Iterator<AnnotationDto> itAnnotationsSource = source.getAnnotations().iterator();
        List<Annotation> results = new ArrayList<Annotation>(source.getAnnotations().size());
        while (itAnnotationsSource.hasNext()) {
            AnnotationDto annotationDto = itAnnotationsSource.next();
            Annotation annotationRest = annotationDtoToRest(annotationDto, null, addParameter(metadataName, ServiceExceptionSingleParameters.ANNOTATION));
            results.add(annotationRest);
        }

        Annotations annotations = new Annotations();
        annotations.getAnnotations().addAll(results);
        target.setAnnotations(annotations);
    }

    public org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal externalItemToExternalItemRest(ExternalItemDto source,
            org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal target, String metadataName) throws MetamacException {

        if (target == null) {
            // New
            target = new org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal();
        }

        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setKind(source.getType().getValue());
        // Without managementAppUrl and selfLink

        target.setName(internationalStringDtoToRest(source.getTitle(), target.getName(), addParameter(metadataName, ServiceExceptionSingleParameters.NAME)));
        return target;
    }
}
