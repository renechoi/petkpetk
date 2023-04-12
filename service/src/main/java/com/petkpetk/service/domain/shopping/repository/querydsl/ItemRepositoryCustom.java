package com.petkpetk.service.domain.shopping.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.petkpetk.service.domain.shopping.dto.security.ItemSearchDto;
import com.petkpetk.service.domain.shopping.dto.security.MainItemDto;
import com.petkpetk.service.domain.shopping.entity.item.Item;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
