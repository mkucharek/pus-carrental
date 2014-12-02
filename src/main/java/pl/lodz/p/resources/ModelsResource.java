package pl.lodz.p.resources;

import org.apache.commons.lang.StringUtils;
import pl.lodz.p.stores.CarStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * @author mkucharek
 */
@Path("models")
public class ModelsResource {

    @GET
    @Produces("text/plain")
    public String filterByBrand(@QueryParam("brandName") String brandName) {
        if (StringUtils.isEmpty(brandName)) {
            return getAllModels();
        } else {
            return getModelsByBrand(brandName);
        }
    }

    private String getAllModels() {
        return StringUtils.join(CarStore.INSTANCE.getAllModels(), ResourceUtil.SEPARATOR_COMMA);
    }

    private String getModelsByBrand(String brandName) {
        return StringUtils.join(CarStore.INSTANCE.getModelsByBrand(brandName), ResourceUtil.SEPARATOR_COMMA);
    }
}
