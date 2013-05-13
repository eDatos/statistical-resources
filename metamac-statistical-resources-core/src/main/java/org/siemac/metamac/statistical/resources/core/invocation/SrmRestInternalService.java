package org.siemac.metamac.statistical.resources.core.invocation;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;


public interface SrmRestInternalService {

    DataStructure retrieveDsdByUrn(String urn);

    DataStructures findDsds(int firstResult, int maxResult, String condition);
}
