package org.cha2code.dango_pages.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.cha2code.dango_pages.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserService userService;

	/**
	 * 로그인 한 사용자의 아이디를 통해 정보를 가져온다.
	 * @param userId 사용자 아이디
	 * @return 사용자 정보 페이지
	 */
	@GetMapping("userInfo/{userId}")
	public String getUser(Model model, @PathVariable String userId) {
		// 아이디로 사용자의 정보 검색 후 저장
		UserMasterDTO userInfo = userService.getUserInfo(userId);

		// view로 사용자 정보 전달
		model.addAttribute("userInfo", userInfo);

		return "pages/userInfo";
	}
}
