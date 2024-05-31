package org.cha2code.dango_pages.controller;

import lombok.RequiredArgsConstructor;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.cha2code.dango_pages.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class CommonController {
	private final UserService userService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String loginPage() {
		return "pages/userLogin";
	}

	@GetMapping("/signup")
	public String signUp() {
		return "pages/signUp";
	}

	@GetMapping("/ad")
	public String adPage() {
		return "pages/board/adPage";
	}

	@PostMapping("/ad")
	public String adPage(@RequestParam Long categoryId, Model model, Principal principal) {
		String userId = principal.getName();
		UserMasterDTO userInfo = userService.getUserInfo(userId);

		model.addAttribute("categoryId", categoryId);
		model.addAttribute("nickname", userInfo.nickname());

		return "pages/board/item";
	}
}