package com.petkpetk.service.domain.shopping.entity.calculation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.user.entity.SellerAccount;

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
public class SellerCalculation extends AuditingFields {
	@Id
	@Column(name ="calculation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "seller_account")
	private SellerAccount sellerAccount;




}
