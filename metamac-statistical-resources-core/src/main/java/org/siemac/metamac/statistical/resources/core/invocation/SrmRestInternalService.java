package org.siemac.metamac.statistical.resources.core.invocation;

import java.util.List;

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
    public DataStructures findDsds(int firstResult, int maxResult, String query);
    public List<String> findDsdsUrns(int firstResult, int maxResult, String query);
    public DataStructure retrieveDsdByUrn(String urn);
    
    // CONCEPT SCHEMES
    public ConceptSchemes findConceptSchemes(int firstResult, int maxResult, String query);
    public List<String> findConceptSchemesUrns(int firstResult, int maxResult, String query);
    public ConceptScheme retrieveConceptSchemeByUrn(String urn);
    
    // CONCEPTS
    public Concept retrieveConceptByUrn(String urn);
    public Concepts findConcepts(String conceptSchemeUrn, int firstResult, int maxResult, String query);
    public List<String> findConceptsUrns(int firstResult, int maxResult, String query);

    // CODELIST
    public Codelists findCodelists(int firstResult, int maxResult, String query);
    public List<String> findCodelistsAsUrnsList(int firstResult, int maxResult, String query);
    public Codelist retrieveCodelistByUrn(String urn);

    // CODES
    public Codes findCodes(String codelistUrn, int firstResult, int maxResult, String query);
    public List<String> findCodesUrns(int firstResult, int maxResult, String query);
    
    // ORGANISATION SCHEMES
    public OrganisationSchemes findOrganisationSchemes(int firstResult, int maxResult, String query);
    public List<String> findOrganisationSchemesUrns(int firstResult, int maxResult, String query);
    
    // ORGANISATION
    public Organisations findOrganisations(int firstResult, int maxResult, String query);
    public List<String> findOrganisationsUrns(int firstResult, int maxResult, String query);
    
    // CATEGORY SCHEMES
    public CategorySchemes findCategorySchemes(int firstResult, int maxResult, String query);
    public List<String> findCategorySchemesUrns(int firstResult, int maxResult, String query);
    
    // CATEGORY
    public Categories findCategories(int firstResult, int maxResult, String query);
    public List<String> findCategoriesUrns(int firstResult, int maxResult, String query);

}
