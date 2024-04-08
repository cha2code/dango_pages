package org.cha2code.dango_pages.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.cha2code.dango_pages.entity.UserMaster;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.cha2code.dango_pages.entity.UserMaster}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserMasterDto(String username,
                            String userPassword,
                            String nickname,
                            String email,
                            LocalDateTime passwordModifiedAt,
                            String createUser,
                            LocalDateTime createDate,
                            String modifyUser,
                            LocalDateTime modifyDate) implements
		Serializable {
	public UserMaster toEntity() {
		return UserMaster.builder()
		                 .username(username)
		                 .userPassword(userPassword)
		                 .nickname(nickname)
		                 .email(email)
		                 .build();
	}
}