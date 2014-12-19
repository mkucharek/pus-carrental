package pl.lodz.p.carrental;

import org.glassfish.jersey.server.ResourceConfig;
import pl.lodz.p.carrental.core.CarRentalService;
import pl.lodz.p.carrental.core.CarRentalStore;
import pl.lodz.p.carrental.core.InMemoryCarRentalStore;
import pl.lodz.p.carrental.rest.resources.BrandsResource;
import pl.lodz.p.carrental.rest.resources.CarsResource;
import pl.lodz.p.carrental.rest.resources.ModelsResource;

/**
 * @author mkucharek
 */
public class CarRentalApplication extends ResourceConfig {

    public CarRentalApplication() {

        CarRentalStore carRentalStore = new InMemoryCarRentalStore();

        CarRentalService carRentalService = new CarRentalService(carRentalStore);

        register(new BrandsResource(carRentalService));
        register(new ModelsResource(carRentalService));
        register(new CarsResource(carRentalService));

        // setting a package that contains the REST resource classes
        packages("pl.lodz.p.carrental.rest.providers");


    }
}
