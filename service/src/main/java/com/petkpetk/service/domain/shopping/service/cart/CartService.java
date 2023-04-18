package com.petkpetk.service.domain.shopping.service.cart;

import static com.petkpetk.service.domain.shopping.entity.cart.Cart.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
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
			CartItem cartItem = createCartItem(cart, item, cartItemDto.getItemCount());
			cartItemRepository.save(cartItem);
			return cartItem.getId();
		}
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

	private CartItem createCartItem(Cart cart, Item item, Long itemCount) {
		return CartItem.createCartItem(cart, item, itemCount);
	}


}
