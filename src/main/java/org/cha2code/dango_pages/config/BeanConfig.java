package org.cha2code.dango_pages.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 프로젝트 전체에 걸쳐 사용되는 Bean을 정의하는 설정 클래스
 */
@Configuration
public class BeanConfig {

	/**
	 * 단방향 암호화 처리간 사용되는 passwordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
