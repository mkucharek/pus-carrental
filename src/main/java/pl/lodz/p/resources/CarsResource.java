package pl.lodz.p.resources;

import pl.lodz.p.beans.Car;
import pl.lodz.p.stores.CarStore;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * @author mkucharek
 */
@Path("cars")
public class CarsResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Collection<Car> getAllCars() {
        return CarStore.INSTANCE.getAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Response getCar(@PathParam("id") int id) {

        final Car car = CarStore.INSTANCE.getOne(id);

        if (null != car) {
            return Response.ok(car).build();

        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Car not found for id=" + id)
                    .build();

        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response addCar(@Context UriInfo uriInfo, @PathParam("id") int id, Car car) {

        // adjusting ID to match the path parameter
        car.setId(id);

        // adding the car
        CarStore.INSTANCE.addOrUpdate(car);

        return Response.created(uriInfo.getAbsolutePath())
                .build();

    }
}
