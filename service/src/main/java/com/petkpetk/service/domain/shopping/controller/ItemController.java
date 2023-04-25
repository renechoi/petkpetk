package com.petkpetk.service.domain.shopping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.dto.item.ItemDto;
import com.petkpetk.service.domain.shopping.dto.item.ItemImageDto;
import com.petkpetk.service.domain.shopping.dto.item.request.ItemRegisterRequest;
import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.dto.review.response.ReviewResponse;
import com.petkpetk.service.domain.shopping.service.item.ItemService;
import com.petkpetk.service.domain.shopping.service.review.ReviewService;
import com.petkpetk.service.domain.shopping.service.review.likes.LikesService;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.service.UserAccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

	private final ItemService itemService;
	private final UserAccountService userAccountService;
	private final LikesService likesService;
	private final ReviewService reviewService;

	@GetMapping("/my-page")
	public String myPageView() {
		return "my-page/seller/sellerMyPage";
	}

	// 상품 등록 페이지
	@GetMapping("/new")
	public String registerItem(Model model) {
		model.addAttribute("item", new ItemRegisterRequest());
		return "/item/itemApply";
	}

	// 상품 등록
	@PostMapping("/new")
	public String registerItem(@Valid ItemRegisterRequest itemRegisterRequest, Authentication authentication) {
		itemService.registerItem(
			ItemDto.from(itemRegisterRequest, userAccountService.getCurrentPrincipal(authentication)));
		return "redirect:/";
	}

	// 해당 상품 상세 페이지
	@GetMapping("/{itemId}")
	public String itemDetail(Model model, @PathVariable("itemId") Long itemId, Authentication authentication) {
		ItemResponse itemResponse = itemService.getItemDetail(itemId);

		String email = "";
		if (authentication != null && authentication.isAuthenticated()) {
			email = authentication.getName();
			model.addAttribute("userEmail", email);
			UserAccount userAccount = userAccountService.searchUser(email).get();
			HashMap<String, String> hashMap;
			hashMap = likesService.findHistoryLikeByUser(userAccount.getId());
			model.addAttribute("reviewHashMap",hashMap);
		}
		List<ReviewResponse> reviewList = reviewService.getReviewList(itemId);

		model.addAttribute("item", itemResponse);
		model.addAttribute("reviewList", reviewList);
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
	public String modifyItem(
		ItemRegisterRequest itemUpdateRequest,
		BindingResult bindingResult, Model model,
		@RequestParam("images") List<MultipartFile> rawImages,
		@RequestParam("imageNames") List<String> imageNames,
		@RequestParam("uniqueImageNames") List<String> uniqueImageNames
	) {

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

		ItemResponse itemResponse = itemService.updateItem(itemUpdateRequest);
		model.addAttribute("item", itemResponse);

		return "redirect:/item/" + itemUpdateRequest.getId();
	}

	// 상품 삭제
	@GetMapping("/delete/{itemId}")
	public String deleteItem(@PathVariable("itemId") Long itemId) {
		itemService.deleteItem(itemId);
		return "redirect:/seller/item-manage";
	}

}
