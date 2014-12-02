package pl.lodz.p.resources;

import org.apache.commons.lang.StringUtils;
import pl.lodz.p.domain.Car;
import pl.lodz.p.domain.CarRentalService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
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
        CarRentalService.INSTANCE.addOrUpdate(car);

        return Response.created(uriInfo.getAbsolutePath())
                .build();

    }
}
