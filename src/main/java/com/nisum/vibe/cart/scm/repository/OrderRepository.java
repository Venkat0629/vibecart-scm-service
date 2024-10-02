package com.nisum.vibe.cart.scm.repository;

import com.nisum.vibe.cart.scm.dao.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Order} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for accessing and manipulating {@link Order}
 * entities in the database. It includes custom query methods to find orders based on different criteria.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByCustomerCustomerId(Long customerId);

    @Query(value = "select * from vibe_cart_orders where shipping_zip_code = :zipcode", nativeQuery = true)
    Optional<Order> findByShippingZipCode(@Param("zipcode") Long zipcode);

    Optional<Order> findById(String orderId);
}
