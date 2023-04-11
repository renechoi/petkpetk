package com.petkpetk.service.domain.user.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.user.constant.RoleType;
import com.petkpetk.service.domain.user.constant.SignUpProvider;
import com.petkpetk.service.common.converter.RoleTypeConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor()
@Table(
	indexes = {
		@Index(columnList = "email"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")})
@Entity
public class UserAccount extends AuditingFields implements Serializable {

	//Todo: 칼럼 length 제한 설정

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	private String password;

	private String name;

	private String nickname;

	private String address;

	@Column(length = 512)
	@Size(max = 512)
	private String profileImage;

	@Enumerated(EnumType.STRING)
	private SignUpProvider signUpProvider;

	@Column(nullable = false)
	@Convert(converter = RoleTypeConverter.class)
	private Set<RoleType> roles = new LinkedHashSet<>();


	public UserAccount(String email, String password, String name, String nickname, String address, String profileImage,
		SignUpProvider signUpProvider, Set<RoleType> roles) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.address = address;
		this.profileImage = profileImage;
		this.signUpProvider = signUpProvider;
		this.roles = roles;
	}

	public static UserAccount of(String email, String password, String name, String nickname, String address, String profileImage,
		SignUpProvider signUpProvider, Set<RoleType> roles) {
		return new UserAccount(email, password, name, nickname, address, profileImage, signUpProvider, roles);
	}

	public UserAccount encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
		return this;
	}

	public boolean checkPassword(String thatPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(thatPassword, this.password);
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (!(that instanceof UserAccount)) {
			return false;
		}
		return this.getId() != null && this.getId().equals(((UserAccount)that).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}

