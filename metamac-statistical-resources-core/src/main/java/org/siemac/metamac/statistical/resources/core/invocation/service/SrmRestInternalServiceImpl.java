package org.siemac.metamac.statistical.resources.core.invocation.service;

import static org.siemac.metamac.rest.api.constants.RestApiConstants.DEFAULT_OFFSET;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.MAXIMUM_LIMIT;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.api.constants.RestApiConstants;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agency;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
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

@Component(SrmRestInternalService.BEAN_ID)
public class SrmRestInternalServiceImpl implements SrmRestInternalService {

    @Autowired
    private MetamacApisLocator restApiLocator;

    // -------------------------------------------------------------------------------------------------
    // DSD
    // -------------------------------------------------------------------------------------------------

    @Override
    public DataStructure retrieveDsdByUrn(String urn) throws MetamacException {
        try {
            String[] dataStructureComponents = GeneratorUrnUtils.extractVersionableArtefactParts(urn);
            String agencyId = dataStructureComponents[0];
            String dsdId = dataStructureComponents[1];
            String version = dataStructureComponents[2];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveDataStructure(agencyId, dsdId, version);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    private DataStructures findDsds(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String offset = String.valueOf(firstResult);
            String limit = String.valueOf(maxResult);
            return restApiLocator.getSrmRestInternalFacadeV10().findDataStructures(query, null, limit, offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<ResourceInternal> findDsds(String query) throws MetamacException {
        try {
            Integer offset = DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            DataStructures dsds = null;
            do {
                dsds = findDsds(offset, MAXIMUM_LIMIT, query);
                results.addAll(dsds.getDataStructures());
                offset += dsds.getDataStructures().size(); // next page
            } while (dsds.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findDsdsAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> dsds = findDsds(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : dsds) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------
    // CONCEPT SCHEMES
    // -------------------------------------------------------------------------------------------------

    private ConceptSchemes findConceptSchemes(int firstResult, int maxResult, String query) throws MetamacException {
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
    public List<ResourceInternal> findConceptSchemes(String query) throws MetamacException {
        try {
            Integer offset = RestApiConstants.DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            ConceptSchemes conceptSchemes = null;
            do {
                conceptSchemes = findConceptSchemes(offset, MAXIMUM_LIMIT, query);
                results.addAll(conceptSchemes.getConceptSchemes());
                offset += conceptSchemes.getConceptSchemes().size(); // next page
            } while (conceptSchemes.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findConceptSchemesAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> results = findConceptSchemes(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : results) {
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
    public List<ResourceInternal> findConcepts(String query) throws MetamacException {
        try {
            Integer offset = RestApiConstants.DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            Concepts concepts = null;
            do {
                concepts = findConcepts(offset, MAXIMUM_LIMIT, query);
                results.addAll(concepts.getConcepts());
                offset += concepts.getConcepts().size(); // next page
            } while (concepts.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    private Concepts findConcepts(int firstResult, int maxResult, String query) throws MetamacException {
        return findConcepts(null, firstResult, maxResult, query);
    }

    private Concepts findConcepts(String conceptSchemeUrn, int firstResult, int maxResult, String query) throws MetamacException {
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
    public List<String> findConceptsAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> results = findConcepts(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : results) {
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

    private Codelists findCodelists(int firstResult, int maxResult, String query) throws MetamacException {
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
    public List<ResourceInternal> findCodelists(String query) throws MetamacException {
        try {
            Integer offset = RestApiConstants.DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            Codelists codelists = null;
            do {
                codelists = findCodelists(offset, MAXIMUM_LIMIT, query);
                results.addAll(codelists.getCodelists());
                offset += codelists.getCodelists().size(); // next page
            } while (codelists.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findCodelistsAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> results = findCodelists(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : results) {
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

    private Codes findCodes(int firstResult, int maxResult, String query) throws MetamacException {
        return findCodes(null, firstResult, maxResult, query);
    }

    private Codes findCodes(String codelistUrn, int firstResult, int maxResult, String query) throws MetamacException {
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
    public List<ResourceInternal> findCodes(String query) throws MetamacException {
        try {
            Integer offset = RestApiConstants.DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            Codes codes = null;
            do {
                codes = findCodes(offset, MAXIMUM_LIMIT, query);
                results.addAll(codes.getCodes());
                offset += codes.getCodes().size(); // next page
            } while (codes.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findCodesAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> results = findCodes(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : results) {
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

    private OrganisationSchemes findOrganisationSchemes(int firstResult, int maxResult, String query) throws MetamacException {
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
    public List<ResourceInternal> findOrganisationSchemes(String query) throws MetamacException {
        try {
            Integer offset = RestApiConstants.DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            OrganisationSchemes organisationSchemes = null;
            do {
                organisationSchemes = findOrganisationSchemes(offset, MAXIMUM_LIMIT, query);
                results.addAll(organisationSchemes.getOrganisationSchemes());
                offset += organisationSchemes.getOrganisationSchemes().size(); // next page
            } while (organisationSchemes.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findOrganisationSchemesAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> results = findOrganisationSchemes(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : results) {
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

    private Organisations findOrganisations(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getSrmRestInternalFacadeV10().findOrganisations(RestApiConstants.WILDCARD_ALL, RestApiConstants.WILDCARD_ALL, RestApiConstants.WILDCARD_ALL, query, orderBy, limit,
                    offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<ResourceInternal> findOrganisations(String query) throws MetamacException {
        try {
            Integer offset = RestApiConstants.DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            Organisations organisations = null;
            do {
                organisations = findOrganisations(offset, MAXIMUM_LIMIT, query);
                results.addAll(organisations.getOrganisations());
                offset += organisations.getOrganisations().size(); // next page
            } while (organisations.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findOrganisationsAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> results = findOrganisations(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : results) {
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
    // CATEGORY SCHEMES
    // -------------------------------------------------------------------------------------------------

    private CategorySchemes findCategorySchemes(int firstResult, int maxResult, String query) throws MetamacException {
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
    public List<ResourceInternal> findCategorySchemes(String query) throws MetamacException {
        try {
            Integer offset = RestApiConstants.DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            CategorySchemes categorySchemes = null;
            do {
                categorySchemes = findCategorySchemes(offset, MAXIMUM_LIMIT, query);
                results.addAll(categorySchemes.getCategorySchemes());
                offset += categorySchemes.getCategorySchemes().size(); // next page
            } while (categorySchemes.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findCategorySchemesAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> results = findCategorySchemes(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : results) {
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

    private Categories findCategories(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getSrmRestInternalFacadeV10().findCategories(RestApiConstants.WILDCARD_ALL, RestApiConstants.WILDCARD_ALL, RestApiConstants.WILDCARD_ALL, query, orderBy, limit,
                    offset);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public Category retrieveCategoryByUrn(String urn) throws MetamacException {
        try {
            String[] params = UrnUtils.splitUrnItem(urn);
            String agencyId = params[0];
            String schemeId = params[1];
            String version = params[2];
            String categoryId = params[3];
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveCategory(agencyId, schemeId, version, categoryId);
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<ResourceInternal> findCategories(String query) throws MetamacException {
        try {
            Integer offset = RestApiConstants.DEFAULT_OFFSET;
            List<ResourceInternal> results = new ArrayList<ResourceInternal>();
            Categories categories = null;
            do {
                categories = findCategories(offset, MAXIMUM_LIMIT, query);
                results.addAll(categories.getCategories());
                offset += categories.getCategories().size(); // next page
            } while (categories.getTotal().intValue() != results.size());
            return results;
        } catch (Exception e) {
            throw manageSrmInternalRestException(e);
        }
    }

    @Override
    public List<String> findCategoriesAsUrnsList(String query) throws MetamacException {
        try {
            List<ResourceInternal> results = findCategories(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : results) {
                urns.add(resource.getUrn());
            }
            return urns;
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
