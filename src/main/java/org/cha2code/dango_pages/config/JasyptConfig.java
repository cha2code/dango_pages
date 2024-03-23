package org.cha2code.dango_pages.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jasypt 라이브러리를 활용해 데이터의 암/복호화 처리 Bean을 생성하는 설정 클래스
 *
 * @author cha2jini
 */
@Slf4j
@Configuration
public class JasyptConfig {
    /** 환경변수에 대한 비밀번호 저장 */
    @Value("${jasypt.encryptor.password}")
    String password;

    /**
     * Jasypt 라이브러리로 문자열 암/복호화를 처리하는 StringEncryptor 객체를 반환한다.
     *
     * @return StringEncryptor
     */
    @Bean
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(password);
        // 암호화 알고리즘
        config.setAlgorithm("PBEWithMD5AndDES");
        // 반복할 해싱 회수
        config.setKeyObtentionIterations("1000");
        // 인스턴스 pool
        config.setPoolSize("1");

        config.setProviderName("SunJCE");
        // salt 생성 클래스
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        //인코딩 방식
        config.setStringOutputType("base64");

        encryptor.setConfig(config);

        log.info("jasypt encryptor bean created!");

        return encryptor;
    }
}
