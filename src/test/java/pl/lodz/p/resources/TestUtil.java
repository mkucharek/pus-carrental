package pl.lodz.p.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * @author mkucharek
 */
public final class TestUtil {

    private TestUtil() {}

    public static final String HOST_URL = "http://localhost:8080";

    public static final Entity<String> ANY_ENTITY = Entity.entity("Any", MediaType.TEXT_PLAIN);

}
