package org.cha2code.dango_pages.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @EnableWebMvc가 자동으로 세팅해주는 설정에 추가로 설정하는 Configuration
 */
public class WebConfig implements WebMvcConfigurer {
	/**
	 * 정적 리소스에 대한 경로를 설정한다.
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/templates/pages/**")
		        .addResourceLocations("classpath:/static/dist/");
	}
}
