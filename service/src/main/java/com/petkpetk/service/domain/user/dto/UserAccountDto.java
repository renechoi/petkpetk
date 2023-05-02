package com.petkpetk.service.domain.user.dto;

import java.util.Set;

import com.petkpetk.service.common.RoleType;
import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.config.security.oauth2.OAuth2ProviderInfo;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import com.petkpetk.service.domain.user.entity.ProfileImage;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.entity.embedded.Address;

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
	private ProfileImage profileImage;
	private Address address;
	private OAuth2ProviderInfo OAuth2ProviderInfo;
	private Set<RoleType> roles;

	private String phoneNumber;

	private String businessName;

	private String businessNumber;

	public static UserAccountDto from(UserAccountPrincipal userAccountPrincipal) {
		UserAccountDto userAccountDto = EntityAndDtoConverter.convertToDto(userAccountPrincipal, UserAccountDto.class);
		userAccountDto.setRoles(Set.of(RoleType.USER));
		return userAccountDto;
	}

	public static UserAccountDto from(UserAccount userAccount) {
		UserAccountDto userAccountDto = EntityAndDtoConverter.convertToDto(userAccount, UserAccountDto.class);
		userAccountDto.setRoles(Set.of(RoleType.USER));
		return userAccountDto;
	}

	public UserAccount toEntity() {
		UserAccount userAccount = EntityAndDtoConverter.convertToEntity(this, UserAccount.class);
		userAccount.addImage(profileImage);
		return userAccount;
	}

	public static UserAccountDto fromEntity(UserAccount userAccount) {
		return EntityAndDtoConverter.convertToDto(userAccount, UserAccountDto.class);
	}

	public static UserAccountDto of(Long id, String email, String password, String name, String nickname,
		ProfileImage profileImage, Address address, OAuth2ProviderInfo OAuth2ProviderInfo, Set<RoleType> roles,
		String phoneNumber, String businessName, String businessNumber) {
		return new UserAccountDto(id, email, password, name, nickname, profileImage, address, OAuth2ProviderInfo, roles,
			phoneNumber, businessName, businessNumber);
	}

}
