package com.petkpetk.service.domain.board.dto;

import java.time.LocalDate;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.board.entity.HashTag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HashTagDto {

	private Long hashTagId;
	private String hashTagName;
	private LocalDate createdAt;
	private LocalDate modifiedAt;
	private LocalDate deletedAt;
	private String deleteYn;

	public HashTag toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, HashTag.class);
	}

	public static HashTagDto fromEntity(HashTag hashTag) {
		return EntityAndDtoConverter.convertToDto(hashTag, HashTagDto.class);
	}

	public static HashTagDto of(Long hashTagId, String hashTagName, LocalDate createdAt, LocalDate modifiedAt,
		LocalDate deletedAt, String deleteYn) {
		return new HashTagDto(
		hashTagId,
		hashTagName,
		createdAt,
		modifiedAt,
		deletedAt,
		deleteYn
		);
	}
}
