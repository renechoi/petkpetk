package com.petkpetk.service.domain.shopping.entity.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import com.petkpetk.service.common.AuditingFields;

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
@Where(clause = "deleted_yn='N'")
public class KakaoPayment extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "kakao_payment_id")
	private Long id;

	private String tid; // 결제 고유 번호

	private String next_redirect_mobile_url; // 모바일 웹일 경우 받는 결제페이지 url

	private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지


}
