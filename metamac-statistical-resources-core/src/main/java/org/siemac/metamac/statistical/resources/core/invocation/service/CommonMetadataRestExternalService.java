package org.siemac.metamac.statistical.resources.core.invocation.service;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;

public interface CommonMetadataRestExternalService {

    public static final String BEAN_ID = "commonMetadataRestExternalService";

    public Configurations findConfigurations(String query) throws MetamacException;
    public List<String> findConfigurationsAsUrnsList(String query) throws MetamacException;

    public Configuration retrieveConfigurationById(String id) throws MetamacException;
}
