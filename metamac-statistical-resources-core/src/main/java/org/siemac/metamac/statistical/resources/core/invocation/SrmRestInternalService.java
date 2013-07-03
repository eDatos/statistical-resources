package org.siemac.metamac.statistical.resources.core.invocation;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;


public interface SrmRestInternalService {

    DataStructure retrieveDsdByUrn(String urn);

    DataStructures findDsds(int firstResult, int maxResult, String condition);

    Concept retrieveConceptByUrn(String urn);

    Codelist retrieveCodelistByUrn(String urn);
    
    Codes findCodes(String codelistUrn, int firstResult, int maxResult, String query);

    ConceptScheme retrieveConceptSchemeByUrn(String urn);
}
