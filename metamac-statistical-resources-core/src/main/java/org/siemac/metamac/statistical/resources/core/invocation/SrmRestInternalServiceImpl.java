package org.siemac.metamac.statistical.resources.core.invocation;

import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalServiceImpl implements SrmRestInternalService {

    @Autowired
    private MetamacApisLocator restApiLocator;
    
    /*
     * DSD 
     */
    
    @Override
    public DataStructure retrieveDsdByUrn(String urn) {
        String[] dataStructureComponents = GeneratorUrnUtils.extractSdmxDatastructurUrnComponents(urn);
        String agencyId = dataStructureComponents[0];
        String dsdId = dataStructureComponents[1];
        String version = dataStructureComponents[2];
        return restApiLocator.getSrmRestInternalFacadeV10().retrieveDataStructure(agencyId, dsdId, version);
    }
    
    @Override
    public DataStructures findDsds(int firstResult, int maxResult, String condition) {
        String limit = String.valueOf(maxResult);
        String offset = String.valueOf(firstResult);
        return restApiLocator.getSrmRestInternalFacadeV10().findDataStructures(condition, null, limit, offset);
    }
    
    /*
     * Concept schemes
     */
    @Override
    public ConceptScheme retrieveConceptSchemeByUrn(String urn) {
        String[] params = UrnUtils.splitUrnItemScheme(urn);
        String agencyId = params[0];
        String resourceId = params[1];
        String version = params[2];
        return restApiLocator.getSrmRestInternalFacadeV10().retrieveConceptScheme(agencyId, resourceId, version);
    }
    
    /*
     * Concepts
     */
    
    @Override
    public Concept retrieveConceptByUrn(String urn) {
        String[] params = UrnUtils.splitUrnItem(urn);
        String agencyId = params[0];
        String schemeId = params[1];
        String version = params[2];
        String conceptId = params[3];
        return restApiLocator.getSrmRestInternalFacadeV10().retrieveConcept(agencyId, schemeId, version, conceptId);
    }
    
    @Override
    public Concepts findConcepts(String conceptSchemeUrn, int firstResult, int maxResult, String query) {
        String limit = String.valueOf(maxResult);
        String offset = String.valueOf(firstResult);
        String[] params = UrnUtils.splitUrnItemScheme(conceptSchemeUrn);
        String agencyId = params[0];
        String resourceId = params[1];
        String version = params[2];
        return restApiLocator.getSrmRestInternalFacadeV10().findConcepts(agencyId, resourceId, version, query, null, limit, offset);
    }
    
    /*
     * Code lists
     */
    @Override
    public Codelist retrieveCodelistByUrn(String urn) {
        String[] params = UrnUtils.splitUrnItemScheme(urn);
        String agencyId = params[0];
        String resourceId = params[1];
        String version = params[2];
        return restApiLocator.getSrmRestInternalFacadeV10().retrieveCodelist(agencyId, resourceId, version);
    }
    
    /*
     * Codes
     */
    @Override
    public Codes findCodes(String codelistUrn, int firstResult, int maxResult, String query) {
        String limit = String.valueOf(maxResult);
        String offset = String.valueOf(firstResult);
        String[] params = UrnUtils.splitUrnItemScheme(codelistUrn);
        String agencyId = params[0];
        String resourceId = params[1];
        String version = params[2];
        return restApiLocator.getSrmRestInternalFacadeV10().findCodes(agencyId, resourceId, version, query, null, limit, offset);
    }
    

    
}
