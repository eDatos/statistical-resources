package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebRestCriteriaUtils.buildQueryCode;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebRestCriteriaUtils.buildQueryConcept;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebRestCriteriaUtils.buildQueryConceptScheme;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebRestCriteriaUtils.buildQueryDsd;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebRestCriteriaUtils.buildQueryOrganisation;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebRestCriteriaUtils.buildQueryOrganisationScheme;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agency;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attributes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Code;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.MeasureDimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Organisations;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TimeDimension;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.web.server.utils.ExternalItemUtils;
import org.siemac.metamac.statistical.resources.web.shared.DTO.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
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

    // DSDs

    @Override
    public ExternalItemsResult findDsds(int firstResult, int maxResult, DsdWebCriteria criteria) throws MetamacWebException {
        try {
            String query = buildQueryDsd(criteria);

            DataStructures structures = srmRestInternalService.findDsds(firstResult, maxResult, query);

            List<ExternalItemDto> dsdsExternalItems = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : structures.getDataStructures()) {
                dsdsExternalItems.add(ExternalItemUtils.buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.DATASTRUCTURE));
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
            Dimensions dimensionsList = structure.getDataStructureComponents().getDimensions();

            List<String> dimensions = new ArrayList<String>();
            for (DimensionBase dimObj : dimensionsList.getDimensions()) {
                if (dimObj instanceof Dimension) {
                    Dimension dim = (Dimension) dimObj;
                    dimensions.add(dim.getId());
                } else if (dimObj instanceof MeasureDimension) {
                    MeasureDimension dim = (MeasureDimension) dimObj;
                    dimensions.add(dim.getId());
                } else if (dimObj instanceof TimeDimension) {
                    TimeDimension dim = (TimeDimension) dimObj;
                    dimensions.add(dim.getId());
                }
            }
            return dimensions;
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public List<DsdAttributeDto> retrieveDsdAttributes(String dsdUrn) throws MetamacWebException {
        try {

            List<DsdAttributeDto> dsdAttributeDtos = new ArrayList<DsdAttributeDto>();

            DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(dsdUrn);
            Attributes attributes = dataStructure.getDataStructureComponents().getAttributes();

            if (attributes != null && attributes.getAttributes() != null) {

                for (AttributeBase attributeBase : attributes.getAttributes()) {
                    if (attributeBase instanceof Attribute) {
                        DsdAttributeDto dsdAttributeDto = RestMapper.buildDsdAttributeDtoFromAttribute((Attribute) attributeBase);
                        dsdAttributeDtos.add(dsdAttributeDto);
                    }
                }
            }

            return dsdAttributeDtos;
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    // CODES

    @Override
    public ExternalItemsResult findCodesInCodelist(String codeListUrn, int firstResult, int maxResult, MetamacWebCriteria criteria) throws MetamacWebException {
        try {
            String query = buildQueryCode(criteria);

            Codes codes = srmRestInternalService.findCodes(codeListUrn, firstResult, maxResult, query);

            List<ExternalItemDto> codesExternalItems = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : codes.getCodes()) {
                codesExternalItems.add(ExternalItemUtils.buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.CODE));
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
            return ExternalItemUtils.buildExternalItemDtoFromCode(code);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    // CONCEPTS

    @Override
    public ExternalItemsResult findConceptSchemes(int firstResult, int maxResult, MetamacWebCriteria criteria) throws MetamacWebException {
        try {
            String query = buildQueryConceptScheme(criteria);

            ConceptSchemes conceptSchemes = srmRestInternalService.findConceptSchemes(firstResult, maxResult, query);

            List<ExternalItemDto> conceptSchemesExternalItems = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : conceptSchemes.getConceptSchemes()) {
                conceptSchemesExternalItems.add(ExternalItemUtils.buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.CONCEPT_SCHEME));
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
                conceptsExternalItems.add(ExternalItemUtils.buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.CONCEPT));
            }
            return ExternalItemUtils.createExternalItemsResultFromListBase(concepts, conceptsExternalItems);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    // ORGANISATIONS

    @Override
    public ExternalItemsResult findOrganisationSchemes(int firstResult, int maxResult, MetamacWebCriteria criteria, TypeExternalArtefactsEnum type) throws MetamacWebException {
        try {
            String query = buildQueryOrganisationScheme(criteria, type);

            OrganisationSchemes schemes = srmRestInternalService.findOrganisationSchemes(firstResult, maxResult, query);

            List<ExternalItemDto> items = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : schemes.getOrganisationSchemes()) {
                items.add(ExternalItemUtils.buildExternalItemDtoFromResource(resource, type));
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
                items.add(ExternalItemUtils.buildExternalItemDtoFromResource(resource, type));
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
            return ExternalItemUtils.buildExternalItemDtoFromAgency(agency);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}