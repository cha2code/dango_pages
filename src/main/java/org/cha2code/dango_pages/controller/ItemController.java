package org.cha2code.dango_pages.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {

	@GetMapping("/new")
	public String createItem() {
		return "pages/board/item";
	}
}
