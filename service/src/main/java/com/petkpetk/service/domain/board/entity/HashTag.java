package com.petkpetk.service.domain.board.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Table(name="hashTag")
@Entity
public class HashTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hashTagId;
	private String hashTagName;
	private LocalDate createdAt;
	private LocalDate modifiedAt;
	private LocalDate deletedAt;
	private String deleteYn;

	public HashTag(String hashTagName, LocalDate createdAt, LocalDate modifiedAt,
		LocalDate deletedAt,
		String deleteYn) {
		this.hashTagName = hashTagName;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.deletedAt = deletedAt;
		this.deleteYn = deleteYn;
	}

	public static HashTag of(String hashTagName, LocalDate createdAt, LocalDate modifiedAt,
		LocalDate deletedAt, String deleteYn) {
		return new HashTag(
		hashTagName,
		createdAt,
		modifiedAt,
		deletedAt,
		deleteYn
		);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		HashTag hashTag = (HashTag)o;
		return Objects.equals(hashTagId, hashTag.hashTagId) && Objects.equals(
			hashTagName, hashTag.hashTagName) && Objects.equals(createdAt, hashTag.createdAt)
			&& Objects.equals(modifiedAt, hashTag.modifiedAt) && Objects.equals(
			deletedAt, hashTag.deletedAt) && Objects.equals(deleteYn, hashTag.deleteYn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(hashTagId, hashTagName, createdAt, modifiedAt, deletedAt, deleteYn);
	}
}
