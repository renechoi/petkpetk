package com.petkpetk.service.domain.shopping.service.cart;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.repository.cart.CartItemRepository;
import com.petkpetk.service.domain.shopping.repository.item.ItemRepository;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

@SpringBootTest
@Transactional
class CartServiceTest {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	UserAccountRepository memberRepository;

	@Autowired
	CartService cartService;

	@Autowired
	CartItemRepository cartItemRepository;

	public Item saveItem(){
		Item item = new Item();
		item.setItemName("테스트 상품");
		item.setPrice(10000L);
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemStatus(ItemStatus.SELL);
		item.setItemAmount(100L);
		return itemRepository.save(item);
	}

	public UserAccount saveUserAccount(){
		UserAccount member = new UserAccount();
		member.setEmail("test@test.com");
		return memberRepository.save(member);
	}

	@Test
	@DisplayName("장바구니 담기 테스트")
	public void addCart(){
		Item item = saveItem();
		UserAccount member = saveUserAccount();

		CartItemDto cartItemDto = new CartItemDto();
		cartItemDto.setItemCount(5L);
		cartItemDto.setItemId(item.getId());

		Long cartItemId = cartService.addCartItems(cartItemDto, member.getEmail());
		CartItem cartItem = cartItemRepository.findById(cartItemId)
			.orElseThrow(EntityNotFoundException::new);

		assertEquals(item.getId(), cartItem.getItem().getId());
		assertEquals(cartItemDto.getItemCount(), cartItem.getItemCount());
	}



}