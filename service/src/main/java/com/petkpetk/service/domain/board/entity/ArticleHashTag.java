package com.petkpetk.service.domain.board.entity;

import java.util.Objects;

import javax.persistence.Entity;
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
@Table(name="articleHashTag")
@Entity
public class ArticleHashTag {


	@Id
	private Long articleId;
	private Long hashTagId;
	private Long userId;

	public ArticleHashTag(Long articleId, Long hashTagId, Long userId) {
		this.articleId = articleId;
		this.hashTagId = hashTagId;
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ArticleHashTag that = (ArticleHashTag)o;
		return Objects.equals(articleId, that.articleId) && Objects.equals(
			hashTagId, that.hashTagId) && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleId, hashTagId, userId);
	}
}
