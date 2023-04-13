package com.petkpetk.service.domain.shopping.entity.cart;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;
	@Column(unique = true)
	private Long cartId;
	@Column(unique = true)
	private Long itemId;

	public Cart(Long cartId, Long itemId) {
		this.cartId = cartId;
		this.itemId = itemId;
	}

	public static Cart of(Long cartId,Long itemId) {
		return new Cart(cartId, itemId);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cart cart = (Cart)o;
		return Objects.equals(cartItemId, cart.cartItemId) && Objects.equals(cartId, cart.cartId)
			&& Objects.equals(itemId, cart.itemId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartItemId, cartId, itemId);
	}
}
