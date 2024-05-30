package org.cha2code.dango_pages.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemUserId implements Serializable {
	private static final long serialVersionUID = 5019515664106731614L;

	@Column(name = "user_id", nullable = false, length = 40)
	private String userId;

	@Column(name = "item_id", columnDefinition = "int UNSIGNED not null")
	private Long itemId;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		ItemUserId entity = (ItemUserId) o;
		return Objects.equals(this.userId, entity.userId) &&
				Objects.equals(this.itemId, entity.itemId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, itemId);
	}
}
