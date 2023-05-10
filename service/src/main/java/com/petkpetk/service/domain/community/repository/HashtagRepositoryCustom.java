package com.petkpetk.service.domain.community.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface HashtagRepositoryCustom {

	@Query("select h.hashtagName from Hashtag h order by h.hit desc")
	List<String> findAllByOrderByHitDescHashtagName();
}
