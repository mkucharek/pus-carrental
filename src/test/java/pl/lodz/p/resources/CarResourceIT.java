package pl.lodz.p.resources;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CarResourceIT {

    private static final String CARS_PATH = "cars";

    private WebTarget existingCar;

    @Before
    public void setUp() {

        Client c = ClientBuilder.newClient();

        existingCar = c.target(TestUtil.HOST_URL).path(CARS_PATH).path("1");

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
    public void postShouldNotBeAllowed() {
        //when
        final Response response = existingCar.request().post(TestUtil.ANY_ENTITY);

        //then
        assertEquals(405, response.getStatus()); // 405 Method Not Allowed expected
    }

}