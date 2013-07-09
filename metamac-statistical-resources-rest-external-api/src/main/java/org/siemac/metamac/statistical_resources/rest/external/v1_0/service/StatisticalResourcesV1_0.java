package org.siemac.metamac.statistical_resources.rest.external.v1_0.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@Path("v1.0")
// IMPORTANT: If a new version of API is added, remember change latest url y urlrewrite.xml in war
public interface StatisticalResourcesV1_0 {

    @GET
    @Produces("application/xml")
    @Path("xxx/{id}")
    Dataset retrieveDataset(@PathParam("id") String id);

}
