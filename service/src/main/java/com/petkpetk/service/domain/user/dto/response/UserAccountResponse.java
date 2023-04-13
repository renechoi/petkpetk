package com.petkpetk.service.domain.user.dto.response;

import java.util.Set;

import com.petkpetk.service.domain.user.constant.RoleType;
import com.petkpetk.service.domain.user.constant.SignUpProvider;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountResponse {

	private Long id;

	private String email;
	private String password;
	private String name;
	private String nickname;

	private Address address;
	private String profileImage;
	private SignUpProvider signUpProvider;
	private Set<RoleType> roles;

	public static UserAccountResponse from(UserAccountDto dto) {
		return new UserAccountResponse(dto.getId(), dto.getEmail(), dto.getPassword(), dto.getName(), dto.getNickname(),
			dto.getAddress(), dto.getProfileImage(), dto.getSignUpProvider(), dto.getRoles());
	}

	public UserAccountDto toDto() {
		return UserAccountDto.of(null, this.email, this.password, this.name, this.nickname, this.address,
			this.profileImage, this.signUpProvider, Set.of(RoleType.USER));
	}

}
