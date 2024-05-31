package org.cha2code.dango_pages.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 상품과 사용자 아이디 정보를 가진 테이블과 연동되는 entity class
 */
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
	private Long itemId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private UserMaster userInfo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
	private ItemMaster productInfo;
}
