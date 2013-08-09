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
     * Retrieves the base Url api
     */
    public String retrieveSdmxSrmExternalApiUrlBase() throws MetamacException;

}
