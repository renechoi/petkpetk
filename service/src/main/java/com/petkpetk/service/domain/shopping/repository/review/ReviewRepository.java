package com.petkpetk.service.domain.shopping.repository.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.repository.querydsl.reivew.ReviewRepositoryCustom;

public interface ReviewRepository extends JpaRepository<Review, Long>,
	QuerydslPredicateExecutor<Review>,
	ReviewRepositoryCustom {

	@Query("select r from Review r where r.item.id = ?1 and r.deletedYn = 'N' order by r.likes DESC , r.id DESC ")
	List<Review> findAllByItem_Id(Long itemId);

}
