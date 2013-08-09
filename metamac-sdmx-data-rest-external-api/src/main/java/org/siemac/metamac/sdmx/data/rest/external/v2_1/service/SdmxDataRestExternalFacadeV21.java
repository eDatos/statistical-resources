package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.sdmx.resources.sdmxml.schemas.v2_1.message.Structure;

@Path("v2.1")
public interface SdmxDataRestExternalFacadeV21 {

    /**
     * Find data
     * 
     * @return Structure
     */
    @GET
    @Produces("application/xml")
    @Path("data")
    Structure findData(@DefaultValue("full") @QueryParam("detail") String detail, @DefaultValue("none") @QueryParam("references") String references);
}