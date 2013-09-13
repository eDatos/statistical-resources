package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.sdmx.resources.sdmxml.schemas.v2_1.message.Structure;

@Path("v2.1")
public interface SdmxDataRestExternalFacadeV21 {

    /**
     * Find data
     * 
     * @return Response
     */
    @GET
    @Produces({"application/xml", "application/vnd.sdmx.genericdata+xml;version=2.1", "application/vnd.sdmx.structurespecificdata+xml;version=2.1",
            "application/vnd.sdmx.generictimeseriesdata+xml;version=2.1", "application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1"})
    @Path("data/{flowRef}")
    Response findData(@Context HttpHeaders headers, @PathParam("flowRef") String flowRef, @DefaultValue("full") @QueryParam("detail") String detail,
            @QueryParam("dimensionAtObservation") String dimensionAtObservation, @QueryParam("startPeriod") String startPeriod, @QueryParam("endPeriod") String endPeriod);

    /**
     * Find data
     * 
     * @return Response
     */
    @GET
    @Produces({"application/xml", "application/vnd.sdmx.genericdata+xml;version=2.1", "application/vnd.sdmx.structurespecificdata+xml;version=2.1",
            "application/vnd.sdmx.generictimeseriesdata+xml;version=2.1", "application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1"})
    @Path("data/{flowRef}/{key}")
    Response findData(@Context HttpHeaders headers, @PathParam("flowRef") String flowRef, @PathParam("key") String key, @DefaultValue("full") @QueryParam("detail") String detail,
            @QueryParam("dimensionAtObservation") String dimensionAtObservation, @QueryParam("startPeriod") String startPeriod, @QueryParam("endPeriod") String endPeriod);

    /**
     * Find data
     * 
     * @return Response
     */
    @GET
    @Produces({"application/xml", "application/vnd.sdmx.genericdata+xml;version=2.1", "application/vnd.sdmx.structurespecificdata+xml;version=2.1",
            "application/vnd.sdmx.generictimeseriesdata+xml;version=2.1", "application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1"})
    @Path("data/{flowRef}/{key}/{providerRef}")
    Response findData(@Context HttpHeaders headers, @PathParam("flowRef") String flowRef, @PathParam("key") String key, @PathParam("providerRef") String providerRef,
            @DefaultValue("full") @QueryParam("detail") String detail, @QueryParam("dimensionAtObservation") String dimensionAtObservation, @QueryParam("startPeriod") String startPeriod,
            @QueryParam("endPeriod") String endPeriod);

    /**
     * Find data flows, retrieves the latest version of all dataflow for all agencies.
     * Note: this is a SDMX structure service but for this implementation this service reside here
     * 
     * @return Structure
     */
    @GET
    @Produces("application/xml")
    @Path("dataflow")
    Structure findDataFlows(@DefaultValue("full") @QueryParam("detail") String detail, @DefaultValue("none") @QueryParam("references") String references);

    // /**
    // * Find data flows, retrieves the specific version of specific data consumer schemes and of specific agency.
    // *
    // * @param agencyID Code of agency. Keyword ALL can be used.
    // * @param resourceID Code of data consumer scheme. Keyword ALL can be used.
    // * @param version Version of data consumer scheme. Keyword ALL or LATEST can be used.
    // * @return Structure
    // */
    // @GET
    // @Produces("application/xml")
    // @Path("dataflow/{agencyID}/{resourceID}/{version}")
    // Structure findDataFlows(@PathParam("agencyID") String agencyID, @PathParam("resourceID") String resourceID, @PathParam("version") String version,
    // @DefaultValue("full") @QueryParam("detail") String detail, @DefaultValue("none") @QueryParam("references") String references);

}