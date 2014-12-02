package pl.lodz.p.domain;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
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

    public void addOrUpdate(Car car) {
        CarRentalStore.INSTANCE.addOrUpdate(car);
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
}
