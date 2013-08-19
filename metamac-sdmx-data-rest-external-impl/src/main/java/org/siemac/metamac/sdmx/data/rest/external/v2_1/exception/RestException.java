package org.siemac.metamac.sdmx.data.rest.external.v2_1.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

//TODO librería común statistic-sdmx-rest?
/**
 * JAX-RS has a RuntimeException class, called WebApplicationException, that allows you to abort your JAX-RS service method.
 * Can take an HTTP status code or even a Response object as one of its constructor parameters
 */
public class RestException extends WebApplicationException {

    private static final long                                      serialVersionUID = 1L;
    private org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error;
    private Status                                                 status;

    public RestException(org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error, Status status) {
        super(Response.status(status).entity(error).build());
    }

    public org.sdmx.resources.sdmxml.schemas.v2_1.message.Error getError() {
        return error;
    }

    public Status getStatus() {
        return status;
    }
}