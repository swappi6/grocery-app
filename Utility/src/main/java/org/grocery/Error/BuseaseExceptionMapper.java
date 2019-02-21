package org.grocery.Error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BuseaseExceptionMapper implements ExceptionMapper<BuseaseException> {
    public Response toResponse(BuseaseException exception) {
        return Response.status(exception.getCode())
                .entity(exception.getError())
                .build();
    }
}