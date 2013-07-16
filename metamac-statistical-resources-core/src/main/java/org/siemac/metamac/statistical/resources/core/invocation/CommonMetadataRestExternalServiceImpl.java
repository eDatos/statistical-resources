package org.siemac.metamac.statistical.resources.core.invocation;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonMetadataRestExternalServiceImpl implements CommonMetadataRestExternalService {

    @Autowired
    private MetamacApisLocator restApiLocator;
    
    @Override
    public Configurations findConfigurations(String query) {
        String orderBy = null;
        return restApiLocator.getCommonMetadataRestExternalFacadeV10().findConfigurations(query, orderBy);
    }

    @Override
    public List<String> findConfigurationsUrns(String query) {
        Configurations configurations = findConfigurations(query);
        List<String> urns = new ArrayList<String>();
        for (ResourceInternal resource : configurations.getConfigurations()) {
            urns.add(resource.getUrn());
        }
        return urns;
    }
    
    @Override
    public Configuration retrieveConfigurationById(String id) {
        return restApiLocator.getCommonMetadataRestExternalFacadeV10().retrieveConfigurationById(id);
    }

}
