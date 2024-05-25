package org.cha2code.dango_pages.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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

	/**
	 * update 되는 사용자 정보를 반환한다.
	 * @param nickname 사용자 닉네임
	 * @param email 사용자 이메일
	 * @return 사용자 정보
	 */
	public UserMaster updateData(String nickname, String email) {
		if (StringUtils.hasText(nickname) && !this.nickname.equals(nickname)) {
			this.nickname = nickname;
		}

		if (StringUtils.hasText(email) && !this.email.equals(email)) {
			this.email = email;
		}

		return this;
	}

	/**
	 * 수정되는 사용자 비밀번호를 암호화 후 반환한다.
	 * @param encoder 비밀번호 암호화
	 * @param userPassword 사용자 비밀번호
	 * @return 사용자의 비밀번호 정보
	 */
	public UserMaster updatePassword(PasswordEncoder encoder, String userPassword) {
		if (StringUtils.hasText(userPassword) && !encoder.matches(userPassword, this.userPassword)) {
			this.userPassword = encoder.encode(userPassword);
			this.passwordModifiedAt = LocalDateTime.now();
		}

		return this;
	}

	/**
	 * entity -> DTO로 변환 후 반환한다.
	 * @return UserMasterDTO 사용자 DTO 객체
	 */
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

	/**
	 * 생성일자 및 생성자 유무를 체크 후 반환한다.
	 * @return true/false
	 */
	@Transient
	public boolean isCreated() {
		return StringUtils.hasText(getCreateUser()) && !ObjectUtils.isEmpty(getCreateDate());
	}
}