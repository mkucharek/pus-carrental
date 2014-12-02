package pl.lodz.p.stores;

import pl.lodz.p.beans.Car;

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

    public Set<String> getAllBrands() {

        if (cars.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> brandSet = new HashSet<>();

        for (Car car : cars.values()) {
            brandSet.add(car.getBrandName());
        }

        return brandSet;

    }

    public Set<String> getAllModels() {

        if (cars.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> modelSet = new HashSet<>();

        for (Car car : cars.values()) {
            modelSet.add(car.getModelName());
        }

        return modelSet;

    }

    public Set<String> getModelsByBrand(String brandName) {

        if (cars.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> modelSet = new HashSet<>();

        for (Car car : cars.values()) {
            if (car.getBrandName().equals(brandName)) {
                modelSet.add(car.getModelName());
            }
        }

        return modelSet;
    }

}
