package pl.lodz.p.resources;

import org.apache.commons.lang.StringUtils;
import pl.lodz.p.domain.Model;
import pl.lodz.p.domain.CarRentalService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Collection;

/**
 * @author mkucharek
 */
@Path("models")
public class ModelsResource {

    @GET
    @Produces({"application/xml", "application/json" })
    public Collection<Model> filterByBrand(@QueryParam("brandName") String brandName) {
        if (StringUtils.isEmpty(brandName)) {
            return CarRentalService.INSTANCE.getAllModels();
        } else {
            return CarRentalService.INSTANCE.getModelsByBrand(brandName);
        }
    }
}
