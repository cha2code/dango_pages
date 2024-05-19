package org.cha2code.dango_pages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.cha2code.dango_pages.entity.UserMaster;
import org.cha2code.dango_pages.repository.UserMasterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 정보를 CRUD 하기 위한 service class
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserMasterRepository userRepo;
	private final PasswordEncoder passwordEncoder;

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
	public UserMasterDTO getUserInfo(String username) {
		// 입력 받은 사용자 ID 검색
		Optional<UserMaster> userMaster = userRepo.findByUserId(username);

		return userMaster.map(UserMaster::toDTO)
		                 .orElse(null);
	}

	/**
	 * 특정 닉네임에 해당하는 사용자의 수를 반환한다.
	 * @param nickname 사용자 닉네임
	 * @return 해당 닉네임을 가진 사용자의 수
	 */
	public long countByNickname(String nickname) {
		return userRepo.countByNickname(nickname);
	}

	/**
	 * 특정 이메일에 해당하는 사용자의 수를 반환한다.
	 * @param email 사용자 이메일
	 * @return 해당 이메일을 가진 사용자의 수
	 */
	public long countByEmail(String email) {
		return userRepo.countByEmail(email);
	}

	/**
	 * 사용자를 DB에 등록 후 결과를 반환한다.
	 * @param dataList 사용자 정보
	 * @return true/false
	 */
	@Transactional
	public boolean createData(List<UserMasterDTO> dataList) {
		List<UserMaster> list = dataList.stream()
										.map(UserMasterDTO::toEntity)
										.map(entity -> entity.encodePassword(passwordEncoder))
										.toList();

		List<UserMaster> resultList = userRepo.saveAll(list);

		return resultList.stream().allMatch(UserMaster::isCreated);
	}
}
