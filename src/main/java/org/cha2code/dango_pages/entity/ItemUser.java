package org.cha2code.dango_pages.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@Entity
@Table(name = "item_user")
@IdClass(ItemUserId.class)
public class ItemUser extends BaseAuditorEntity {
	@Id
	@Column(name = "user_id", nullable = false, length = 40)
	private String userId;

	@Id
	@Column(name = "item_id", columnDefinition = "int UNSIGNED not null")
	private String itemId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private UserMaster userInfo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
	private ItemMaster productInfo;
}
