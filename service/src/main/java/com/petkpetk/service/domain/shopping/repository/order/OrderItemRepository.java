package com.petkpetk.service.domain.shopping.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petkpetk.service.domain.shopping.entity.order.OrderItem;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
