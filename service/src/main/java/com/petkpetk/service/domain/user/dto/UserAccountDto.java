package com.petkpetk.service.domain.user.dto;

import java.util.Set;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.common.RoleType;
import com.petkpetk.service.config.security.oauth2.OAuth2ProviderInfo;
import com.petkpetk.service.domain.user.entity.embedded.Address;
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
	private OAuth2ProviderInfo OAuth2ProviderInfo;
	private Set<RoleType> roles;

	public UserAccount toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, UserAccount.class);
	}

	public static UserAccountDto fromEntity(UserAccount userAccount) {
		return EntityAndDtoConverter.convertToDto(userAccount, UserAccountDto.class);
	}

	public static UserAccountDto of(Long id, String email, String password, String name, String nickname,
		Address address, String profileImage, OAuth2ProviderInfo OAuth2ProviderInfo, Set<RoleType> roles) {
		return new UserAccountDto(id, email, password, name, nickname, address, profileImage, OAuth2ProviderInfo, roles);
	}

	// TODO : update 수행을 dto가 아닌 entity에서 직접 담당하게 하는 것이 맞는 것으로 판단. 이점에 대한 추후 재고 필요.
	// public void updateUserAccount(UserAccount userAccount) {
	// 	userAccount.setEmail(this.email);
	// 	userAccount.setPassword(this.password);
	// 	userAccount.setName(this.name);
	// 	userAccount.setNickname(this.nickname);
	// 	userAccount.setAddress(this.address);
	// 	userAccount.setProfileImage(this.profileImage);
	// 	userAccount.setRoles(this.roles);
	// }
}
