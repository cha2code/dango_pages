package org.cha2code.dango_pages.repository;

import org.cha2code.dango_pages.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 사용자 테이블의 CRUD 처리를 위한 메소드를 정의하는 repository interface
 */
public interface UserMasterRepository extends JpaRepository<UserMaster, String> {
	// 사용자를 찾기 위한 메소드
	@Query(nativeQuery = true, value = "SELECT * FROM user_master where user_id = :username")
	Optional<UserMaster> findByUserId(@Param("username") String username);

	// 닉네임 검색을 위한 메소드
	@Query(nativeQuery = true, value = "SELECT * FROM user_master WHERE nickname = :nickname")
	Optional<UserMaster> findByNickname(@Param("nickname") String nickname);

	// 이메일 검색을 위한 메소드
	@Query(nativeQuery = true, value = "SELECT * FROM user_master WHERE email = :email")
	Optional<UserMaster> findByEmail(@Param("email") String email);

	long countByUserId(String userId);
	long countByNickname(String nickname);
	long countByEmail(String email);
}