package org.cha2code.dango_pages.repository;

import org.cha2code.dango_pages.entity.UserRole;
import org.cha2code.dango_pages.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 권한 테이블의 CRUD 처리를 위한 메소드를 정의하는 repository interface
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
	// 사용자가 가진 권한들을 찾는 메소드
	List<UserRole> findAllByUsernameIs(String username);
}
