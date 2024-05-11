package org.cha2code.dango_pages.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * 사용자 권한 테이블의 복합키 설정을 위한 IdClass
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRoleId implements Serializable {
	private static final long serialVersionUID = 6636787881606024025L;

	@Column(name = "user_id", nullable = false, length = 40)
	private String userId;

	@Column(name = "role_code", nullable = false, length = 40)
	private String roleCode;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		UserRoleId entity = (UserRoleId) o;
		return Objects.equals(this.roleCode, entity.roleCode) &&
				Objects.equals(this.userId, entity.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, roleCode);
	}

}