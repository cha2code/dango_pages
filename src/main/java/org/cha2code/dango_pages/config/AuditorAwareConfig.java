package org.cha2code.dango_pages.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * entity 객체가 생성 및 수정 되었을 때
 * 생성자, 수정자, 생성일시, 수정일시 등을 자동 삽입하기 위해
 * Bean으로 등록하는 Class
 */
@Configuration
@SuppressWarnings("NullableProblems") // 컴파일 시 Null 경고 무시
public class AuditorAwareConfig implements AuditorAware<String> {
	/**
	 * 생성자, 수정자로 들어갈 사용자 정보를 불러와 반환한다.
	 * @return 사용자 정보
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Security session에 정보가 없거나 인증된 사용자가 아닐 경우
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}

		// Spring Security에 저장 된 정보 가져오기
		Object principal = authentication.getPrincipal();

		// principal이 UserDetails 타입이거나 참조하는 경우 사용자 아이디 반환
		if (principal instanceof UserDetails) {
			return Optional.of(((UserDetails) principal).getUsername());
		}

		// 데이터를 새로 생성하여 위 조건을 만족하지 못할 경우 "SYSTEM" 반환
		else {
			return Optional.of("SYSTEM");
		}
	}
}
