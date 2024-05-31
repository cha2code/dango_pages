package org.cha2code.dango_pages.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.cha2code.dango_pages.entity.ItemMaster;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.cha2code.dango_pages.entity.ItemMaster}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemMasterDTO(Long itemId,
                            Long categoryId,
                            String nickname,
                            String imageUrl,
                            String title,
                            String contents,
                            Long price,
                            LocalDateTime createDate,
                            LocalDateTime modifyDate) implements
		Serializable {
	public ItemMaster toEntity() {
		return ItemMaster.builder()
						 .itemId(itemId)
						 .categoryId(categoryId)
						 .nickname(nickname)
						 .imageUrl(imageUrl)
						 .title(title)
						 .contents(contents)
						 .price(price)
		                 .build();
	}
}