package org.siemac.metamac.statistical.resources.core.invocation;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;


public interface SrmRestInternalService {

    DataStructure retrieveDsdByUrn(String urn);
}
