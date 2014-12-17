package pl.lodz.p.resources;

import org.junit.Before;
import org.junit.Test;
import pl.lodz.p.domain.Car;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CarResourceIT {

    private static final String CARS_PATH = "cars";
    private static final Car CAR_TO_PUT = new Car("audi", "A15", true);
    static {
        CAR_TO_PUT.setId(1);
    }


    private WebTarget existingCar;

    private WebTarget carToDelete;

    @Before
    public void setUp() {

        Client c = ClientBuilder.newClient();

        existingCar = c.target(TestUtil.HOST_URL).path(CARS_PATH).path("1");
        carToDelete = c.target(TestUtil.HOST_URL).path(CARS_PATH).path("3");

    }

    @Test
    public void getShouldReturn200() {
        //when
        final Response response = existingCar.request().get();

        //then
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void getShouldReplyWithETag() {
        //when
        final Response response = existingCar.request().get();

        //then
        assertNotNull(response.getEntityTag());
    }

    @Test
    public void getShouldSupportRequestWithETag() {
        //setup
        EntityTag eTag = existingCar.request().get().getEntityTag();

        //when
        final Response response = existingCar.request().header("If-None-Match", eTag.toString()).get();

        //then
        assertEquals(304, response.getStatus());
        assertFalse(response.hasEntity());
    }

    @Test
    public void putShouldReturn401ForUnauthenticatedUser() {
        //when
        final Response response = existingCar.request().put(TestUtil.ANY_ENTITY);

        //then
        assertEquals(401, response.getStatus()); // 401 Unauthorized expected
    }

    @Test
    public void putShouldReturn403ForNonAdminUser() {
        //when
        final Response response = existingCar.request()
                .header("Authorization", "Basic dXNlcjp1c2Vy")
                .put(Entity.entity(CAR_TO_PUT, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(403, response.getStatus()); // 403 Forbidden expected
    }

    @Test
    public void putShouldCreateCarForAdminUserWithXml() {
        //when
        final Response response = existingCar.request()
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .put(Entity.entity(CAR_TO_PUT, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(204, response.getStatus());
    }

    @Test
    public void postShouldNotBeAllowed() {
        //when
        final Response response = existingCar.request().post(TestUtil.ANY_ENTITY);

        //then
        assertEquals(405, response.getStatus()); // 405 Method Not Allowed expected
    }

    @Test
    public void deleteShouldReturn401ForUnauthenticatedUser() {
        //when
        final Response response = existingCar.request().delete();

        //then
        assertEquals(401, response.getStatus()); // 401 Unauthorized expected
    }

    @Test
    public void deleteShouldReturn403ForNonAdminUser() {
        //when
        final Response response = existingCar.request()
                .header("Authorization", "Basic dXNlcjp1c2Vy")
                .delete();

        //then
        assertEquals(403, response.getStatus()); // 403 Forbidden expected
    }

    @Test
    public void deleteShouldDeleteCarForAdminUser() {

        //when
        final Response response = carToDelete.request()
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .delete();

        //then
        assertEquals(204, response.getStatus()); // 204 No Content expected

        assertEquals(404, carToDelete.request().get().getStatus()); // resource should not exist anymore
    }

}