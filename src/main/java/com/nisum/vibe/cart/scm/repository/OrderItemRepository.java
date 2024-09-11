package com.nisum.vibe.cart.scm.repository;

import com.nisum.vibe.cart.scm.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link OrderItem} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides basic CRUD operations for the {@link OrderItem}
 * entity. It does not include custom queries as it relies on the default methods provided by {@link JpaRepository}.
 * </p>
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
