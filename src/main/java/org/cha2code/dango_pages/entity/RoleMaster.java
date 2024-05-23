package org.cha2code.dango_pages.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cha2code.dango_pages.dto.RoleMasterDTO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;

/**
 * 권한 테이블과 연동되는 entity class
 */
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@Entity
@Table(name = "role_master")
public class RoleMaster extends BaseAuditorEntity {
	@Id
	@Column(name = "role_code", nullable = false, length = 40)
	private String roleCode;

	@Column(name = "role_name", nullable = false, length = 40)
	private String roleName;

	@Column(name = "memo", length = 200)
	private String memo;

	@Transient
	public RoleMasterDTO toDTO() {
		return new RoleMasterDTO(roleCode,
		                         roleName,
								 memo,
		                         getCreateUser(),
		                         getCreateDate(),
		                         getModifyUser(),
		                         getModifyDate());
	}

	/** 권한 이름, 메모 수정을 위한 method */
	public RoleMaster updateData(String roleName, String memo) {
		if (StringUtils.hasText(roleName) && !this.roleName.equals(roleName)) {
			this.roleName = roleName;
		}

		if (StringUtils.hasText(memo) && !this.memo.equals(memo)) {
			this.memo = memo;
		}

		return this;
	}

	@Transient
	public boolean isCreated() {
		return StringUtils.hasText(getCreateUser()) && !ObjectUtils.isEmpty(getCreateDate());
	}
}