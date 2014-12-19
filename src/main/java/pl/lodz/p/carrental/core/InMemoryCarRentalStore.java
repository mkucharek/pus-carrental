package pl.lodz.p.carrental.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mkucharek
 */
public class InMemoryCarRentalStore implements CarRentalStore {

    private final ConcurrentMap<Integer, Car> cars;
    private final AtomicInteger idCounter;

    public InMemoryCarRentalStore() {
        cars = new ConcurrentHashMap<>();
        idCounter = new AtomicInteger(0);

        // some test data to start with
        add(new Car("audi", "A1", Boolean.TRUE));
        add(new Car("audi", "A3", Boolean.FALSE));
        add(new Car("audi", "A4", Boolean.TRUE));
        add(new Car("VW", "Golf", Boolean.TRUE));
        add(new Car("VW", "Passat", Boolean.TRUE));
        add(new Car("Peugeot", "207", Boolean.FALSE));
    }

    @Override
    public Integer add(Car car) {
        Integer id = idCounter.incrementAndGet();

        car.setId(id);
        cars.putIfAbsent(id, car);

        return id;
    }

    @Override
    public Collection<Car> getAllCars() {
        return cars.values();
    }

    @Override
    public Car getOne(Integer id) {
        return cars.get(id);
    }

    @Override
    public void update(Car car) {
        if (null == car.getId()) {
            throw new IllegalArgumentException("Cannot update a car that has no ID");
        }

        cars.put(car.getId(), car);
    }

    @Override
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

    @Override
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

    @Override
    public void deleteCar(Integer carId) {
        cars.remove(carId);
    }
}
