package com.petkpetk.service.domain.shopping.entity.cart;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.user.entity.UserAccount;

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
public class Cart  extends AuditingFields {

	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_account_id")
	private UserAccount userAccount;

	private Long totalPrice;

	private Cart(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public static Cart of(UserAccount userAccount) {
		return new Cart(userAccount);
	}


	public static Cart createCart(UserAccount userAccount){
			return Cart.of(userAccount);
	}

}
