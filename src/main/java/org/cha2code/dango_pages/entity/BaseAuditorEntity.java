package org.cha2code.dango_pages.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditorEntity {
	@CreatedBy
	@Column(name = "create_user", nullable = false, updatable = false, length = 40)
	private String createUser;

	@CreatedDate
	@Column(name = "create_date", nullable = false, updatable = false)
	@JsonFormat
	private LocalDateTime createDate;

	@LastModifiedBy
	@Column(name = "modify_user", insertable = false, length = 40)
	private String modifyUser;

	@LastModifiedDate
	@Column(name = "modify_date", insertable = false)
	private LocalDateTime modifyDate;
}
