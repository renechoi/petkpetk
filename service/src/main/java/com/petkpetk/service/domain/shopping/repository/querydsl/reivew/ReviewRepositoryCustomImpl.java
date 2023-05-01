package com.petkpetk.service.domain.shopping.repository.querydsl.reivew;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public ReviewRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}


}
