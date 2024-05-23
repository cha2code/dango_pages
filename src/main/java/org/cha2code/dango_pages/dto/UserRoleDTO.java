package org.cha2code.dango_pages.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.cha2code.dango_pages.entity.UserRole;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.cha2code.dango_pages.entity.UserRole}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRoleDTO(String userId,
                          String roleCode,
                          String createUser,
                          LocalDateTime createDate,
                          String modifyUser,
                          LocalDateTime modifyDate) implements
		Serializable {
	// UserRole entity로 변환
	public UserRole toEntity() {
		return UserRole.builder()
		               .userId(userId)
		               .roleCode(roleCode)
		               .build();
	}

	// UserRoleDTO 생성자
	public UserRoleDTO(String userId, String roleCode) {
		this(userId, roleCode, null, null, null, null);
	}
}