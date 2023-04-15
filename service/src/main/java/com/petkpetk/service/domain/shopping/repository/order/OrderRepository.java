package com.petkpetk.service.domain.shopping.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.shopping.entity.order.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
