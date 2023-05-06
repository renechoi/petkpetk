// package com.petkpetk.service.domain.shopping.repository.cart;
//
// import java.util.List;
// import java.util.Optional;
//
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;
//
// import com.petkpetk.service.domain.shopping.dto.cart.CartDetailDto;
// import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
// import com.petkpetk.service.domain.shopping.entity.cart.Cart;
// import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
//
//
// @Repository
// public interface CartItemRepository extends JpaRepository<CartItem, Long> {
//
// 	Optional<CartItem> findByCartIdAndItemId(Long cartId, Long itemId);
//
// 	@Query("select new com.petkpetk.service.domain.shopping.dto.cart.CartDetailDto(cartItem.id, item.itemName, item.price, cartItem.itemCount, itemImage.imageUrl) from CartItem cartItem, ItemImage itemImage join cartItem.item item where cartItem.cart.id =:cartId and itemImage.item.id = cartItem.item.id and itemImage.representativeImageYn = 'Y' order by cartItem.createdAt desc")
// 	List<CartDetailDto> findCartDetailDtos(Long cartId);
//
//
// 	List<CartItem> deleteAllByIdIn(List<Long> cartIds);
//
// 	Cart findByUserAccountId(Long id);
// }
