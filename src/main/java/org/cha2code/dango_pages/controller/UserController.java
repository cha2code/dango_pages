package org.cha2code.dango_pages.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.cha2code.dango_pages.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 사용자 페이지 표시 및 데이터 전송을 위한 Controller
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserService userService;

	/**
	 * 로그인 한 사용자의 아이디를 통해 정보를 가져온다.
	 * @param model view로 전달할 데이터
	 * @return 사용자 정보 페이지
	 */
	@GetMapping("/userInfo")
	public String getUser(Model model) {
		// Security session에 저장되어 있는 사용자 ID 저장
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();

		// 아이디로 사용자의 정보 검색 후 DTO에 저장
		UserMasterDTO userInfo = userService.getUserInfo(userId);

		// view로 사용자 정보 전달
		model.addAttribute("userInfo", userInfo);

		return "pages/userInfo";
	}
}
