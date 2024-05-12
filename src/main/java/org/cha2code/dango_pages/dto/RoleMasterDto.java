package org.cha2code.dango_pages.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.cha2code.dango_pages.entity.RoleMaster;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.cha2code.dango_pages.entity.RoleMaster}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RoleMasterDto(String roleCode,
                            String roleName,
                            String memo,
                            String createUser,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createDate,
                            String modifyUser,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modifyDate) implements
		Serializable {
	public RoleMaster toEntity() {
		return RoleMaster.builder()
		                 .roleCode(roleCode)
		                 .roleName(roleName)
		                 .memo(memo)
		                 .build();
	}

	public RoleMasterDto(String roleCode, String roleName, String memo) {
		this(roleCode, roleName, memo, null, null, null, null);
	}
}