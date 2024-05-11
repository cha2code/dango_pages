package org.cha2code.dango_pages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.UserMasterDto;
import org.cha2code.dango_pages.entity.UserMaster;
import org.cha2code.dango_pages.repository.UserMasterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 사용자 정보를 CRUD처리 하기 위한 service class
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserMasterRepository userRepo;

	/**
	 * 특정 ID에 해당하는 사용자의 수를 반환한다.
	 * @param userId 사용자 ID
	 * @return 해당 ID를 가진 사용자의 수
	 */
	public long countByUserId(String userId) {
		return userRepo.countByUserId(userId);
	}

	/**
	 * 사용자 ID를 입력받아 해당 사용자의 정보를 반환한다.
	 * @param username 사용자 ID
	 * @return 사용자 정보 DTO, UserMasterDTO
	 */
	public UserMasterDto getUserInfo(String username) {
		// 입력 받은 사용자 ID 검색
		Optional<UserMaster> userMaster = userRepo.findByUsername(username);

		return userMaster.map(UserMaster::toDTO)
		                 .orElse(null);
	}
}
