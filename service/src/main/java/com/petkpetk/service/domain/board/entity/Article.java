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
@Table(name="article")
@Entity
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long articleId;
	private Long userId;
	private String title;
	private String content;
	private Long hit;
	private Long like;
	private LocalDate createdAt;
	private LocalDate modifiedAt;
	private LocalDate deletedAt;
	private Boolean deleteYn;
	private String img;

	public Article( Long userId, String title, String content, Long hit, Long like,
		LocalDate createdAt, LocalDate modifiedAt, LocalDate deletedAt, Boolean deleteYn,
		String img) {
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.like = like;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.deletedAt = deletedAt;
		this.deleteYn = deleteYn;
		this.img = img;
	}

	public static Article of(Long userId, String title, String content, Long hit, Long like,
		LocalDate createdAt, LocalDate modifiedAt, LocalDate deletedAt, Boolean deleteYn, String img) {
		return new Article(
		userId,
		title,
		content,
		hit,
		like,
		createdAt,
		modifiedAt,
		deletedAt,
		deleteYn,
		img
		);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Article article = (Article)o;
		return Objects.equals(articleId, article.articleId) && Objects.equals(
			userId, article.userId) && Objects.equals(title, article.title)
			&& Objects.equals(content, article.content) && Objects.equals(hit,
			article.hit) && Objects.equals(like, article.like) && Objects.equals(
			createdAt, article.createdAt) && Objects.equals(modifiedAt, article.modifiedAt)
			&& Objects.equals(deletedAt, article.deletedAt) && Objects.equals(
			deleteYn, article.deleteYn) && Objects.equals(img, article.img);
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleId, userId, title, content, hit, like, createdAt, modifiedAt,
			deletedAt, deleteYn, img);
	}
}
