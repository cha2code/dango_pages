package org.cha2code.dango_pages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.MailCheckDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 이메일 인증 시 발송할 인증 코드 생성 및 메일을 전송하는 Service Class
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;

	// application.yml에 환경변수로 설정한 이메일 아이디
	@Value("${mail.username}")
	private String sender;

	/**
	 * 6자리 인증 코드를 생성 후 반환한다.
	 * @return checkCode 생성 된 문자열 인증 코드
	 */
	public String createRandomCode() {
		String[] StringSet = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
				"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
		};

		// 인증 코드를 저장하기 위한 StringBuffer
		StringBuffer createCode = new StringBuffer();

		// 랜덤으로 생성 된 인덱스 저장을 위한 변수
		int randIndex = 0;

		// StringSet에 저장 된 문자들 중 6개를 랜덤으로 뽑아 코드 생성
		for(int i = 0; i < 6; i++){
			//0 <= x < 1 실수를 리턴하기 때문에 int로 형변환
			randIndex = (int)(StringSet.length * Math.random());
			// 해당 위치의 문자열 하나씩 저장
			createCode.append(StringSet[randIndex]);
		}

		// 생성 된 코드를 반환
		return createCode.toString();
	}

	/**
	 * 이메일 인증 시 보낼 메일 내용을 설정한다.
	 * @param mailCheckDTO 메일 주소, 메일 내용에 대한 데이터
	 */
	public void CreateMail(MailCheckDTO mailCheckDTO) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mail, true, "UTF-8");

		// 전송할 메일 정보 저장
		message.setFrom(sender);
		message.setTo(mailCheckDTO.getReceiver());
		message.setSubject("dango 인증 번호 발송 메일");
		message.setText(setContext(mailCheckDTO.getCheckCode()), true);

		// 메일 전송
		javaMailSender.send(mail);
	}

	/**
	 * html로 작성된 내용에 생성한 인증 코드를 추가 후 반환한다.
	 * @param checkCode 생성 된 인증 코드
	 * @return emailAuthentication 메일 발송 내용
	 */
	private String setContext(String checkCode) {
		Context context = new Context();
		context.setVariable("checkCode", checkCode);

		return templateEngine.process("emailAuthentication", context);
	}
}