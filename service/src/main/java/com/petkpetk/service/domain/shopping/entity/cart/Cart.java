package com.petkpetk.service.domain.shopping.entity.cart;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Cart {

	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long user;

	// 맵핑 후 변경 예정
	// @OneToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "user_id")
	// private User user;

	// private Cart(User user) {
	// 	this.user = user;
	// }

	// 맵핑 후 수정 예정
	// public static Cart of(Long userId) {
	// 	return new Cart(userId);
	// }

	// 	public static Cart createCart(User user){
	// 		return Cart.of(user);
	// }

}
