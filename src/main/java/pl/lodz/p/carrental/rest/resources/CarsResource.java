package pl.lodz.p.carrental.rest.resources;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.lodz.p.carrental.core.Car;
import pl.lodz.p.carrental.core.CarNotFoundException;
import pl.lodz.p.carrental.core.CarRentalService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
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
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * @author mkucharek
 */
@Path("cars")
@Singleton
@PermitAll
public class CarsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarsResource.class);

    private final CarRentalService carRentalService;

    public CarsResource(CarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getCars(
            @Context SecurityContext sc,
            @MatrixParam("brandName") String brandName,
            @MatrixParam("modelName") String modelName,
            @MatrixParam("available") Boolean available) {

        final Response.ResponseBuilder rb = Response.ok();

        if (StringUtils.isEmpty(brandName) && StringUtils.isEmpty(modelName) && null == available) {
            rb.entity(new GenericEntity<Collection<Car>>(carRentalService.getAllCars()) {
            });

        } else {
            rb.entity(
                    new GenericEntity<Collection<Car>>(
                            carRentalService.getAllCarsFilteredBy(brandName, modelName, available)) {
                    });
        }

        rb.link(UriBuilder.fromResource(BrandsResource.class).build(), "brands");
        rb.link(UriBuilder.fromResource(ModelsResource.class).build(), "models");

        return rb.build();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Response getCar(@PathParam("id") int id, @Context Request request) {

        final Car car = carRentalService.getCarById(id);

        if (null == car) {
            // CarNotFoundException extends WebApplicationException, so it will be handled by Jersey
            throw new CarNotFoundException(id);
        }

        LOGGER.debug("Found {}", car);

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(10);

        EntityTag entityTag = new EntityTag(computeTag(car));

        LOGGER.debug("Computed ETag: {}", entityTag.getValue());

        final Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(entityTag);

        if (null != responseBuilder) {
            LOGGER.debug("ETag unchanged - returning metadata");
            return responseBuilder.status(Response.Status.NOT_MODIFIED)
                    .cacheControl(cacheControl)
                    .tag(entityTag)
                    .build();
        }

        LOGGER.debug("ETag has changed - returning new representation");
        return Response.ok(car).cacheControl(cacheControl).tag(entityTag).build();

    }

    private String computeTag(Car existingCar) {
        return existingCar.hashCode() + "";
    }

    @RolesAllowed({"cr-admin"})
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response addCar(@Context UriInfo uriInfo, @Context SecurityContext sc, Car car) {

        Integer id = carRentalService.addCar(car);

        return Response.created(
                UriBuilder.fromUri(uriInfo.getRequestUri())
                        .path(id.toString())
                        .build())
                .build();
    }

    @RolesAllowed({"cr-admin"})
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response updateCar(@Context UriInfo uriInfo,
                              @Context Request request,
                              @PathParam("id") Integer id, Car car) {

        final Car existingCar = carRentalService.getCarById(id);

        EntityTag existingTag = new EntityTag(computeTag(existingCar));

        final Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(existingTag);

        if (responseBuilder != null) {
            LOGGER.debug("ETag does NOT match - returning 412");
            return responseBuilder.build();
        }

        LOGGER.debug("ETag matches - performing update");

        try {
            carRentalService.updateCar(id, car);
        } catch (NullPointerException e) {
            // wrapping regular exception with WebApplicationException
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build());
        }

        return Response.noContent().build();

    }

    @RolesAllowed({"cr-admin"})
    @DELETE
    @Path("{id}")
    public Response deleteCar(@PathParam("id") Integer id) {

        carRentalService.deleteCar(id);

        return Response.noContent().build();

    }
}
