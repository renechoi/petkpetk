package com.petkpetk.service.domain.community.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface HashtagRepositoryCustom {

	List<String> findAllByOrderByHitDescHashtagName();
}
