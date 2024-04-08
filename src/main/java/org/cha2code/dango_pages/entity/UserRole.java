package org.cha2code.dango_pages.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 사용자 권한 테이블과 연동되는 entity class
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "user_role")
@IdClass(UserRoleId.class)
public class UserRole {
	@Id
	@Column(name = "user_id", nullable = false, length = 40)
	private String username;

	@Id
	@Column(name = "role_code", nullable = false, length = 40)
	private String roleCode;

	@CreatedBy
	@Column(name = "create_user", nullable = false, updatable = false, length = 40)
	private String createUser;

	@CreatedDate
	@Column(name = "create_date", nullable = false, updatable = false)
	@JsonFormat
	private LocalDateTime createDate;
}