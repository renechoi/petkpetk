package com.petkpetk.service.domain.shopping.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;
import com.petkpetk.service.domain.shopping.dto.item.request.ItemRequest;
import com.petkpetk.service.domain.shopping.service.item.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

	private final ItemService itemService;



	@GetMapping("/my-page")
	public String myPageView(){
		return "my-page/sellerMyPage";
	}


	// 상품 등록 페이지
	@GetMapping("/new")
	public String addItemView(Model model) {
		model.addAttribute("ItemResponse", new ItemResponse());
		return "/item/itemApply";
	}

	// 상품 등록
	@PostMapping("/new")
	public String addItem(Model model,
		@Valid ItemResponse itemResponse,
		@RequestParam("itemImgFile") List<MultipartFile> itemImageFiles) {


		if (itemImageFiles.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "대표 이미지를 정해주세요.");
			return "item/itemApply";
		}

		try {
			Long id = itemService.saveItem(itemResponse, itemImageFiles);
			System.out.println("----------------------------- id = " + id+" -----------------------------");
			ItemRequest itemRequest = itemService.getItemDetail(id);

		} catch (Exception e) {
			log.info("errors = {}", itemResponse, e);
			model.addAttribute("errorMessage", "에러가 발생했습니다");
			return "redirect:/seller/item-manage";
		}

		log.info("◎◎◎◎◎◎◎◎ 상품 등록 완료 ◎◎◎◎◎◎◎◎");
		return "redirect:/";
	}

	// 해당 상품 상세 페이지
	@GetMapping("/{itemId}")
	public String itemDetail(Model model, @PathVariable("itemId") Long itemId) {
		System.out.println("itemId = " + itemId);

		try {
			ItemRequest itemRequest = itemService.getItemDetail(itemId);
			System.out.println("itemRequest = " + itemRequest);
			model.addAttribute("item", itemRequest);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다");
			return "main";

		}

		return "item/itemDetail";

	}

	// 해당 상품 수정 페이지 이동
	@GetMapping("/modify/{itemId}")
	public String modifyItemView(@PathVariable("itemId") Long itemId, Model model) {
		try {
			ItemRequest itemRequest = itemService.getItemDetail(itemId);
			model.addAttribute("ItemResponse", itemRequest);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "에러가 발생했습니다");
			return "my-page/seller/sellerItemList";

		}

		return "item/itemApply";
	}

	// 상품 수정
	@PostMapping("/modify/{itemId}")
	public String modifyItem(@PathVariable("itemId") Long itemId, ItemResponse itemResponse,
		Model model, BindingResult bindingResult,
		@RequestParam("itemImgFile") List<MultipartFile> itemImageFiles,
		@RequestParam("imageNames")List<String> imageNames) {

		System.out.println("▣▣▣▣▣▣▣▣▣▣▣▣▣▣ itemId = " + itemId);
		System.out.println("▣▣▣▣▣▣▣▣▣▣▣▣▣▣ itemResponse = " + itemResponse);
		System.out.println("▣▣▣▣▣▣▣▣▣▣▣▣▣▣ itemImageFiles = " + itemImageFiles);
		System.out.println("▣▣▣▣▣▣▣▣▣▣▣▣▣▣ imageNames = " + itemImageFiles);

		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "redirect:/seller/item-manage";
		}

		if (itemImageFiles.get(0).isEmpty() && itemResponse.getId() == null) {
			model.addAttribute("errorMessage", "대표 이미지를 정해주세요.");
			return "item/itemApply";
		}

		try {

			ItemRequest itemRequest = itemService.updateItem(itemResponse, itemImageFiles, itemId, imageNames);
			model.addAttribute("item", itemRequest);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "에러가 발생했습니다");
			return "redirect:/seller/item-manage";
		}
		return "redirect:/item/"+itemId;
	}


	// 상품 삭제
	@GetMapping("/delete/{itemId}")
	public String deleteItem(@PathVariable("itemId") Long itemId, Model model) {

		System.out.println("=============== itemId = " + itemId);

		try {
			List<ItemImage> itemImages = itemService.deleteItem(itemId);
			itemService.deletePathImage(itemImages);

		} catch (Exception e) {
			return "redirect:/seller/item-manage";

		}

		return "redirect:/seller/item-manage";
	}

}
