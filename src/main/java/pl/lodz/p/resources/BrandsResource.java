package pl.lodz.p.resources;

import org.apache.commons.lang.StringUtils;
import pl.lodz.p.domain.Brand;
import pl.lodz.p.domain.CarRentalService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collection;

/**
 * @author mkucharek
 */
@Path("brands")
public class BrandsResource {

    private final static char COMMA_SEPARATOR = ',';

    @GET
    @Produces({"application/xml", "application/json"})
    public Collection<Brand> getAllBrands() {
        return CarRentalService.INSTANCE.getAllBrands();
    }

    @GET
    @Produces("text/plain")
    public String getAllBrandsAsText() {
        return StringUtils.join(getAllBrands(), COMMA_SEPARATOR);
    }

}
