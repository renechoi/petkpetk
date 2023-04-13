package com.petkpetk.service.domain.shopping.entity.cart;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.petkpetk.service.domain.shopping.dto.order.OrderItemDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	@Column(unique = true)
	private Long userId;

	private List<OrderItemDto> orderItemList;

	private Long totalPrice;


	public CartItem(Long cartId, Long userId, Long totalPrice) {
		this.cartId = cartId;
		this.userId = userId;
		this.totalPrice = totalPrice;
	}

	public static CartItem of(Long cartId, Long userId, Long totalPrice) {
		return new CartItem(cartId, userId, totalPrice);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CartItem cartItem = (CartItem)o;
		return Objects.equals(cartId, cartItem.cartId) && Objects.equals(userId, cartItem.userId)
			&& Objects.equals(orderItemList, cartItem.orderItemList) && Objects.equals(totalPrice,
			cartItem.totalPrice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, userId, orderItemList, totalPrice);
	}
}
