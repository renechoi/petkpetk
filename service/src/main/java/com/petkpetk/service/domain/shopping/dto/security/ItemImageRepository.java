package com.petkpetk.service.domain.shopping.dto.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
	List<ItemImage> findByItemIdOrderByIdAsc(Long itemId);

	ItemImage findByItemIdAndRepresentativeImageYn(Long itemId, String representativeImageYn);

}
