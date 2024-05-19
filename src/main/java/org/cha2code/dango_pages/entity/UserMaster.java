package org.cha2code.dango_pages.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 사용자 테이블과 연동되는 entity class
 */
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@Entity
@Table(name = "user_master")
public class UserMaster extends BaseAuditorEntity {
	@Id
	//@GeneratedValue(generator = "uuid")
	//@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "user_id", nullable = false, length = 40)
	private String userId;

	@Column(name = "user_password", nullable = false)
	private String userPassword;

	@Column(name = "nickname", nullable = false, length = 20)
	private String nickname;

	@Column(name = "email", nullable = false, length = 256)
	private String email;

	// 비밀번호 수정일시
	@Column(name = "password_modified_at")
	private LocalDateTime passwordModifiedAt;

	// 비밀번호 암호화
	public UserMaster encodePassword(PasswordEncoder encoder) {
		if (StringUtils.hasText(this.userPassword)) {
			this.userPassword = encoder.encode(this.userPassword);
		}

		return this;
	}

	// entity -> DTO 변환 메소드 (트랜잭션 처리 예외)
	@Transient
	public UserMasterDTO toDTO() {
		return new UserMasterDTO(userId,
		                         userPassword,
		                         nickname,
		                         email,
		                         passwordModifiedAt,
		                         getCreateUser(),
		                         getCreateDate(),
		                         getModifyUser(),
		                         getModifyDate());
	}

	// 생성자와 생성일자 유무 체크
	@Transient
	public boolean isCreated() {
		return StringUtils.hasText(getCreateUser()) && !ObjectUtils.isEmpty(getCreateDate());
	}
}