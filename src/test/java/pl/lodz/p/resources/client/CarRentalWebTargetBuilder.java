package pl.lodz.p.resources.client;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author mkucharek
 */
public final class CarRentalWebTargetBuilder {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWD_CLEARTEXT = "admin123";

    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWD_CLEARTEXT = "user123";

    private WebTarget webTarget;

    private CarRentalWebTargetBuilder(Client client) {
        this.webTarget = client.target(ClientUtils.HOST_URL);
    }

    public WebTarget getBrands() {
        return webTarget.path(ClientUtils.BRANDS_PATH);
    }

    public WebTarget getCars() {
        return webTarget.path(ClientUtils.CARS_PATH);
    }

    public WebTarget getExistingCar() {
        return webTarget.path(ClientUtils.EXISTING_CAR_PATH);
    }

    public WebTarget getCarToDelete() {
        return webTarget.path(ClientUtils.CAR_TO_DELETE_PATH);
    }

    public static CarRentalWebTargetBuilder newNoAuthTarget() {
        return new CarRentalWebTargetBuilder(ClientBuilder.newClient());
    }

    public static CarRentalWebTargetBuilder newAdminAuthorizedTarget() {
        return new CarRentalWebTargetBuilder(createDigestAuthClient(ADMIN_USERNAME, ADMIN_PASSWD_CLEARTEXT));
    }

    public static CarRentalWebTargetBuilder newUserAuthorizedTarget() {
        return new CarRentalWebTargetBuilder(createDigestAuthClient(USER_USERNAME, USER_PASSWD_CLEARTEXT));
    }

    private static Client createDigestAuthClient(String username, String password) {
        return ClientBuilder.newClient()
                .register(HttpAuthenticationFeature.digest(username, password));
    }

}
