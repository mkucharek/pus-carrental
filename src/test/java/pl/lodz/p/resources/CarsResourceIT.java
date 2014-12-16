package pl.lodz.p.resources;

import org.junit.Before;
import org.junit.Test;
import pl.lodz.p.domain.Car;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CarsResourceIT {

    private static final String CARS_PATH = "cars";
    private static final Car CAR_TO_POST = new Car("audi", "A15", true);

    private WebTarget cars;

    @Before
    public void setUp() {

        cars = ClientBuilder.newClient().target(TestUtil.HOST_URL).path(CARS_PATH);
    }

    @Test
    public void getShouldReturn200() {
        //when
        final Response response = cars.request().get();

        //then
        assertEquals(200, response.getStatus());
    }

    @Test
    public void postShouldReturn401ForUnauthenticatedUser() {
        //when
        final Response response = cars.request().post(TestUtil.ANY_ENTITY);

        //then
        assertEquals(401, response.getStatus()); // 401 Unauthorized expected
    }

    @Test
    public void postShouldReturn403ForNonAdminUser() {
        //when
        final Response response = cars.request()
                .header("Authorization", "Basic dXNlcjp1c2Vy")
                .post(Entity.entity(CAR_TO_POST, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(403, response.getStatus()); // 403 Forbidden expected
    }

    @Test
    public void postShouldCreateCarForAdminUserWithXml() {
        //when
        final Response response = cars.request()
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .post(Entity.entity(CAR_TO_POST, MediaType.APPLICATION_XML_TYPE));

        //then
        assertEquals(201, response.getStatus()); // 201 Created expected
        assertNotNull(response.getLocation());
        assertTrue(response.getLocation().toString().contains(TestUtil.HOST_URL));
        assertTrue(response.getLocation().getPath().contains(CARS_PATH));
    }

    @Test
    public void postShouldCreateCarForAdminUserWithJSON() {
        //when
        final Response response = cars.request()
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .post(Entity.entity(CAR_TO_POST, MediaType.APPLICATION_JSON_TYPE));

        //then
        assertEquals(201, response.getStatus()); // 201 Created expected
        assertNotNull(response.getLocation());
        assertTrue(response.getLocation().toString().contains(TestUtil.HOST_URL));
        assertTrue(response.getLocation().getPath().contains(CARS_PATH));
    }

    @Test
    public void postShouldReturn415WhenNotJsonNorXml() {
        //when
        final Response response = cars.request()
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .post(TestUtil.ANY_ENTITY);

        //then
        assertEquals(415, response.getStatus()); // 415 Unsupported Media Type expected
    }

    @Test
    public void putShouldNotBeAllowed() {
        //when
        final Response response = cars.request().put(TestUtil.ANY_ENTITY);

        //then
        assertEquals(405, response.getStatus()); // 405 Method Not Allowed expected
    }

    @Test
    public void deleteShouldNotBeAllowed() {
        //when
        final Response response = cars.request().delete();

        //then
        assertEquals(405, response.getStatus()); // 405 Method Not Allowed expected
    }

}