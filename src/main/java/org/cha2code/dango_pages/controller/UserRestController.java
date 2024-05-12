package org.cha2code.dango_pages.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.UserExistCheckDTO;
import org.cha2code.dango_pages.dto.UserMasterDto;
import org.cha2code.dango_pages.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 입력받은 사용자 정보를 처리하기 위한 RestController
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class UserRestController {
	private final UserService userService;

	/**
	 * 아이디 중복 검사 결과를 반환한다.
	 * @param userCheckDTO UserExistCheckDTO
	 * @return true/false 아이디 유무 결과 반환
	 */
	@PostMapping("inputId")
	public boolean searchId(@RequestBody UserExistCheckDTO userCheckDTO){
		String inputId = userCheckDTO.getUserId();

		if (StringUtils.hasText(inputId)) {
			// service에서 해당 사용자 ID가 있는 지 체크
			long userCnt = userService.countByUserId(inputId);

			// 중복된 아이디가 없는 경우
			if (userCnt == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 닉네임 중복 검사 결과를 반환한다.
	 * @param userCheckDTO UserExistCheckDTO
	 * @return true/false 닉네임 유무 결과 반환
	 */
	@PostMapping("inputNick")
	public boolean searchNickname(@RequestBody UserExistCheckDTO userCheckDTO) {
		String inputNick = userCheckDTO.getNickname();

		if(StringUtils.hasText(inputNick)) {
			// service에서 해당 닉네임이 있는 지 체크
			long nickCnt = userService.countByNickname(inputNick);

			// 중복된 닉네임이 없는 경우
			if(nickCnt == 0) {
				return true;
			}
		}
		return false;
	}
}

