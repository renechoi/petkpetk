package com.petkpetk.service.domain.shopping.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petkpetk.service.domain.shopping.entity.cart.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserAccountId(Long userAccountId);
}
