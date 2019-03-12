package org.grocery.Error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GroceryExceptionMapper implements ExceptionMapper<GroceryException> {
    public Response toResponse(GroceryException exception) {
        return Response.status(exception.getCode())
                .entity(exception.getError())
                .build();
    }
}