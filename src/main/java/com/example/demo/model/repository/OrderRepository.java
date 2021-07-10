package com.example.demo.model.repository;

import com.example.demo.model.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query(value = "INSERT INTO user_order (user_id, car_id, driver, term, total_cost, order_status, local_date_time) " +
            "VALUES (:userId, :carId, :driver, :term, :total_cost, 'CHECKING', :dateTime)",
            nativeQuery = true)
    void makeOrder(@Param("userId") Long userId,
                   @Param("carId") Long carId,
                   @Param("driver") Boolean driver,
                   @Param("term") BigDecimal term,
                   @Param("total_cost") BigDecimal total_cost,
                   @Param("dateTime") LocalDateTime localDate);

    List<Order> findByUser_Id(Long id);

    @Modifying
    @Query(value = "UPDATE User_order SET order_status = :orderStatus WHERE id = :orderId",
            nativeQuery = true)
    int setOrderStatus(@Param("orderStatus") String orderStatus, @Param("orderId") Long orderId);

    @Override
    Page<Order> findAll(Pageable pageable);

    /**
     * testing HQL
     */
    @Query("FROM Order AS o WHERE o.user.id = :userId ")
    List<Order> findAllByUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM user_order WHERE user_id = :userId ", nativeQuery = true)
    Page<Order> findAllByUserPaginated(@Param("userId") Long userId, Pageable pageable);

    Order getById(Long id);

    @Modifying
    void deleteById(Long id);

    @Query(value = "SELECT ROW_COUNT()",nativeQuery = true)
    int rowCount();

    @Modifying
    @Query(value = "INSERT INTO finished_order(order_id, user_id, local_date_time, term, total_cost, car_id) " +
            "SELECT id, user_id, local_date_time, term, total_cost, car_id " +
            "FROM user_order WHERE id=:orderId",
            nativeQuery = true)
    int finishOrder(@Param("orderId") Long orderId);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    int lastInsertedRow();
}
