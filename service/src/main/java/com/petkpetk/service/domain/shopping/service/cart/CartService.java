package com.petkpetk.service.domain.shopping.service.cart;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.shopping.dto.cart.request.CartItemRequest;
import com.petkpetk.service.domain.shopping.dto.item.ItemDto;
import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.exception.StockAlreadyInCartException;
import com.petkpetk.service.domain.shopping.repository.cart.CartItemRepository;
import com.petkpetk.service.domain.shopping.repository.cart.CartRepository;
import com.petkpetk.service.domain.shopping.service.item.ItemService;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final ItemService itemService;
	private final UserAccountService userAccountService;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	public void addCartItems(CartItemRequest cartItemRequest, String email) {

		Item item = itemService.getItem(cartItemRequest.getItemId());
		UserAccountDto userAccountDto = userAccountService.searchUserDto(email);
		Cart cart = cartRepository.findByUserAccountId(userAccountDto.getId()).orElse(Cart.of(userAccountDto));

		if (cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId()).isPresent() ) {
			throw new StockAlreadyInCartException();
		}

		CartItem cartItem = CartItem.of(cart, item, cartItemRequest.getCartItemCount());
		cartItemRepository.save(cartItem);
	}



	// public Long addCartItems(CartItemRequest cartItemRequest, String email) {
	//
	// 	ItemDto itemDto = itemService.searchItem(cartItemRequest.getItemId());
	// 	UserAccountDto userAccountDto = userAccountService.searchUserDto(email);
	//
	//
	// 	// cart 레포지토리에 똑같은 게 있다 => 유저도 같고, 아이템도 같은 경우 => 이미 장바구니에 존재합니다 라는 메시지 리턴
	//
	// 	Cart cart = cartRepository.findByUserAccountId(userAccountDto.getId());
	//
	//
	// 	// 없다 => 저장 => 성공 메시지
	//
	//
	//
	//
	// 	CartItem cartItem = createCartItem(cart, item, cartItemDto.getItemCount(), cartItemDto.getCartId().getTotalPrice());
	// 	cartItemRepository.save(cartItem);
	//
	//
	// 	Cart cart = getCartByUserAccountId(userAccount.getId());
	//
	// 	if (cart == null) {
	// 		cart = createCart(userAccount);
	// 	}
	//
	// 	CartItem savedCartItem = getCartItemByCartIdAndItemId(cart.getId(), item.getId());
	//
	// 	if (savedCartItem != null) {
	// 		savedCartItem.addItemCount(cartItemDto.getItemCount());
	// 		return savedCartItem.getId();
	// 	} else {
	// 		CartItem cartItem = createCartItem(cart, item, cartItemDto.getItemCount(), cartItemDto.getCartId().getTotalPrice());
	// 		cartItemRepository.save(cartItem);
	// 		return cartItem.getId();
	// 	}
	// }




	// @Transactional(readOnly = true)
	// public List<CartDetailDto> getCartItems(String email) {
	// 	List<CartDetailDto> cartDetailDtos = new ArrayList<>();
	//
	// 	UserAccount userAccount = getUserAccountByEmail(email);
	// 	Cart cart = getCartByUserAccountId(userAccount.getId());
	//
	// 	if (cart == null) {
	// 		return cartDetailDtos;
	// 	}
	//
	// 	return cartItemRepository.findCartDetailDtos(cart.getId());
	// }


	// @Transactional(readOnly = true)
	// public boolean validateCartItem(Long cartItemId, String email) {
	// 	UserAccount currentUserAccount = userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
	// 	CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
	// 	UserAccount savedMember = cartItem.getCart().getUserAccount();
	//
	// 	return currentUserAccount.getEmail().equals(savedMember.getEmail());
	// }


	// public void updateCartItemCount(Long cartItemId, Long count) {
	// 	CartItem cartItem = cartItemRepository.findById(cartItemId)
	// 		.orElseThrow(EntityNotFoundException::new);
	//
	// 	cartItem.updateCount(count);
	// }
	//
	// public void deleteCartItem(Long cartItemId) {
	// 	CartItem cartItem = cartItemRepository.findById(cartItemId)
	// 		.orElseThrow(EntityNotFoundException::new);
	// 	cartItemRepository.delete(cartItem);
	// }
	//
	// public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {
	// 	List<OrderDto> orderDtos = getOrdersFromCartItems(cartOrderDtoList);
	// 	Long orderId = orderService.createOrders(orderDtos, email);
	//
	// 	List<Long> cartItemIds = getCartItemIds(cartOrderDtoList);
	// 	cartItemRepository.deleteAllByIdIn(cartItemIds);
	//
	// 	return orderId;
	// }
	//
	// private List<OrderDto> getOrdersFromCartItems(List<CartOrderDto> cartOrderDtoList) {
	// 	return cartOrderDtoList.stream()
	// 		.map(cartOrderDto -> {
	// 			CartItem cartItem = getCartItemById(cartOrderDto.getCartItemId());
	// 			OrderDto orderDto = new OrderDto();
	// 			orderDto.setItemId(cartItem.getItem().getId());
	// 			orderDto.setOrderCount(cartItem.getItemCount());
	// 			return orderDto;
	// 		})
	// 		.collect(Collectors.toList());
	// }
	//
	// private List<Long> getCartItemIds(List<CartOrderDto> cartOrderDtoList) {
	// 	return cartOrderDtoList.stream()
	// 		.map(CartOrderDto::getCartItemId)
	// 		.collect(Collectors.toList());
	// }
	//
	//
	// private CartItem getCartItemById(Long cartItemId) {
	// 	return cartItemRepository.findById(cartItemId)
	// 		.orElseThrow(EntityNotFoundException::new);
	// }
	//
	//
	// private CartItem createCartItem(Cart cart, Item item, Long itemCount, Long totalPrice) {
	// 	return CartItem.createCartItem(cart, item, itemCount, totalPrice);
	// }


}
