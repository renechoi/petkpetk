package com.petkpetk.service.domain.shopping.service.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.shopping.dto.cart.CartDetailDto;
import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
import com.petkpetk.service.domain.shopping.dto.cart.CartOrderDto;
import com.petkpetk.service.domain.shopping.dto.order.OrderDto;
import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.repository.cart.CartItemRepository;
import com.petkpetk.service.domain.shopping.repository.cart.CartRepository;
import com.petkpetk.service.domain.shopping.repository.item.ItemRepository;
import com.petkpetk.service.domain.shopping.service.order.OrderService;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final CartRepository cartRepository;
	private final OrderService orderService;
	private final ItemRepository itemRepository;
	private final UserAccountRepository userAccountRepository;
	private final CartItemRepository cartItemRepository;

	// TODO : 카트 아이템 추가, 카트 아이템 갯수변경, 카트 아이템 삭제

	
	/** 카트 아이템 추가 */
	public Long addCartItems(CartItemDto cartItemDto, String email) {
		Item item = getItemById(cartItemDto.getItemId());
		UserAccount userAccount = getUserAccountByEmail(email);
		Cart cart = getCartByUserAccountId(userAccount.getId());

		if (cart == null) {
			cart = createCart(userAccount);
		}

		CartItem savedCartItem = getCartItemByCartIdAndItemId(cart.getId(), item.getId());

		if (savedCartItem != null) {
			savedCartItem.addItemCount(cartItemDto.getItemCount());
			return savedCartItem.getId();
		} else {
			CartItem cartItem = createCartItem(cart, item, cartItemDto.getItemCount(), cartItemDto.getCartId().getTotalPrice());
			cartItemRepository.save(cartItem);
			return cartItem.getId();
		}
	}

	@Transactional(readOnly = true)
	public List<CartDetailDto> getCartItems(String email) {
		List<CartDetailDto> cartDetailDtos = new ArrayList<>();

		UserAccount userAccount = getUserAccountByEmail(email);
		Cart cart = getCartByUserAccountId(userAccount.getId());

		if (cart == null) {
			return cartDetailDtos;
		}

		return cartItemRepository.findCartDetailDtos(cart.getId());
	}


	@Transactional(readOnly = true)
	public boolean validateCartItem(Long cartItemId, String email) {
		UserAccount currentUserAccount = userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
		UserAccount savedMember = cartItem.getCart().getUserAccount();

		return currentUserAccount.getEmail().equals(savedMember.getEmail());
	}


	public void updateCartItemCount(Long cartItemId, Long count) {
		CartItem cartItem = cartItemRepository.findById(cartItemId)
			.orElseThrow(EntityNotFoundException::new);

		cartItem.updateCount(count);
	}

	public void deleteCartItem(Long cartItemId) {
		CartItem cartItem = cartItemRepository.findById(cartItemId)
			.orElseThrow(EntityNotFoundException::new);
		cartItemRepository.delete(cartItem);
	}

	public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {
		List<OrderDto> orderDtos = getOrdersFromCartItems(cartOrderDtoList);
		Long orderId = orderService.createOrders(orderDtos, email);

		List<Long> cartItemIds = getCartItemIds(cartOrderDtoList);
		cartItemRepository.deleteAllByIdIn(cartItemIds);

		return orderId;
	}

	private List<OrderDto> getOrdersFromCartItems(List<CartOrderDto> cartOrderDtoList) {
		return cartOrderDtoList.stream()
			.map(cartOrderDto -> {
				CartItem cartItem = getCartItemById(cartOrderDto.getCartItemId());
				OrderDto orderDto = new OrderDto();
				orderDto.setProductId(cartItem.getItem().getId());
				orderDto.setAmount(cartItem.getItemCount());
				return orderDto;
			})
			.collect(Collectors.toList());
	}

	private List<Long> getCartItemIds(List<CartOrderDto> cartOrderDtoList) {
		return cartOrderDtoList.stream()
			.map(CartOrderDto::getCartItemId)
			.collect(Collectors.toList());
	}


	private CartItem getCartItemById(Long cartItemId) {
		return cartItemRepository.findById(cartItemId)
			.orElseThrow(EntityNotFoundException::new);
	}



	private Item getItemById(Long itemId) {
		return itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
	}

	private UserAccount getUserAccountByEmail(String email) {
		return userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
	}

	private Cart getCartByUserAccountId(Long userAccountId) {
		return cartRepository.findByUserAccountId(userAccountId);
	}
	private Cart createCart(UserAccount userAccount) {
		Cart cart = Cart.createCart(userAccount);
		cartRepository.save(cart);
		return cart;
	}

	private CartItem getCartItemByCartIdAndItemId(Long cartId, Long itemId) {
		return cartItemRepository.findByCartIdAndItemId(cartId, itemId);
	}

	private CartItem createCartItem(Cart cart, Item item, Long itemCount, Long totalPrice) {
		return CartItem.createCartItem(cart, item, itemCount, totalPrice);
	}


}
