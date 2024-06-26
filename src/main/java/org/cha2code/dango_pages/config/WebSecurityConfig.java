package org.cha2code.dango_pages.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Security 설정을 위한 Configuration
 */
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * 접근 권한, 로그인/로그아웃, 성공/실패 핸들러 설정을 위한 Bean 등록
	 * @param http HttpSecurity
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf()
		                // 사용자 세션에 csrf 토큰 저장
		                .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
		           .and()
		           .authorizeRequests()
		                // 정적 리소스 모두 허용
						.antMatchers("/common/**", "/dist/**","/plugins/**").permitAll()
		                // 로그인, 회원가입 페이지는 익명 사용자만 접근 가능
		                .antMatchers("/login", "/signup").anonymous()
		                // 인증된 사용자만 접근 가능
						.antMatchers("/userInfo", "/updateNickname", "/updatePassword").authenticated()
						.anyRequest().permitAll()
		           .and()
		           // 로그인 설정
		           .formLogin()
		                // 로그인 페이지 URL
		                .loginPage("/login")
		                // 로그인 페이지에서 전달하는 ID 파라미터의 명칭
		                .usernameParameter("username")
		                // 로그인 페이지에서 전달하는 비밀번호 파라미터의 명칭
		                .passwordParameter("password")
		                // 로그인 성공시
		                .successHandler((request, response, authentication) -> {
				           request.getSession().setAttribute("userInfo", authentication.getPrincipal());
				           log.info("authentication : " + authentication.getName());
				           response.sendRedirect("/");
		                })
		                 // 로그인 실패시
		                .failureHandler((request, response, exception) -> {
			                log.info("exception : " + exception.getMessage());
			                response.sendRedirect("/login");
		                })
		           .and()
		           // 로그아웃 설정
		           .logout()
		                .logoutUrl("/logout")
		                .invalidateHttpSession(true)
		                .deleteCookies("JSESSIONID")
		                .logoutSuccessUrl("/")
		           .and()
		           .rememberMe()
		                .rememberMeParameter("remember-me")
						.tokenValiditySeconds(604800) // 쿠키 만료 시간 (7일)
						.alwaysRemember(false) // 사용자가 체크할 때만 쿠키 저장
						.userDetailsService(userDetailsService) // 저장할 사용자 정보
		           .and()
		           .build();
	}

	/**
	 * userId, password가 유효한 지 검사하기 위한 Bean 등록
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}
}
