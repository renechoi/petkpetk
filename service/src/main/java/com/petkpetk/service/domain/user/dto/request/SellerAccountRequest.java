package com.petkpetk.service.domain.user.dto.request;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.petkpetk.service.common.RoleType;
import com.petkpetk.service.config.security.oauth2.OAuth2ProviderInfo;
import com.petkpetk.service.domain.user.dto.SellerAccountDto;
import com.petkpetk.service.domain.user.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerAccountRequest {

	private Long id;

	@Email(message = "이메일 형식으로 입력해주세요")
	@NotBlank(message = "이메일을 입력해주세요.")
	private String email;

	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	@NotBlank(message = "닉네임을 입력해주세요.")
	private String nickname;

	@Valid
	@NotNull
	@NotBlank(message = "주소를 입력해주세요.")
	private Address address;
	private String profileImage;
	private OAuth2ProviderInfo OAuth2ProviderInfo;
	private Set<RoleType> roles;

	@NotBlank
	private String phoneNumber;

	@NotBlank
	private String businessName;

	@NotBlank
	private String businessNumber;

	public SellerAccountDto toDto() {
		return SellerAccountDto.of(null, this.email, this.password, this.name, this.nickname, this.address,
			this.profileImage, this.OAuth2ProviderInfo, Set.of(RoleType.SELLER), this.phoneNumber, this.businessName,
			this.businessNumber);
	}

}
