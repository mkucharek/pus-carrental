package pl.lodz.p.carrental.rest.resources;

import org.junit.Before;
import org.junit.Test;
import pl.lodz.p.carrental.core.Brand;
import pl.lodz.p.carrental.rest.resources.client.CarRentalWebTargetBuilder;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandsResourceIT {

    private static final GenericType<List<Brand>> BRAND_LIST_ENTITY_TYPE = new GenericType<List<Brand>>() {
    };

    private WebTarget brands;
    
    @Before
    public void setUp() {
        brands = CarRentalWebTargetBuilder.newNoAuthTarget().getBrands();
    }

    @Test
    public void getShouldReturn200() {
        //when
        final Response response = brands.request().get();

        //then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void postShouldNotBeAllowed() {
        //when
        final Response response = brands.request().post(ResourceUtils.ANY_ENTITY);

        //then
        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());
    }

    @Test
    public void putShouldNotBeAllowed() {
        //when
        final Response response = brands.request().put(ResourceUtils.ANY_ENTITY);

        //then
        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteShouldNotBeAllowed() {
        //when
        final Response response = brands.request().delete();

        //then
        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());
    }

    @Test
    public void getShouldServeJSON() {
        //when
        final Response response = brands.request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        final List<Brand> brands = response.readEntity(BRAND_LIST_ENTITY_TYPE);


        //then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals(3, brands.size());
    }

    @Test
    public void getShouldServeXML() {
        //when
        final Response response = brands.request()
                .accept(MediaType.APPLICATION_XML)
                .get();

        final List<Brand> brands = response.readEntity(BRAND_LIST_ENTITY_TYPE);

        //then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_XML_TYPE, response.getMediaType());
        assertEquals(3, brands.size());
    }

    @Test
    public void getShouldServeTextPlain() {
        //when
        final Response response = brands.request()
                .accept(MediaType.TEXT_PLAIN)
                .get();

        //then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getShouldNotServeAtom() {
        //when
        final Response response = brands.request()
                .accept(MediaType.APPLICATION_ATOM_XML)
                .get();

        //then
        assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
    }

}