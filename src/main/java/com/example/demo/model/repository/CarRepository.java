package com.example.demo.model.repository;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarComfort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Modifying
    @Query(value = "UPDATE Car c SET c.marque=?1, c.model=?2, c.comfort=?3, c.price=?4 WHERE c.id=?5")
    int updateCar(String marque, String model, CarComfort comfort, BigDecimal price, long id);

    @Query(value = "FROM Car WHERE car_status='FREE'")
    List<Car> findFreeCars();

    @Modifying
    @Query(value = "UPDATE Car SET car_status='ORDERED' WHERE id=:id",
            nativeQuery = true)
    void orderCar(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE Car SET car_status = 'FREE' WHERE id = :carId",
            nativeQuery = true)
    int setCarFree(@Param("carId")Long carId);

}
