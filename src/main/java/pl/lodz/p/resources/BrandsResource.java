package pl.lodz.p.resources;

import pl.lodz.p.beans.Brand;
import pl.lodz.p.stores.CarStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collection;

/**
 * @author mkucharek
 */
@Path("brands")
public class BrandsResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Collection<Brand> getAllBrands() {
        return CarStore.INSTANCE.getAllBrands();
    }

}
