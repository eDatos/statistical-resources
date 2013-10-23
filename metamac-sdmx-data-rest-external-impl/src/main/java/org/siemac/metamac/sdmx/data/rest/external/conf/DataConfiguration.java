package org.siemac.metamac.sdmx.data.rest.external.conf;

import org.siemac.metamac.core.common.exception.MetamacException;

public interface DataConfiguration {

    /**
     * Retrieves organisation URN that is default maintainer of artefacts
     */
    public String retrieveMaintainerUrnDefault() throws MetamacException;

    /**
     * Retrieves organisation ID that is default maintainer to send artefacts
     */
    public String retrieveOrganisationIDDefault() throws MetamacException;

    /**
     * Retrieves the registry base Url api
     */
    public String retrieveSdmxRegistryApiUrlBase() throws MetamacException;

    /**
     * Retrieves the srm base Url api
     */
    public String retrieveSdmxSrmApiUrlBase() throws MetamacException;

    /**
     * Retrieves the statistical resources base Url api
     */
    public String retrieveSdmxStatisticalResourceApiUrlBase() throws MetamacException;
}
