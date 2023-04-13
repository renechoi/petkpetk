package com.petkpetk.service.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petkpetk.service.domain.board.entity.Article;
import com.querydsl.apt.jpa.JPAAnnotationProcessor;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
