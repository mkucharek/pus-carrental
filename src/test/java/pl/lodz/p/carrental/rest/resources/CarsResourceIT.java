package pl.lodz.p.carrental.rest.resources;

import org.junit.Test;
import pl.lodz.p.carrental.core.Car;
import pl.lodz.p.carrental.rest.resources.client.CarRentalWebTargetBuilder;
import pl.lodz.p.carrental.rest.resources.client.ClientUtils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CarsResourceIT {

    private static final Car CAR_TO_POST = new Car("audi", "A15", true);

    @Test
    public void getShouldReturn200() {
        //setup
        WebTarget cars = CarRentalWebTargetBuilder.newNoAuthTarget().getCars();

        //when
        final Response response = cars.request().get();

        //then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void postShouldReturn401ForUnauthenticatedUser() {
        //setup
        WebTarget cars = CarRentalWebTargetBuilder.newNoAuthTarget().getCars();

        //when
        final Response response = cars.request().post(Entity.entity(CAR_TO_POST, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void postShouldReturn403ForNonAdminUser() {
        //setup
        WebTarget cars = CarRentalWebTargetBuilder.newUserAuthorizedTarget().getCars();

        //when
        final Response response = cars.request()
                .post(Entity.entity(CAR_TO_POST, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void postShouldCreateCarForAdminUserWithXml() {
        //setup
        WebTarget cars = CarRentalWebTargetBuilder.newAdminAuthorizedTarget().getCars();

        //when
        final Response response = cars.request()
                .post(Entity.entity(CAR_TO_POST, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
        assertTrue(response.getLocation().toString().contains(ClientUtils.HOST_URL));
        assertTrue(response.getLocation().getPath().contains(ClientUtils.CARS_PATH));
    }

    @Test
    public void postShouldCreateCarForAdminUserWithJSON() {
        //setup
        WebTarget cars = CarRentalWebTargetBuilder.newAdminAuthorizedTarget().getCars();

        //when
        final Response response = cars.request()
                .post(Entity.entity(CAR_TO_POST, MediaType.APPLICATION_JSON_TYPE));

        //then
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
        assertTrue(response.getLocation().toString().contains(ClientUtils.HOST_URL));
        assertTrue(response.getLocation().getPath().contains(ClientUtils.CARS_PATH));
    }

    @Test
    public void postShouldReturn415WhenNotJsonNorXml() {
        //setup
        WebTarget cars = CarRentalWebTargetBuilder.newAdminAuthorizedTarget().getCars();

        //when
        final Response response = cars.request()
                .post(ResourceUtils.ANY_ENTITY);

        //then
        assertEquals(Response.Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(), response.getStatus());
    }

    @Test
    public void putShouldNotBeAllowed() {
        //setup
        WebTarget cars = CarRentalWebTargetBuilder.newNoAuthTarget().getCars();

        //when
        final Response response = cars.request().put(ResourceUtils.ANY_ENTITY);

        //then
        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteShouldNotBeAllowed() {
        //setup
        WebTarget cars = CarRentalWebTargetBuilder.newNoAuthTarget().getCars();

        //when
        final Response response = cars.request().delete();

        //then
        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());
    }

}