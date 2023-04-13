package com.petkpetk.service.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {

	USER("ROLE_USER", "구매자"),
	SELLER("ROLE_SELLER", "사업자"),
	ADMIN("ROLE_ADMIN", "관리자");

	private final String roleName;
	private final String description;
}
