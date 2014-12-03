package pl.lodz.p.domain;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author mkucharek
 */
public class CarNotFoundException extends WebApplicationException {

    public CarNotFoundException(Integer carId) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity("The car with id=" + carId + " does not exist in the store")
                .build());
    }
}
