package com.petkpetk.service.domain.shopping.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.dto.item.request.ItemRequest;
import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.service.ItemService;

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
		BindingResult bindingResult,
		@RequestParam("itemImgFile") List<MultipartFile> itemImageFiles) {

		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "redirect:item/temApply";
		}

		if (itemImageFiles.get(0).isEmpty() && itemResponse.getId() == null) {
			model.addAttribute("errorMessage", "대표 이미지를 정해주세요.");
			return "redirect:item/itemApply";
		}

		try {
			Long id = itemService.saveItem(itemResponse, itemImageFiles);
			System.out.println("----------------------------- id = " + id+" -----------------------------");
			ItemRequest itemRequest = itemService.getItemDetail(id);

		} catch (Exception e) {
			log.info("errors = {}", itemResponse, e);
			model.addAttribute("errorMessage", "에러가 발생했습니다");
			return "redirect:item/itemApply";
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
			return "my-page/sellerItemList";

		}

		return "my-page/sellerItemModify";
	}

	// 상품 수정
	@PutMapping("/{itemId}")
	public String modifyItem(@PathVariable("itemId") Long itemId, @Valid ItemResponse itemResponse,
		Model model, BindingResult bindingResult,
		@RequestParam("itemImgFile") List<MultipartFile> itemImageFiles) {

		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "redirect:my-page/sellerItemList";
		}

		if (itemImageFiles.get(0).isEmpty() && itemResponse.getId() == null) {
			model.addAttribute("errorMessage", "대표 이미지를 정해주세요.");
			return "item/itemApply";
		}

		try {
			itemService.updateItem(itemResponse, itemImageFiles);
			model.addAttribute("ItemResponse", itemResponse);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "에러가 발생했습니다");
			return "redirect:my-page/sellerItemList";
		}
		return "item/itemDetail";
	}

	
	// 상품 삭제
	@DeleteMapping("/{itemId}")
	public String deleteItem(@PathVariable("itemId") Long itemId, Model model,
		@Valid ItemResponse itemResponse, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "redirect:my-page/sellerItemDetail";
		}

		try {
			int num = itemService.deleteItem(itemId);
			if (num != 1) {
				log.info("상품 삭제를 실패하였습니다.");
				model.addAttribute("errorMessage", "에러가 발생했습니다");
				return "redirect:my-page/sellerItemDetail";
			}

		} catch (Exception e) {
			return "redirect:/my-page/sellerItemDetail";

		}

		return "my-page/sellerItemList";
	}

}
