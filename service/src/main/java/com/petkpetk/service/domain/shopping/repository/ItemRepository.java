package com.petkpetk.service.domain.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.repository.querydsl.ItemRepositoryCustom;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>,
	ItemRepositoryCustom {

	List<Item> findByItemName(String itemName);

	List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

}
