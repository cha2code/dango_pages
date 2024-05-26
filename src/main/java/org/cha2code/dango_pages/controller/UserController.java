package org.cha2code.dango_pages.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.cha2code.dango_pages.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

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
	 * @param principal 세션에 저장 된 사용자 정보
	 * @param model view로 전달할 사용자 아이디
	 * @return 사용자 정보 페이지
	 */
	@GetMapping("/userInfo")
	public String getUser(Principal principal, Model model) {
		// session에 저장되어 있는 사용자 ID 저장
		String userId = principal.getName();

		// 아이디로 사용자의 정보 검색 후 DTO에 저장
		UserMasterDTO userInfo = userService.getUserInfo(userId);

		// view로 사용자 정보 전달
		model.addAttribute("userInfo", userInfo);

		return "pages/userInfo";
	}

	/**
	 * 사용자 닉네임 수정 페이지를 반환한다.
	 * @param principal 세션에 저장 된 사용자 정보
	 * @param model view로 전달할 사용자 정보
	 * @return 사용자 닉네임 수정 페이지
	 */
	@GetMapping("/updateNickname")
	public String updateUserNick(Principal principal, Model model) {
		// session에 저장되어 있는 사용자 ID 저장
		String userId = principal.getName();

		// 아이디로 사용자의 정보 검색 후 DTO에 저장
		UserMasterDTO userInfo = userService.getUserInfo(userId);

		// view로 사용자 정보 전달
		model.addAttribute("userInfo", userInfo);

		return "pages/user/update/updateNickname";
	}

	/**
	 * 사용자 비밀번호 수정 페이지를 반환한다.
	 * @param principal 세션에 저장 된 사용자 정보
	 * @param model view로 전달할 사용자 아이디
	 * @return 사용자 비밀번호 수정 페이지
	 */
	@GetMapping("/updatePassword")
	public String updateUserPassword(Principal principal, Model model) {
		// session에 저장되어 있는 사용자 ID 저장
		String userId = principal.getName();

		// view로 사용자 아이디 전달
		model.addAttribute("userId", userId);

		return "pages/user/update/updatePassword";
	}

	/**
	 * 사용자 이메일 수정 페이지를 반환한다.
	 * @param principal 세션에 저장 된 사용자 정보
	 * @param model view로 전달할 사용자 아이디
	 * @return 사용자 이메일 수정 페이지
	 */
	@GetMapping("/updateEmail")
	public String updateUserEmail(Principal principal, Model model) {
		// session에 저장되어 있는 사용자 ID 저장
		String userId = principal.getName();

		// 아이디로 사용자의 정보 검색 후 DTO에 저장
		UserMasterDTO userInfo = userService.getUserInfo(userId);

		// view로 사용자 정보 전달
		model.addAttribute("userInfo", userInfo);

		return "pages/user/update/updateEmail";
	}
}