package com.petkpetk.admin.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.petkpetk.admin.config.constant.RoleType;
import com.petkpetk.admin.config.converter.RoleTypeConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "email"), @Index(columnList = "createdAt"), @Index(columnList = "createdBy")})
@Where(clause = "deleted_yn='N'")
@Entity
public class AdminAccount extends AuditingFields implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_account_id", length = 30)
	private Long id;

	@Column(unique = true, length = 50)
	private String email;

	@Column(length = 150)
	private String password;

	@Column(length = 20)
	private String name;

	@Column(nullable = false)
	@Convert(converter = RoleTypeConverter.class)
	private Set<RoleType> roles = new LinkedHashSet<>();

	public AdminAccount encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
		return this;
	}

	public boolean checkPassword(String thatPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(thatPassword, this.password);
	}

	@PrePersist
	public void anonymousSetup() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || "anonymousUser".equals(authentication.getName())) {
			this.createdBy = this.getName();
			this.modifiedBy = this.getName();
		}
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (!(that instanceof AdminAccount)) {
			return false;
		}
		return this.getId() != null && this.getId().equals(((AdminAccount)that).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}

}

