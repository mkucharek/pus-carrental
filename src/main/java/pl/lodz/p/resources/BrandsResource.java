package pl.lodz.p.resources;

import org.apache.commons.lang.StringUtils;
import pl.lodz.p.stores.CarStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author mkucharek
 */
@Path("brands")
public class BrandsResource {

    @GET
    @Produces("text/plain")
    public String getAllBrands() {
        return StringUtils.join(CarStore.INSTANCE.getAllBrands(), ResourceUtil.SEPARATOR_COMMA);
    }

}
