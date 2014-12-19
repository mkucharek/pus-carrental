package pl.lodz.p.carrental.rest.resources;

import org.apache.commons.lang.StringUtils;
import pl.lodz.p.carrental.core.CarRentalService;
import pl.lodz.p.carrental.core.Model;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Collection;

/**
 * @author mkucharek
 */
@Singleton
@Path("models")
public class ModelsResource {

    private final CarRentalService carRentalService;

    public ModelsResource(CarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }

    @GET
    @Produces({"application/xml", "application/json" })
    public Collection<Model> filterByBrand(@QueryParam("brandName") String brandName) {
        if (StringUtils.isEmpty(brandName)) {
            return carRentalService.getAllModels();
        } else {
            return carRentalService.getModelsByBrand(brandName);
        }
    }
}
