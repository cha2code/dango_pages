package org.cha2code.dango_pages.controller;

import lombok.RequiredArgsConstructor;
import org.cha2code.dango_pages.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(value = "/signup", method = {RequestMethod.GET, RequestMethod.POST})
	public String signUp() {
		return "pages/signUp";
	}
}