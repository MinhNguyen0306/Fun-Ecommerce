package com.example.funE.Repositories;

import com.example.funE.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer> {

    @Query(value = "select count(cart_item_id) from cart_item where user_id = :userId", nativeQuery = true)
    Integer getSizeCart(@Param(value = "userId") Integer userId);
}
