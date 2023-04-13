package com.petkpetk.service.domain.user.dto;

import java.util.Set;

import com.petkpetk.service.common.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.user.constant.RoleType;
import com.petkpetk.service.domain.user.constant.SignUpProvider;
import com.petkpetk.service.domain.user.entity.Address;
import com.petkpetk.service.domain.user.entity.SellerAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerAccountDto {

	private Long id;

	private String email;
	private String password;
	private String name;
	private String nickname;
	private Address address;
	private String profileImage;
	private SignUpProvider signUpProvider;
	private Set<RoleType> roles;

	private String phoneNumber;

	private String businessName;

	private String businessNumber;

	public SellerAccount toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, SellerAccount.class);
	}

	public static SellerAccountDto fromEntity(SellerAccount sellerAccount) {
		return EntityAndDtoConverter.convertToDto(sellerAccount, SellerAccountDto.class);
	}

	public static SellerAccountDto of(Long id, String email, String password, String name, String nickname,
		Address address, String profileImage, SignUpProvider signUpProvider, Set<RoleType> roles, String phoneNumber,
		String businessName, String businessNumber) {
		return new SellerAccountDto(id, email, password, name, nickname, address, profileImage, signUpProvider, roles,
			phoneNumber, businessName, businessNumber);
	}

}
