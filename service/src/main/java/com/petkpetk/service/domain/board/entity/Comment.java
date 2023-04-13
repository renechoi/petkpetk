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
@Table(name="comment")
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;
	private Long articleId;
	private Long userId;
	private String content;
	private Long parentCommentId;
	private LocalDate createdAt;
	private LocalDate modifiedAt;
	private LocalDate deletedAt;
	private String deleteYn;

	public Comment(Long articleId, Long userId, String content,
		Long parentCommentId,
		LocalDate createdAt, LocalDate modifiedAt, LocalDate deletedAt, String deleteYn) {
		this.articleId = articleId;
		this.userId = userId;
		this.content = content;
		this.parentCommentId = parentCommentId;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.deletedAt = deletedAt;
		this.deleteYn = deleteYn;
	}

	public static Comment of(Long articleId, Long userId, String content, Long parentCommentId,
		LocalDate createdAt, LocalDate modifiedAt, LocalDate deletedAt, String deleteYn) {
		return new Comment(
		articleId,
		userId,
		content,
		parentCommentId,
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
		Comment comment = (Comment)o;
		return Objects.equals(commentId, comment.commentId) && Objects.equals(
			articleId, comment.articleId) && Objects.equals(userId, comment.userId)
			&& Objects.equals(content, comment.content) && Objects.equals(
			parentCommentId, comment.parentCommentId) && Objects.equals(createdAt,
			comment.createdAt) && Objects.equals(modifiedAt, comment.modifiedAt)
			&& Objects.equals(deletedAt, comment.deletedAt) && Objects.equals(
			deleteYn, comment.deleteYn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentId, articleId, userId, content, parentCommentId, createdAt,
			modifiedAt, deletedAt, deleteYn);
	}
}
