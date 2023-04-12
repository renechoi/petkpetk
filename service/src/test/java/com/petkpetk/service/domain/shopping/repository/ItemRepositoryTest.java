package com.petkpetk.service.domain.shopping.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemRepositoryTest {

	@Autowired ItemRepository itemRepository;

	@Test
	void test() {
		itemRepository.findById(1L);
	}
}