package com.petkpetk.service.domain.user.dto.response;

import java.util.Set;

import com.petkpetk.service.common.RoleType;
import com.petkpetk.service.config.security.oauth2.OAuth2ProviderInfo;
import com.petkpetk.service.domain.user.dto.SellerAccountDto;
import com.petkpetk.service.domain.user.entity.embedded.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerAccountResponse {

	private Long id;

	private String email;
	private String password;
	private String name;
	private String nickname;

	private Address address;
	private String profileImage;
	private OAuth2ProviderInfo OAuth2ProviderInfo;
	private Set<RoleType> roles;

	private String phoneNumber;

	private String businessName;

	private String businessNumber;

	public static SellerAccountResponse from(SellerAccountDto dto) {
		return new SellerAccountResponse(dto.getId(), dto.getEmail(), dto.getPassword(), dto.getName(),
			dto.getNickname(), dto.getAddress(), dto.getProfileImage(), dto.getOAuth2ProviderInfo(), dto.getRoles(),
			dto.getPhoneNumber(), dto.getBusinessName(), dto.getBusinessNumber());
	}

	public SellerAccountDto toDto() {
		return SellerAccountDto.of(null, this.email, this.password, this.name, this.nickname, this.address,
			this.profileImage, this.OAuth2ProviderInfo, Set.of(RoleType.SELLER), this.phoneNumber, this.businessName,
			this.businessNumber);
	}

}
