package com.petkpetk.service.domain.shopping.repository.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.repository.querydsl.item.ItemRepositoryCustom;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>,
	ItemRepositoryCustom {

	List<Item> findByItemName(String itemName);

	void deleteById(Long itemId);

	List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

	@Query("select count(t) from Item t where t.deletedYn='N'")
	Long getItemCount();
}
