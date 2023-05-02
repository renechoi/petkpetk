package com.petkpetk.service.domain.shopping.repository.order;

import java.util.List;

import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.repository.querydsl.order.OrderRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.petkpetk.service.domain.shopping.entity.order.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order>, OrderRepositoryCustom {

//	Order findOne(Long id);
	Page<Order> findByUserAccountEmailOrderByCreatedAtDesc(String email, Pageable pageable);
	Long countOrderByUserAccountEmail(String email);
}
