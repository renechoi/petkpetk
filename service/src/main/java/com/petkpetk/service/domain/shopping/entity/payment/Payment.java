package com.petkpetk.service.domain.shopping.entity.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.entity.item.Item;
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
@AllArgsConstructor
@Entity
public class Payment extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long id;
	@Column(unique = true)
	private Long orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id")
	private UserAccount userAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	private Long totalCost;
	private String payToken; // ***** 결제토큰
	private String payApp; // ***** 결제 방식

	public Payment(Long orderId, UserAccount userAccount, Item item, Long totalCost, String payToken, String payApp) {
		this.orderId = orderId;
		this.userAccount = userAccount;
		this.item = item;
		this.totalCost = totalCost;
		this.payToken = payToken;
		this.payApp = payApp;
	}

	public static Payment of(Long orderId, UserAccount userAccount, Item item, Long totalCost, String payToken, String payApp) {
		return new Payment(orderId, userAccount, item, totalCost,payToken,payApp);
	}


}
