package com.nisum.vibe.cart.scm.repository;

import com.nisum.vibe.cart.scm.dao.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Warehouse} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for accessing and manipulating {@link Warehouse}
 * entities in the database. It includes a custom query method to find warehouses based on a zipcode.
 * </p>
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query(value = "SELECT * FROM vibe_cart_warehouse WHERE :zipcode BETWEEN zipcode_start AND zipcode_end", nativeQuery = true)
    Optional<Warehouse> findWarehouseByZipcode(@Param("zipcode") Long zipcode);

}
