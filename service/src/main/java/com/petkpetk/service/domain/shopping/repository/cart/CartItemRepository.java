package com.petkpetk.service.domain.shopping.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
