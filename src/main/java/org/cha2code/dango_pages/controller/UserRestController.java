package org.cha2code.dango_pages.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.MailCheckDTO;
import org.cha2code.dango_pages.dto.UserExistCheckDTO;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.cha2code.dango_pages.dto.UserRoleDTO;
import org.cha2code.dango_pages.service.MailService;
import org.cha2code.dango_pages.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.List;

/**
 * 입력받은 사용자 정보를 처리하기 위한 RestController
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class UserRestController {
	private final UserService userService;
	private final MailService mailService;

	boolean result = false; // 수행 결과 반환을 위한 변수

	/**
	 * 아이디 중복 검사 결과를 반환한다.
	 * @param userCheckDTO UserExistCheckDTO
	 * @return 아이디 유무 결과 반환
	 */
	@PostMapping("inputId")
	public boolean searchId(@RequestBody UserExistCheckDTO userCheckDTO){
		String inputId = userCheckDTO.getUserId();

		// 입력 받은 아이디가 있을 경우
		if (StringUtils.hasText(inputId)) {
			// service에서 해당 사용자 ID가 있는 지 체크
			long userCnt = userService.countByUserId(inputId);

			// 중복된 아이디가 없는 경우
			if (userCnt == 0) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 닉네임 중복 검사 결과를 반환한다.
	 * @param userCheckDTO UserExistCheckDTO
	 * @return 닉네임 유무 결과 반환
	 */
	@PostMapping("inputNick")
	public boolean searchNickname(@RequestBody UserExistCheckDTO userCheckDTO) {
		String inputNick = userCheckDTO.getNickname();

		// 입력 받은 닉네임이 있을 경우
		if(StringUtils.hasText(inputNick)) {
			// service에서 해당 닉네임이 있는 지 체크
			long nickCnt = userService.countByNickname(inputNick);

			// 중복된 닉네임이 없는 경우
			if(nickCnt == 0) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 이메일 중복 검사 결과 또는 인증 코드를 반환한다.
	 * @param mailCheckDTO UserExistCheckDTO
	 * @return 이메일 중복 결과 또는 인증 코드 반환
	 */
	@PostMapping("inputEmail")
	public String searchEmail(@RequestBody MailCheckDTO mailCheckDTO) throws MessagingException {
		String inputEmail = mailCheckDTO.getEmail();

		// 입력 받은 메일 주소가 있을 경우
		if(StringUtils.hasText(inputEmail)) {
			// service에서 해당 이메일이 있는 지 체크
			long mailCnt = userService.countByEmail(inputEmail);

			// 중복된 이메일이 있는 경우
			if(mailCnt != 0) {
				return "false";
			}

			// 인증 코드 받을 메일 저장
			mailCheckDTO.setReceiver(inputEmail);

			// 인증 코드 생성
			mailCheckDTO.setCheckCode(mailService.createRandomCode());

			// 생성한 인증 코드 포함 후 메일 발송
			mailService.CreateMail(mailCheckDTO);

			// 발송된 인증 코드 반환
			return mailCheckDTO.getCheckCode();
		}

		return "false";
	}

	/**
	 * 사용자의 기존 비밀번호 일치 여부를 반환한다.
	 * @param user 사용자 정보
	 * @return 비밀번호 비교 결과
	 */
	@PostMapping("existPwCheck")
	public boolean existPasswordCheck(@RequestBody UserMasterDTO user) {
		// service에서 사용자 아이디 검색
		UserMasterDTO userInfo = userService.getUserInfo(user.userId());

		// 저장된 사용자의 비밀번호와 입력 받은 비밀번호 비교 결과 저장
		result = userService.matchesPassword(userInfo, user.userPassword());

		return result;
	}

	/**
	 * 사용자 생성 결과를 반환한다.
	 * @param requestData 사용자 등록 요청 데이터
	 * @return 사용자 생성 결과
	 */
	@PostMapping("createUser")
	public boolean createUser(@RequestBody UserMasterDTO requestData) {
		// form에서 전달 받은 데이터가 있을 경우
		if(requestData != null) {
			// UserMasterDTO 값을 List<UserMasterDTO> 값으로 저장
			List<UserMasterDTO> dataList = Collections.singletonList(requestData);

			// UserRoleDTO에 사용자 ID, 기본 권한을 가진 객체 생성
			UserRoleDTO userRole = new UserRoleDTO(requestData.userId(), "user");

			// UserRoleDTO 값을 List<UserRoleDTO>로 저장
			List<UserRoleDTO> roleList = Collections.singletonList(userRole);

			// UserService에서 사용자 및 권한 생성 결과 반환
			result = userService.createUser(dataList, roleList);
		}

		return result;
	}

	/**
	 * 사용자 정보 수정 결과를 반환한다.
	 * @param updateData 수정할 사용자 정보
	 * @return 사용자 정보 저장 결과
	 */
	@PostMapping("updateUserInfo")
	public boolean updateUser(@RequestBody UserMasterDTO updateData) {
		// 입력 받은 데이터가 있을 경우
		if(updateData != null) {
			// DTO -> List 로 변환
			List<UserMasterDTO> updateList = Collections.singletonList(updateData);
			// Service를 통해 정보 수정 후 반환되는 결과 저장
			result = userService.updateUser(updateList);
		}

		return result;
	}

	/**
	 * 사용자 비밀번호 수정 결과를 반환한다.
	 * @param updatePassword 수정할 사용자 아이디와 비밀번호
	 * @return 사용자 비밀번호 수정 결과
	 */
	@PostMapping("updateUserPassword")
	public boolean updateUserPassword(@RequestBody UserMasterDTO updatePassword) {
		// 입력 받은 데이터가 있을 경우
		if(updatePassword != null) {
			// DTO -> List 로 변환
			List<UserMasterDTO> updateList = Collections.singletonList(updatePassword);
			// Service를 통해 정보 수정 후 반환되는 결과 저장
			result = userService.updatePassword(updateList);
		}

		return result;
	}
}