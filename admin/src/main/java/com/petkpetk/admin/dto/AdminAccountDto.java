package com.petkpetk.admin.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import com.petkpetk.admin.config.constant.RoleType;
import com.petkpetk.admin.config.converter.EntityAndDtoConverter;
import com.petkpetk.admin.entity.AdminAccount;

import lombok.Data;

@Data
public class AdminAccountDto {

	private Long id;

	private String email;

	private String password;

	private String name;

	private Set<RoleType> roles = new LinkedHashSet<>();

	public static AdminAccountDto fromEntity(AdminAccount adminAccount) {
		return EntityAndDtoConverter.convertToDto(adminAccount, AdminAccountDto.class);
	}
}

