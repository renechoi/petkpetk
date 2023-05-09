package com.petkpetk.service.domain.community.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petkpetk.service.domain.community.entity.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
	List<ArticleComment> findAllByArticleId(Long articleId);
}
