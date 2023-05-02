package com.petkpetk.service.domain.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.petkpetk.service.domain.community.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	Page<Article> findByTitleContaining(String title, Pageable pageable);

	Article findTopByOrderByIdDesc();

}
