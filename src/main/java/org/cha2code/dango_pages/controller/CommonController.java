package org.cha2code.dango_pages.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

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

	@RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public void logout() {}
}
