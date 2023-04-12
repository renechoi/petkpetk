package com.petkpetk.service.domain.user.dto;

import java.util.Set;

import com.petkpetk.service.common.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.user.constant.RoleType;
import com.petkpetk.service.domain.user.constant.SignUpProvider;
import com.petkpetk.service.domain.user.entity.Address;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {

	private Long id;

	private String email;
	private String password;
	private String name;
	private String nickname;
	private Address address;
	private String profileImage;
	private SignUpProvider signUpProvider;
	private Set<RoleType> roles;

	public UserAccount toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, UserAccount.class);
	}

	public static UserAccountDto fromEntity(UserAccount userAccount) {
		return EntityAndDtoConverter.convertToDto(userAccount, UserAccountDto.class);
	}

	public static UserAccountDto of(Long id, String email, String password, String name, String nickname, Address address, String profileImage, SignUpProvider signUpProvider, Set<RoleType> roles){
		return new UserAccountDto(
			id,
			email,
			password,
			name,
			nickname,
			address,
			profileImage,
			signUpProvider,
			roles
		);
	}
}
