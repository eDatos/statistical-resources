package org.siemac.metamac.statistical.resources.core.invocation.service;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(CommonMetadataRestExternalService.BEAN_ID)
public class CommonMetadataRestExternalServiceImpl implements CommonMetadataRestExternalService {

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Override
    public Configurations findConfigurations(String query) throws MetamacException {
        try {
            String orderBy = null;
            return restApiLocator.getCommonMetadataRestExternalFacadeV10().findConfigurations(query, orderBy);
        } catch (Exception e) {
            throw manageCommonMetadataRestException(e);
        }
    }

    @Override
    public List<String> findConfigurationsAsUrnsList(String query) throws MetamacException {
        try {
            Configurations configurations = findConfigurations(query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : configurations.getConfigurations()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageCommonMetadataRestException(e);
        }
    }

    @Override
    public Configuration retrieveConfigurationById(String id) throws MetamacException {
        try {
            return restApiLocator.getCommonMetadataRestExternalFacadeV10().retrieveConfigurationById(id);
        } catch (Exception e) {
            throw manageCommonMetadataRestException(e);
        }
    }

    private MetamacException manageCommonMetadataRestException(Exception e) throws MetamacException {
        return ServiceExceptionUtils.manageMetamacRestException(e, ServiceExceptionParameters.API_COMMON_METADATA_EXTERNAL, restApiLocator.getCommonMetadataRestExternalFacadeV10());
    }
}
