package org.siemac.metamac.statistical.resources.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Organisations;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus;


public class SrmRestInternalFacadeV10MockUtils extends RestMockUtils {

    public static Organisations mockOrganisationsWithOnlyUrns(List<String> urns) {
        Organisations organisations = new Organisations();
        organisations.getOrganisations().addAll(mockItemResourcesInternalWithOnlyUrns(urns));
        populateListBaseWithResourcesWithOnlyUrns(organisations, urns);
        return organisations;
    }
    
    public static Concepts mockConceptsWithOnlyUrns(List<String> urns) {
        Concepts concepts = new Concepts();
        concepts.getConcepts().addAll(mockItemResourcesInternalWithOnlyUrns(urns));
        populateListBaseWithResourcesWithOnlyUrns(concepts, urns);
        return concepts;
    }
    
    public static DataStructures mockDsdsWithOnlyUrns(List<String> urns) {
        DataStructures dataStructures = new DataStructures();
        dataStructures.getDataStructures().addAll(mockItemResourcesInternalWithOnlyUrns(urns));
        populateListBaseWithResourcesWithOnlyUrns(dataStructures, urns);
        return dataStructures;
    }
    
    public static Codes mockCodesWithOnlyUrns(List<String> urns) {
        Codes codes = new Codes();
        for (String urn : urns) {
            CodeResourceInternal resource = new CodeResourceInternal();
            resource.setUrn(urn);
            codes.getCodes().add(resource);
        }
        populateListBaseWithResourcesWithOnlyUrns(codes, urns);
        return codes;
    }
    
    private static List<ItemResourceInternal> mockItemResourcesInternalWithOnlyUrns(List<String> urns) {
        List<ItemResourceInternal> resources = new ArrayList<ItemResourceInternal>();
        for (String urn : urns) {
            ItemResourceInternal resource = new ItemResourceInternal();
            resource.setUrn(urn);
            resources.add(resource);
        }
        return resources;
    }
    
    public static String mockQueryFindPublishedOrganisationsUrnsAsList(List<String> urns) {
        return mockQueryFindPublishedResourcesUrnsAsList(OrganisationCriteriaPropertyRestriction.URN, OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_PROC_STATUS, ProcStatus.EXTERNALLY_PUBLISHED.toString(), urns);
    }
    
    public static String mockQueryFindPublishedCodesUrnsAsList(List<String> urns) {
        return mockQueryFindPublishedResourcesUrnsAsList(CodeCriteriaPropertyRestriction.URN, CodeCriteriaPropertyRestriction.CODELIST_PROC_STATUS, ProcStatus.EXTERNALLY_PUBLISHED.toString(), urns);
    }

    public static String mockQueryFindPublishedConceptsUrnsAsList(List<String> urns) {
        return mockQueryFindPublishedResourcesUrnsAsList(ConceptCriteriaPropertyRestriction.URN, ConceptCriteriaPropertyRestriction.CONCEPT_SCHEME_PROC_STATUS, ProcStatus.EXTERNALLY_PUBLISHED.toString(), urns);
    }

    public static String mockQueryFindPublishedDsdsUrnsAsList(List<String> urns) {
        return mockQueryFindPublishedResourcesUrnsAsList(DataStructureCriteriaPropertyRestriction.URN, DataStructureCriteriaPropertyRestriction.PROC_STATUS, ProcStatus.EXTERNALLY_PUBLISHED.toString(), urns);
    }

    
    
}
