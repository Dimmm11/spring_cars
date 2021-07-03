package com.example.demo.model.repository;

import com.example.demo.model.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query(value = "INSERT INTO user_order (user_id, car_id, driver, term, total_cost, order_status) " +
            "VALUES (:userId, :carId, :driver, :term, :total_cost, 'CHECKING')",
            nativeQuery = true)
    void carOrder(@Param("userId") Long userId,
                  @Param("carId") Long carId,
                  @Param("driver") Boolean driver,
                  @Param("term") BigDecimal term,
                  @Param("total_cost") BigDecimal total_cost);

    List<Order> findByUser_Id(Long id);

    @Modifying
    @Query(value = "UPDATE User_order SET order_status = :orderStatus WHERE id = :orderId",
            nativeQuery = true)
    int setOrderStatus(@Param("orderStatus") String orderStatus,@Param("orderId") Long orderId);


    @Query(value = "SELECT * FROM user_order WHERE user_id = :userId AND order_status NOT LIKE :orderStatus", nativeQuery = true)
    List<Order> findAllByUserAndStatus(@Param("orderStatus") String orderStatus,
                                       @Param("userId")Long userId);


    Order getById(Long id);

     @Modifying
     void deleteById(Long id);
}
