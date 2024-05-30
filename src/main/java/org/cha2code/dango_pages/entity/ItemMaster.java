package org.cha2code.dango_pages.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cha2code.dango_pages.dto.ItemMasterDto;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;

/**
 * 카테고리별 게시판 테이블과 연동되는 entity
 */
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@Entity
@Table(name = "item_master")
public class ItemMaster extends BaseAuditorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id", columnDefinition = "int UNSIGNED not null")
	private Long itemId;

	@Column(name = "category_id", columnDefinition = "int UNSIGNED not null")
	private Long categoryId;

	@Column(name = "nickname", nullable = false, length = 20)
	private String nickname;

	@Column(name = "title", nullable = false, length = 120)
	private String title;

	@Column(name = "contents", nullable = false)
	private String contents;

	@Column(name = "price", columnDefinition = "int UNSIGNED not null")
	private Long price;

	/**
	 * entity -> DTO로 변환 후 반환한다.
	 * @return ProductMasterDTO DTO 객체
	 */
	@Transient
	public ItemMasterDto toDTO() {
		return new ItemMasterDto(itemId,
		                         categoryId,
								 nickname,
		                         title,
		                         contents,
								 price,
		                         getCreateDate(),
		                         getModifyDate());
	}

	/**
	 * 생성일자 유무 체크 후 반환한다.
	 * @return true/false
	 */
	@Transient
	public boolean isCreated() {
		return StringUtils.hasText(getCreateUser()) && !ObjectUtils.isEmpty(getCreateDate());
	}
}
