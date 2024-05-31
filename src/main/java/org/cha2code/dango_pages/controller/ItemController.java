package org.cha2code.dango_pages.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 카테고리별 판매 페이지 표시 및 데이터 전송을 위한 Controller
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {
	private final UserService userService;

	/**
	 * 판매글 작성 페이지 표시 및 데이터를 전송한다.
	 * @return 판매글 작성 페이지
	 */
	@GetMapping("/newAd")
	public String newItem(Model model) {
		model.addAttribute("categoryId", 1);

		return "pages/board/item";
	}
}
