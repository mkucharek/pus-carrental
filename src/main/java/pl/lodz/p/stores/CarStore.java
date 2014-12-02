package pl.lodz.p.stores;

import pl.lodz.p.beans.Brand;
import pl.lodz.p.beans.Car;
import pl.lodz.p.beans.Model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mkucharek
 */
public enum CarStore {
    INSTANCE;

    private Map<Integer, Car> cars = new ConcurrentHashMap<>();

    CarStore() {
        cars = new HashMap<>();

        // some test data to start with
        cars.put(1, new Car(1, "audi", "A1"));
        cars.put(2, new Car(2, "audi", "A3"));
        cars.put(3, new Car(3, "vw", "golf"));
    }

    public Collection<Car> getAllCars() {
        return cars.values();
    }

    public Car getOne(Integer id) {
        return cars.get(id);
    }

    public void addOrUpdate(Car car) {
        if (null == car.getId()) {
            throw new IllegalArgumentException("Cannot add a car without ID");
        }

        cars.put(car.getId(), car);
    }

    public Set<Brand> getAllBrands() {

        if (cars.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Brand> brandSet = new HashSet<>();

        for (Car car : cars.values()) {
            brandSet.add(new Brand(car.getBrandName()));
        }

        return brandSet;

    }

    public Set<Model> getAllModels() {

        if (cars.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Model> modelSet = new HashSet<>();

        for (Car car : cars.values()) {
            modelSet.add(new Model(new Brand(car.getBrandName()), car.getModelName()));
        }

        return modelSet;

    }

    public Set<Model> getModelsByBrand(String brandName) {

        if (cars.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Model> modelSet = new HashSet<>();

        for (Car car : cars.values()) {
            if (car.getBrandName().equals(brandName)) {
                modelSet.add(new Model(new Brand(car.getBrandName()), car.getModelName()));
            }
        }

        return modelSet;
    }

}
