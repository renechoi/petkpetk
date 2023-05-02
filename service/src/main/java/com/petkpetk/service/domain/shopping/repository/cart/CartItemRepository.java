// package com.petkpetk.service.domain.shopping.repository.cart;
//
// import java.util.List;
//
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;
//
// import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
// import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
//
//
// @Repository
// public interface CartItemRepository extends JpaRepository<CartItem, Long> {
//
// 	CartItem findByCartIdAndItemId(Long cartId, Long itemId);
//
// 	@Query
// 	List<CartItemDto> findCartDetailDtos(Long cartId);
//
//
// 	List<CartItem> deleteAllByIdIn(List<Long> cartIds);
// }
