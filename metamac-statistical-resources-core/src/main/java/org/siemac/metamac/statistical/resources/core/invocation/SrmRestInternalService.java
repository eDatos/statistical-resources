package org.siemac.metamac.statistical.resources.core.invocation;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;

public interface SrmRestInternalService {

    public DataStructure retrieveDsdByUrn(String urn);

    public DataStructures findDsds(int firstResult, int maxResult, String condition);

    public Concept retrieveConceptByUrn(String urn);

    public Codelist retrieveCodelistByUrn(String urn);

    public Codes findCodes(String codelistUrn, int firstResult, int maxResult, String query);

    public ConceptScheme retrieveConceptSchemeByUrn(String urn);

    public Concepts findConcepts(String conceptSchemeUrn, int firstResult, int maxResult, String query);
}
