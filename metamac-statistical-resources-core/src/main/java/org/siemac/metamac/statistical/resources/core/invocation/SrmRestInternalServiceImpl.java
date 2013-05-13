package org.siemac.metamac.statistical.resources.core.invocation;

import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalServiceImpl implements SrmRestInternalService {

    @Autowired
    private MetamacApisLocator restApiLocator;
    
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
    
}
