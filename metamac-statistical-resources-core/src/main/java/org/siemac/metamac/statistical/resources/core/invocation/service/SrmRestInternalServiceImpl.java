package org.siemac.metamac.statistical.resources.core.invocation.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.api.constants.RestApiConstants;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agency;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategorySchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Code;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelists;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Organisations;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalServiceImpl implements SrmRestInternalService {

    @Autowired
    private MetamacApisLocator restApiLocator;

    // -------------------------------------------------------------------------------------------------
    // DSD
    // -------------------------------------------------------------------------------------------------
    
    @Override
    public DataStructure retrieveDsdByUrn(String urn) throws MetamacException {
        try {
            String[] dataStructureComponents = GeneratorUrnUtils.extractSdmxDatastructurUrnComponents(urn);
            String agencyId = dataStructureComponents[0];
            String dsdId = dataStructureComponents[1];
            String version = dataStructureComponents[2];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveDataStructure(agencyId, dsdId, version);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public DataStructures findDsds(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            return restApiLocator.getSrmRestInternalFacadeV10().findDataStructures(query, null, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findDsdsUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            DataStructures dsds = findDsds(firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : dsds.getDataStructures()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }
    
//    @Override
//    public List<ResourceInternal> findDsds(String urn) {
//        try {
//            Integer limit = RestApiConstants.MAXIMUM_LIMIT;
//            int offset = 0;
//            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
//            Concepts concepts = null;
//            do {
//                concepts = restApiLocator.getSrmRestInternalFacadeV10().findConcepts(agencyID, resourceID, version, null, null, limit, String.valueOf(offset));
//                results.addAll(concepts.getConcepts());
//                offset += concepts.getConcepts().size(); // next page
//            } while (concepts.getTotal().intValue() != results.size());
//            return results;
//        } catch (Exception e) {
//            throw manageSrmInternalRestException(e);
//        }
//    }

    // -------------------------------------------------------------------------------------------------
    // CONCEPT SCHEMES
    // -------------------------------------------------------------------------------------------------

    @Override
    public ConceptSchemes findConceptSchemes(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getSrmRestInternalFacadeV10().findConceptSchemes(query, orderBy, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findConceptSchemesUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            ConceptSchemes conceptSchemes = findConceptSchemes(firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : conceptSchemes.getConceptSchemes()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public ConceptScheme retrieveConceptSchemeByUrn(String urn) throws MetamacException {
        try {
            String[] params = UrnUtils.splitUrnItemScheme(urn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveConceptScheme(agencyId, resourceId, version);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------
    // CONCEPTS
    // -------------------------------------------------------------------------------------------------

    @Override
    public Concept retrieveConceptByUrn(String urn) throws MetamacException {
        try {
            String[] params = UrnUtils.splitUrnItem(urn);
            String agencyId = params[0];
            String schemeId = params[1];
            String version = params[2];
            String conceptId = params[3];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveConcept(agencyId, schemeId, version, conceptId);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public Concepts findConcepts(String conceptSchemeUrn, int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String agencyId = null;
            String resourceId = null;
            String version = null;

            if (StringUtils.isBlank(conceptSchemeUrn)) {
                agencyId = RestApiConstants.WILDCARD_ALL;
                resourceId = RestApiConstants.WILDCARD_ALL;
                version = RestApiConstants.WILDCARD_ALL;
            } else {
                String[] params = UrnUtils.splitUrnItemScheme(conceptSchemeUrn);
                agencyId = params[0];
                resourceId = params[1];
                version = params[2];
            }
            return restApiLocator.getSrmRestInternalFacadeV10().findConcepts(agencyId, resourceId, version, query, null, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public Concepts retrieveConceptsOfConceptSchemeEfficiently(String conceptSchemeUrn) throws MetamacException {
        if (StringUtils.isBlank(conceptSchemeUrn)) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.CONCEPT_SCHEME_URN);
        }

        try {
            String[] params = UrnUtils.splitUrnItemScheme(conceptSchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            return restApiLocator.getSrmRestInternalFacadeV10().findConcepts(agencyId, resourceId, version, null, null, null, null);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findConceptsUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            Concepts concepts = findConcepts(null, firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : concepts.getConcepts()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    
    // -------------------------------------------------------------------------------------------------
    // CODELISTS
    // -------------------------------------------------------------------------------------------------

    @Override
    public Codelists findCodelists(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getSrmRestInternalFacadeV10().findCodelists(query, orderBy, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findCodelistsAsUrnsList(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            Codelists codelists = findCodelists(firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : codelists.getCodelists()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public Codelist retrieveCodelistByUrn(String urn) throws MetamacException {
        try {
            String[] params = UrnUtils.splitUrnItemScheme(urn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveCodelist(agencyId, resourceId, version);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------
    // CODES
    // -------------------------------------------------------------------------------------------------
    
    @Override
    public Codes retrieveCodesOfCodelistEfficiently(String codelistUrn) throws MetamacException {
        if (StringUtils.isBlank(codelistUrn)) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.CODELIST_URN);
        }

        try {
            String[] params = UrnUtils.splitUrnItemScheme(codelistUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            return restApiLocator.getSrmRestInternalFacadeV10().findCodes(agencyId, resourceId, version, null, null, null, null, null, null);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }
    
    @Override
    public Codes findCodes(String codelistUrn, int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String agencyId = null;
            String resourceId = null;
            String version = null;

            if (StringUtils.isBlank(codelistUrn)) {
                agencyId = RestApiConstants.WILDCARD_ALL;
                resourceId = RestApiConstants.WILDCARD_ALL;
                version = RestApiConstants.WILDCARD_ALL;
            } else {
                String[] params = UrnUtils.splitUrnItemScheme(codelistUrn);
                agencyId = params[0];
                resourceId = params[1];
                version = params[2];
            }
            return restApiLocator.getSrmRestInternalFacadeV10().findCodes(agencyId, resourceId, version, query, null, limit, offset, null, null);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findCodesUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            Codes codes = findCodes(null, firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : codes.getCodes()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public Code retrieveCodeByUrn(String urn) throws MetamacException {
        try {
            String[] params = UrnUtils.splitUrnItem(urn);
            String agencyId = params[0];
            String schemeId = params[1];
            String version = params[2];
            String conceptId = params[3];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveCode(agencyId, schemeId, version, conceptId);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------
    // ORGANISATION SCHEMES
    // -------------------------------------------------------------------------------------------------

    @Override
    public OrganisationSchemes findOrganisationSchemes(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getSrmRestInternalFacadeV10().findOrganisationSchemes(query, orderBy, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findOrganisationSchemesUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            OrganisationSchemes organisationSchemes = findOrganisationSchemes(firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : organisationSchemes.getOrganisationSchemes()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------
    // ORGANISATIONS
    // -------------------------------------------------------------------------------------------------
    @Override
    public Organisations retrieveOrganisationsOfOrganisationSchemeEfficiently(String organisationSchemeUrn) throws MetamacException {
        if (StringUtils.isBlank(organisationSchemeUrn)) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.ORGANISATION_SCHEME_URN);
        }

        try {
            String[] params = UrnUtils.splitUrnItemScheme(organisationSchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            return restApiLocator.getSrmRestInternalFacadeV10().findOrganisations(agencyId, resourceId, version, null, null, null, null);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }
    
    
    @Override
    public Organisations findOrganisations(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getSrmRestInternalFacadeV10().findOrganisations(RestApiConstants.WILDCARD_ALL, RestApiConstants.WILDCARD_ALL, RestApiConstants.WILDCARD_ALL, query, orderBy, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findOrganisationsUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            Organisations organisations = findOrganisations(firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : organisations.getOrganisations()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------
    // CATEGORY SCHEMES
    // -------------------------------------------------------------------------------------------------

    @Override
    public CategorySchemes findCategorySchemes(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getSrmRestInternalFacadeV10().findCategorySchemes(query, orderBy, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findCategorySchemesUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            CategorySchemes categorySchemes = findCategorySchemes(firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : categorySchemes.getCategorySchemes()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    
    // -------------------------------------------------------------------------------------------------
    // CATEGORIES
    // -------------------------------------------------------------------------------------------------
    @Override
    public Categories retrieveCategoriesOfCategorySchemeEfficiently(String categorySchemeUrn) throws MetamacException {
        if (StringUtils.isBlank(categorySchemeUrn)) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.CATEGORY_SCHEME_URN);
        }

        try {
            String[] params = UrnUtils.splitUrnItemScheme(categorySchemeUrn);
            String agencyId = params[0];
            String resourceId = params[1];
            String version = params[2];
            return restApiLocator.getSrmRestInternalFacadeV10().findCategories(agencyId, resourceId, version, null, null, null, null);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }
    
    @Override
    public Categories findCategories(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getSrmRestInternalFacadeV10().findCategories(RestApiConstants.WILDCARD_ALL, RestApiConstants.WILDCARD_ALL, RestApiConstants.WILDCARD_ALL, query, orderBy, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findCategoriesUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            Categories categories = findCategories(firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : categories.getCategories()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public Agency retrieveAgencyByUrn(String agencyUrn) throws MetamacException {
        try {
            String[] params = UrnUtils.splitUrnItem(agencyUrn);
            String agencyId = params[0];
            String schemeId = params[1];
            String version = params[2];
            String organisationId = params[3];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveAgency(agencyId, schemeId, version, organisationId);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    
    // -------------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------------
    
    private MetamacException manageSrmInternalRestException(Exception e) throws MetamacException {
        return ServiceExceptionUtils.manageMetamacRestException(e, ServiceExceptionParameters.API_SRM_INTERNAL, restApiLocator.getSrmRestInternalFacadeV10());
    }
}
