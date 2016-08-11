package org.siemac.metamac.statistical_resources.rest.external.invocation;

import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;

public interface CommonMetadataRestExternalFacade {

    public Configuration retrieveConfiguration(String configurationId);
}
