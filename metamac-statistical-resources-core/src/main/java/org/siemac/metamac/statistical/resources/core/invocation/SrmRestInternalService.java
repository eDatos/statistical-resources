package org.siemac.metamac.statistical.resources.core.invocation;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategorySchemes;
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

public interface SrmRestInternalService {

    // DSD
    public DataStructures findDsds(int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findDsdsUrns(int firstResult, int maxResult, String query) throws MetamacException;
    public DataStructure retrieveDsdByUrn(String urn) throws MetamacException;
    
    // CONCEPT SCHEMES
    public ConceptSchemes findConceptSchemes(int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findConceptSchemesUrns(int firstResult, int maxResult, String query) throws MetamacException;
    public ConceptScheme retrieveConceptSchemeByUrn(String urn) throws MetamacException;
    
    // CONCEPTS
    public Concept retrieveConceptByUrn(String urn) throws MetamacException;
    public Concepts findConcepts(String conceptSchemeUrn, int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findConceptsUrns(int firstResult, int maxResult, String query) throws MetamacException;

    // CODELIST
    public Codelists findCodelists(int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findCodelistsAsUrnsList(int firstResult, int maxResult, String query) throws MetamacException;
    public Codelist retrieveCodelistByUrn(String urn) throws MetamacException;

    // CODES
    public Codes findCodes(String codelistUrn, int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findCodesUrns(int firstResult, int maxResult, String query) throws MetamacException;
    
    // ORGANISATION SCHEMES
    public OrganisationSchemes findOrganisationSchemes(int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findOrganisationSchemesUrns(int firstResult, int maxResult, String query) throws MetamacException;
    
    // ORGANISATION
    public Organisations findOrganisations(int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findOrganisationsUrns(int firstResult, int maxResult, String query) throws MetamacException;
    
    // CATEGORY SCHEMES
    public CategorySchemes findCategorySchemes(int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findCategorySchemesUrns(int firstResult, int maxResult, String query) throws MetamacException;
    
    // CATEGORY
    public Categories findCategories(int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findCategoriesUrns(int firstResult, int maxResult, String query) throws MetamacException;

}
