package org.cha2code.dango_pages.dto;

import lombok.Data;

/**
 * 이메일 인증 코드 전송 시 변경 되는 정보를 저장하기 위한 DTO class
 */
@Data
public class MailCheckDTO {
	// 입력 받은 이메일 주소
	private String email;
	// 받는 사람
	private String receiver;
	// 메일 제목
	private String title;
	// 메일 내용
	private String content;
	// 생성 된 인증 코드
	private String checkCode;
}
