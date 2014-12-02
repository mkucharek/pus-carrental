package pl.lodz.p.resources;

import org.apache.commons.lang.StringUtils;
import pl.lodz.p.domain.Car;
import pl.lodz.p.domain.CarRentalService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * @author mkucharek
 */
@Path("cars")
public class CarsResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Collection<Car> getCars(
            @MatrixParam("brandName") String brandName,
            @MatrixParam("modelName") String modelName,
            @MatrixParam("available") Boolean available) {

        if (StringUtils.isEmpty(brandName) && StringUtils.isEmpty(modelName) && null == available) {
            return CarRentalService.INSTANCE.getAllCars();

        } else {
            return CarRentalService.INSTANCE.getAllCarsFilteredBy(brandName, modelName, available);
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Response getCar(@PathParam("id") int id) {

        final Car car = CarRentalService.INSTANCE.getCarById(id);

        if (null != car) {
            return Response.ok(car).build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();

        }
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response addCar(@Context UriInfo uriInfo, Car car) {

        Integer id;
        try {
            id = CarRentalService.INSTANCE.addCar(car);

        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(e.getMessage())
                            .build());
        }

        return Response.created(
                UriBuilder.fromUri(uriInfo.getRequestUri())
                        .path(id.toString())
                        .build())
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response updateCar(@Context UriInfo uriInfo, @PathParam("id") Integer id, Car car) {

        try {
            CarRentalService.INSTANCE.updateCar(id, car);

        } catch (IllegalStateException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(e.getMessage())
                            .build());

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();

    }

    @DELETE
    @Path("{id}")
    public Response deleteCar(@PathParam("id") Integer id) {

        try {
            CarRentalService.INSTANCE.deleteCar(id);

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();

    }
}
