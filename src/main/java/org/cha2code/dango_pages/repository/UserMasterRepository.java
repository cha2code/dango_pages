package org.cha2code.dango_pages.repository;

import org.cha2code.dango_pages.entity.UserMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 사용자 테이블의 CRUD 처리를 위한 메소드를 정의하는 repository interface
 */
public interface UserMasterRepository extends JpaRepository<UserMaster, String> {
	// 사용자를 찾기 위한 메소드
	Optional<UserMaster> findByUsername(String username);
}