package org.cha2code.dango_pages.dto;

import lombok.Data;

/**
 * 회원 정보의 중복 체크를 위해 입력 받은 값을 전달하는 DTO Class
 */
@Data
public class UserExistCheckDTO {
	private String userId;
}
