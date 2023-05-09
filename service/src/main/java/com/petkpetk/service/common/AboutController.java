package com.petkpetk.service.common;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.user.dto.request.UserAskRequest;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import com.petkpetk.service.domain.user.service.UserAskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/about")
public class AboutController {

	private final UserAskService userAskService;

	@GetMapping("/notice")
	public String noticeView() {
		return "about/notice";
	}

	@GetMapping("/ask")
	public String askView(Model model, @AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		model.addAttribute("ask", userAskService.getUserAskList(userAccountPrincipal));
		model.addAttribute("askRequest", new UserAskRequest());
		return "about/ask";
	}

	@PostMapping("/askPost")
	public String askPost(UserAskRequest userAskRequest, @AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		//Todo:: 비회원 유저도 문의사항을 넣을 수 있다. 회원이 문의 사항을 넣으면 유저 정보도 같이 저장된다.
		userAskService.saveAsk(userAskRequest, userAccountPrincipal);
		return "redirect:/about/ask";
	}

}
