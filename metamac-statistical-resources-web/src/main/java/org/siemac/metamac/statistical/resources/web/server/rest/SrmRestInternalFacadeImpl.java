package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebCriteriaUtils.buildQueryCode;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebCriteriaUtils.buildQueryConcept;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebCriteriaUtils.buildQueryConceptScheme;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebCriteriaUtils.buildQueryDsd;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebCriteriaUtils.buildQueryOrganisation;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebCriteriaUtils.buildQueryOrganisationScheme;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agency;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Code;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Organisations;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.web.server.utils.ExternalItemUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    @Autowired
    private SrmRestInternalService srmRestInternalService;

    @Override
    public ExternalItemsResult findDsds(int firstResult, int maxResult, DsdWebCriteria criteria) throws MetamacWebException {
        try {
            String query = buildQueryDsd(criteria);

            DataStructures structures = srmRestInternalService.findDsds(firstResult, maxResult, query);

            List<ExternalItemDto> dsdsExternalItems = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : structures.getDataStructures()) {
                dsdsExternalItems.add(buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.DATASTRUCTURE));
            }
            return ExternalItemUtils.createExternalItemsResultFromListBase(structures, dsdsExternalItems);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public List<String> retrieveDsdDimensionsIds(String dsdUrn) throws MetamacWebException {
        try {
            DataStructure structure = srmRestInternalService.retrieveDsdByUrn(dsdUrn);
            DimensionListType dimensionsList = structure.getDataStructureComponents().getDimensionList();

            List<String> dimensions = new ArrayList<String>();
            for (Object dimObj : dimensionsList.getDimensionsAndMeasureDimensionsAndTimeDimensions()) {
                if (dimObj instanceof DimensionType) {
                    DimensionType dim = (DimensionType) dimObj;
                    dimensions.add(dim.getId());
                } else if (dimObj instanceof MeasureDimensionType) {
                    MeasureDimensionType dim = (MeasureDimensionType) dimObj;
                    dimensions.add(dim.getId());
                } else if (dimObj instanceof TimeDimensionType) {
                    TimeDimensionType dim = (TimeDimensionType) dimObj;
                    dimensions.add(dim.getId());
                }
            }
            return dimensions;
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemsResult findCodesInCodelist(String codeListUrn, int firstResult, int maxResult, MetamacWebCriteria criteria) throws MetamacWebException {
        try {
            String query = buildQueryCode(criteria);

            Codes codes = srmRestInternalService.findCodes(codeListUrn, firstResult, maxResult, query);

            List<ExternalItemDto> codesExternalItems = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : codes.getCodes()) {
                codesExternalItems.add(buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.CODE));
            }
            return ExternalItemUtils.createExternalItemsResultFromListBase(codes, codesExternalItems);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemDto retrieveCodeByUrn(String urn) throws MetamacWebException {
        try {
            Code code = srmRestInternalService.retrieveCodeByUrn(urn);
            return buildExternalItemDtoFromCode(code);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemsResult findConceptSchemes(int firstResult, int maxResult, MetamacWebCriteria criteria) throws MetamacWebException {
        try {
            String query = buildQueryConceptScheme(criteria);

            ConceptSchemes conceptSchemes = srmRestInternalService.findConceptSchemes(firstResult, maxResult, query);

            List<ExternalItemDto> conceptSchemesExternalItems = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : conceptSchemes.getConceptSchemes()) {
                conceptSchemesExternalItems.add(buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.CONCEPT_SCHEME));
            }
            return ExternalItemUtils.createExternalItemsResultFromListBase(conceptSchemes, conceptSchemesExternalItems);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemsResult findConcepts(int firstResult, int maxResult, ItemSchemeWebCriteria condition) throws MetamacWebException {
        try {
            String query = null;
            String conceptSchemeUrn = null;
            if (condition != null) {
                query = buildQueryConcept(condition);
                conceptSchemeUrn = condition.getSchemeUrn();
            }
            Concepts concepts = srmRestInternalService.findConcepts(conceptSchemeUrn, firstResult, maxResult, query);

            List<ExternalItemDto> conceptsExternalItems = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : concepts.getConcepts()) {
                conceptsExternalItems.add(buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.CONCEPT));
            }
            return ExternalItemUtils.createExternalItemsResultFromListBase(concepts, conceptsExternalItems);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemsResult findOrganisationSchemes(int firstResult, int maxResult, MetamacWebCriteria criteria, TypeExternalArtefactsEnum type) throws MetamacWebException {
        try {
            String query = buildQueryOrganisationScheme(criteria, type);

            OrganisationSchemes schemes = srmRestInternalService.findOrganisationSchemes(firstResult, maxResult, query);

            List<ExternalItemDto> items = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : schemes.getOrganisationSchemes()) {
                items.add(buildExternalItemDtoFromResource(resource, type));
            }
            return ExternalItemUtils.createExternalItemsResultFromListBase(schemes, items);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemsResult findOrganisations(int firstResult, int maxResult, ItemSchemeWebCriteria criteria, TypeExternalArtefactsEnum type) throws MetamacWebException {
        try {
            String query = buildQueryOrganisation(criteria, type);

            Organisations organizations = srmRestInternalService.findOrganisations(firstResult, maxResult, query);

            List<ExternalItemDto> items = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : organizations.getOrganisations()) {
                items.add(buildExternalItemDtoFromResource(resource, type));
            }
            return ExternalItemUtils.createExternalItemsResultFromListBase(organizations, items);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemDto retrieveAgencyByUrn(String agencyUrn) throws MetamacWebException {
        try {
            Agency agency = srmRestInternalService.retrieveAgencyByUrn(agencyUrn);
            return buildExternalItemDtoFromAgency(agency);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    private ExternalItemDto buildExternalItemDtoFromResource(ResourceInternal resource, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(resource.getId());
        externalItemDto.setCodeNested(resource.getNestedId());
        externalItemDto.setUri(resource.getSelfLink().getHref());
        externalItemDto.setUrn(resource.getUrn());
        externalItemDto.setUrnProvider(resource.getUrnProvider());
        externalItemDto.setType(type);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resource.getName()));
        externalItemDto.setManagementAppUrl(resource.getManagementAppLink());
        return externalItemDto;
    }

    private ExternalItemDto buildExternalItemDtoFromAgency(Agency agency) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(agency.getId());
        externalItemDto.setCodeNested(agency.getNestedId());
        externalItemDto.setUri(agency.getSelfLink().getHref());
        externalItemDto.setUrn(agency.getUrn());
        externalItemDto.setUrnProvider(agency.getUrnProvider());
        externalItemDto.setType(TypeExternalArtefactsEnum.AGENCY);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(agency.getName()));
        externalItemDto.setManagementAppUrl(agency.getManagementAppLink());
        return externalItemDto;
    }

    private ExternalItemDto buildExternalItemDtoFromCode(Code code) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(code.getId());
        externalItemDto.setUri(code.getSelfLink().getHref());
        externalItemDto.setUrn(code.getUrn());
        externalItemDto.setUrnProvider(code.getUrnProvider());
        externalItemDto.setType(TypeExternalArtefactsEnum.CODE);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(code.getName()));
        externalItemDto.setManagementAppUrl(code.getManagementAppLink());
        return externalItemDto;
    }

    protected InternationalStringDto toInternationalStringDto(List<TextType> sources) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        InternationalStringDto targets = new InternationalStringDto();
        for (TextType source : sources) {
            LocalisedStringDto target = new LocalisedStringDto();
            target.setLocale(source.getLang());
            target.setLabel(source.getValue());
            targets.getTexts().add(target);
        }
        return targets;
    }
}