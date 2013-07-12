package org.siemac.metamac.statistical_resources.rest.external.invocation;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;

// TODO SRM: cambiar a API externa
public interface SrmRestExternalFacade {

    public DataStructure retrieveDataStructureByUrn(String urn);
    public ConceptScheme retrieveConceptSchemeByUrn(String urn);
    public Codelist retrieveCodelistByUrn(String urn);
}
