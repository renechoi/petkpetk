package com.petkpetk.service.domain.shopping.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.shopping.dto.item.ItemSearchDto;
import com.petkpetk.service.domain.shopping.dto.item.ManageItemDto;
import com.petkpetk.service.domain.shopping.service.item.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerItemController {

	private final ItemService itemService;

	@GetMapping("/information")
	public String informationView() {
		return "my-page/seller/sellerMyPage";
	}

	@GetMapping(value = {"/item-manage", "/item-manage/{page}"})
	public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
		System.out.println("♠♠♠♠♠♠♠♠♠♠♠♠♠♠♠♠♠♠ itemSearchDto = " + itemSearchDto);
		System.out.println("♠♠♠♠♠♠♠♠♠♠♠♠♠♠♠♠♠♠ page = " + page);


		PageRequest pageRequest = PageRequest.of(page.orElse(0), 10);


		Page<ManageItemDto> items = itemService.getItemList(itemSearchDto, pageRequest);



		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto",itemSearchDto);
		model.addAttribute("maxPage", 5);
		return "my-page/seller/sellerItemList";

	}
}
