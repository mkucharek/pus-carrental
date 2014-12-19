package pl.lodz.p.carrental.rest.resources;

import org.apache.commons.lang.StringUtils;
import pl.lodz.p.carrental.core.Brand;
import pl.lodz.p.carrental.core.CarRentalService;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collection;

/**
 * @author mkucharek
 */
@Singleton
@Path("brands")
public class BrandsResource {

    private final static char COMMA_SEPARATOR = ',';

    private final CarRentalService carRentalService;

    public BrandsResource(CarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }

    @GET
    @Produces("text/plain")
    public String getAllBrandsAsText() {
        return StringUtils.join(getAllBrands(), COMMA_SEPARATOR);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public Collection<Brand> getAllBrands() {
        return carRentalService.getAllBrands();
    }

}
