package pl.lodz.p.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * @author mkucharek
 */
public class ResourceUtils {
    public static final Entity<String> ANY_ENTITY = Entity.entity("Any", MediaType.TEXT_PLAIN);
}
