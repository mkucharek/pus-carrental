package pl.lodz.p.domain;

import jersey.repackaged.com.google.common.base.Predicate;
import jersey.repackaged.com.google.common.base.Predicates;
import jersey.repackaged.com.google.common.collect.Collections2;
import jersey.repackaged.com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * @author mkucharek
 */
public enum CarRentalService {
    INSTANCE;

    public Collection<Car> getAllCars() {
        return CarRentalStore.INSTANCE.getAllCars();
    }

    public Collection<Car> getAllCarsFilteredBy(final String brandName,
                                                final String modelName,
                                                final Boolean available) {
        Predicate<Car> brandPredicate = new Predicate<Car>() {
            @Override
            public boolean apply(Car car) {
                return StringUtils.isEmpty(brandName) || brandName.equals(car.getBrandName());
            }
        };

        Predicate<Car> modelPredicate = new Predicate<Car>() {
            @Override
            public boolean apply(Car car) {
                return StringUtils.isEmpty(modelName) || modelName.equals(car.getModelName());
            }
        };

        Predicate<Car> availablePredicate = new Predicate<Car>() {
            @Override
            public boolean apply(Car car) {
                return null == available || available.equals(car.getAvailable());
            }
        };

        return Collections2.filter(CarRentalStore.INSTANCE.getAllCars(),
                Predicates.and(Arrays.asList(brandPredicate, modelPredicate, availablePredicate)));
    }

    public Car getCarById(final Integer id) {
        return CarRentalStore.INSTANCE.getOne(id);
    }

    public void updateCar(Integer carId, Car car) {

        if (null != car.getId() && !carId.equals(car.getId())) {
            throw new IllegalStateException("carId must match car#getId to perform an update");
        }

        // adjust id
        car.setId(carId);

        CarRentalStore.INSTANCE.update(car);
    }

    public Set<Brand> getAllBrands() {
        return CarRentalStore.INSTANCE.getAllBrands();
    }

    public Set<Model> getAllModels() {
        return CarRentalStore.INSTANCE.getAllModels();
    }

    public Collection<Model> getModelsByBrand(final String brandName) {
        return Sets.filter(getAllModels(), new Predicate<Model>() {
            @Override
            public boolean apply(Model model) {
                return model.getBrand().getName().equals(brandName);
            }
        });
    }

    public Integer addCar(Car car) {
        return CarRentalStore.INSTANCE.add(car);
    }

    public void deleteCar(Integer carId) {
        CarRentalStore.INSTANCE.deleteCar(carId);
    }
}