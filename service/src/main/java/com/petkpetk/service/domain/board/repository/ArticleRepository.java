package com.petkpetk.service.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petkpetk.service.domain.board.entity.Article;
import com.querydsl.apt.jpa.JPAAnnotationProcessor;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	@Modifying
	@Query(value = "update Article a set a.hit = a.hit + 1 where a.articleId =: id")
	void updateHits(@Param("id") Long id);
}
