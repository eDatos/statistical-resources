package org.siemac.metamac.statistical_resources.rest.external.invocation;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;

// TODO SRM: cambiar a API externa (METAMAC-1909)
public interface SrmRestExternalFacade {

    public DataStructure retrieveDataStructureByUrn(String urn);

    public Codelist retrieveCodelistByUrn(String urn);
    public Codes retrieveCodesByCodelistUrn(String urn, String order, String openness);

    public Concepts retrieveConceptsByConceptSchemeByUrn(String urn);
    public Concept retrieveConceptByUrn(String urn);
}
