package pl.lodz.p.carrental.core;

import java.util.Collection;
import java.util.Set;

/**
 * @author mkucharek
 */
public interface CarRentalStore {

    Integer add(Car car);

    Collection<Car> getAllCars();

    Car getOne(Integer id);

    void update(Car car);

    Set<Brand> getAllBrands();

    Set<Model> getAllModels();

    void deleteCar(Integer carId);
}
