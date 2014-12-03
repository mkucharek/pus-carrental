package pl.lodz.p.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Provides mapping for IllegalArgumentException. Jersey will map any IllegalArgumentException into 400 Bad Request status.
 */
@Provider
public class IllegalArgumentProvider
        implements ExceptionMapper<IllegalArgumentException> {
    public Response toResponse(IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}

