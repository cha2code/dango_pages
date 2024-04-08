package org.cha2code.dango_pages.repository;

import org.cha2code.dango_pages.entity.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 권한 테이블의 CRUD 처리를 위한 메소드를 정의하는 repository interface
 */
public interface RoleMasterRepository extends JpaRepository<RoleMaster, String> {
	// 권한을 찾기 위한 메소드
	Optional<RoleMaster> findByRoleCode(String roleCode);
}