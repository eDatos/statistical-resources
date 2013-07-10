package org.siemac.metamac.statistical_resources.rest.external.v1_0.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;

@Path("v1.0")
// IMPORTANT: If a new version of API is added, remember change latest url y urlrewrite.xml in war
public interface StatisticalResourcesV1_0 {

    @GET
    @Produces({"application/xml", "application/json"})
    @Path("datasets/{agencyID}/{resourceID}/{version}")
    Dataset retrieveDataset(@PathParam("agencyID") String agencyID, @PathParam("resourceID") String resourceID, @PathParam("version") String version);

}
