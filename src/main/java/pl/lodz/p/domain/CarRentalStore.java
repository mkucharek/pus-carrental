package pl.lodz.p.domain;

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
enum CarRentalStore {
    INSTANCE;

    private Map<Integer, Car> cars = new ConcurrentHashMap<>();

    CarRentalStore() {
        cars = new HashMap<>();

        // some test data to start with
        addOrUpdate(new Car(1, "audi", "A1", Boolean.TRUE));
        addOrUpdate(new Car(2, "audi", "A3", Boolean.FALSE));
        addOrUpdate(new Car(3, "audi", "A4", Boolean.TRUE));
        addOrUpdate(new Car(4, "VW", "Golf", Boolean.TRUE));
        addOrUpdate(new Car(5, "VW", "Passat", Boolean.TRUE));
        addOrUpdate(new Car(6, "Peugeot", "207", Boolean.FALSE));
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

}
