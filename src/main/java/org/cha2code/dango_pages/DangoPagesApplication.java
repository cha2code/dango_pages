package org.cha2code.dango_pages;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableEncryptableProperties
public class DangoPagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DangoPagesApplication.class, args);
	}

}
