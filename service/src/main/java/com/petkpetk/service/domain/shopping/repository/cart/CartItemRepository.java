package com.petkpetk.service.domain.shopping.repository.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petkpetk.service.domain.shopping.dto.cart.CartDetailDto;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query()
    List<CartDetailDto> findCartDetailDtos(Long cartId);


    List<CartItem> deleteAllByIdIn(List<Long> cartIds);

}
