package com.petkpetk.service.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.petkpetk.service.domain.community.entity.Hashtag;

public interface HashtagRepository
	extends JpaRepository<Hashtag, Long>, HashtagRepositoryCustom, QuerydslPredicateExecutor<Hashtag> {

}
