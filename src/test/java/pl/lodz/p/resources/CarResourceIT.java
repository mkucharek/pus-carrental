package pl.lodz.p.resources;

import org.junit.Test;
import pl.lodz.p.domain.Car;
import pl.lodz.p.resources.client.CarRentalWebTargetBuilder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CarResourceIT {

    private static final Car CAR_TO_PUT = new Car("audi", "A15", true);
    static {
        CAR_TO_PUT.setId(1);
    }

    @Test
    public void getShouldReturn200() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newNoAuthTarget().getExistingCar();

        //when
        final Response response = existingCar.request().get();

        //then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void getShouldReplyWithETag() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newNoAuthTarget().getExistingCar();

        //when
        final Response response = existingCar.request().get();

        //then
        assertNotNull(response.getEntityTag());
    }

    @Test
    public void getShouldSupportRequestWithETag() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newNoAuthTarget().getExistingCar();

        //setup
        EntityTag eTag = existingCar.request().get().getEntityTag();

        //when
        final Response response = existingCar.request().header("If-None-Match", eTag.toString()).get();

        //then
        assertEquals(Response.Status.NOT_MODIFIED.getStatusCode(), response.getStatus());
        assertFalse(response.hasEntity());
    }

    @Test
    public void putShouldReturn401ForUnauthenticatedUser() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newNoAuthTarget().getExistingCar();

        //when
        final Response response = existingCar.request().put(ResourceUtils.ANY_ENTITY);

        //then
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void putShouldReturn403ForNonAdminUser() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newBasicAuthUserTarget().getExistingCar();

        //when
        final Response response = existingCar.request()
                .put(Entity.entity(CAR_TO_PUT, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void putShouldCreateCarForAdminUserWithXml() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newBasicAuthAdminTarget().getExistingCar();

        //when
        final Response response = existingCar.request()
                .put(Entity.entity(CAR_TO_PUT, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void postShouldNotBeAllowed() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newNoAuthTarget().getExistingCar();

        //when
        final Response response = existingCar.request().post(ResourceUtils.ANY_ENTITY);

        //then
        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteShouldReturn401ForUnauthenticatedUser() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newNoAuthTarget().getExistingCar();

        //when
        final Response response = existingCar.request().delete();

        //then
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteShouldReturn403ForNonAdminUser() {
        //setup
        WebTarget existingCar = CarRentalWebTargetBuilder.newBasicAuthUserTarget().getExistingCar();

        //when
        final Response response = existingCar.request()
                .delete();

        //then
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteShouldDeleteCarForAdminUser() {
        //setup
        WebTarget carToDelete = CarRentalWebTargetBuilder.newBasicAuthAdminTarget().getCarToDelete();

        //when
        final Response response = carToDelete.request()
                .delete();

        //then
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), carToDelete.request().get().getStatus());
    }

}