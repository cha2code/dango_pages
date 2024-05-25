package org.cha2code.dango_pages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.UserMasterDTO;
import org.cha2code.dango_pages.dto.UserRoleDTO;
import org.cha2code.dango_pages.entity.UserMaster;
import org.cha2code.dango_pages.entity.UserRole;
import org.cha2code.dango_pages.repository.UserMasterRepository;
import org.cha2code.dango_pages.repository.UserRoleRepository;
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
	private final UserRoleRepository userRoleRepo;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 사용자 ID를 입력받아 해당 사용자의 정보를 반환한다.
	 * @param username 사용자 ID
	 * @return 사용자 정보 DTO, UserMasterDTO
	 */
	public UserMasterDTO getUserInfo(String username) {
		// 입력 받은 사용자 ID 검색
		Optional<UserMaster> userMaster = userRepo.findByUserId(username);

		// UserMaster로 받은 객체를 DTO로 변환 후 결과 반환
		return userMaster.map(UserMaster::toDTO)
		                 .orElse(null);
	}

	/**
	 * 사용자 비밀번호와 입력 받은 비밀번호의 일치 여부 확인 후 결과를 반환한다.
	 * @param userInfo DB에 저장 된 사용자 정보
	 * @param password 입력 받은 비밀번호
	 * @return true/false 비밀번호 일치 여부
	 */
	public boolean matchesPassword(UserMasterDTO userInfo, String password) {
		// 사용자 비밀번호 비교 (수정할 비밀번호, DB에 암호화되어 저장 된 비밀번호)
		if(passwordEncoder.matches(password, userInfo.userPassword())) {
			return true;
		}

		return false;
	}

	/**
	 * 특정 ID에 해당하는 사용자의 수를 반환한다.
	 * @param userId 사용자 ID
	 * @return 해당 ID를 가진 사용자의 수
	 */
	public long countByUserId(String userId) {
		return userRepo.countByUserId(userId);
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
	public boolean createUser(List<UserMasterDTO> dataList, List<UserRoleDTO> userRole) {
		// 사용자 정보를 entity로 변환 후 비밀번호 암호화하여 List에 저장
		List<UserMaster> list = dataList.stream()
										.map(UserMasterDTO::toEntity)
										.map(entity -> entity.encodePassword(passwordEncoder))
										.toList();

		// entity로 변환한 사용자 정보를 DB에 저장
		List<UserMaster> resultList = userRepo.saveAll(list);

		// 사용자 권한 정보를 entity로 변환 후 List에 저장
		List<UserRole> roleList = userRole.stream()
		                                  .map(UserRoleDTO::toEntity)
		                                  .toList();

		// entity로 변환한 사용자 권한 정보를 DB에 저장
		userRoleRepo.saveAll(roleList);

		// 생성자와 생성일자 유무 체크 후 결과 반환
		return resultList.stream().allMatch(UserMaster::isCreated);
	}

	/**
	 * 입력받은 정보로 사용자의 정보를 수정 후 결과를 반환한다.
	 * @param updateList 수정할 사용자 데이터 리스트
	 * @return true/false 수정 결과
	 */
	@Transactional
	public boolean updateUser(List<UserMasterDTO> updateList) {
		// 정보를 수정할 사용자 아이디 저장
		List<String> idList = updateList.stream()
		                              .map(UserMasterDTO::userId)
		                              .toList();

		// 위에서 저장한 사용자 아이디로 DB에서 해당 사용자 검색
		List<UserMaster> targetList = userRepo.findAllById(idList);

		// DB에서 검색한 사용자 정보를 수정 데이터로 업데이트
		for (UserMaster target : targetList) {
			for (UserMasterDTO source : updateList) {
				if (target.getUserId().equals(source.userId())) {
					target.updateData(source.nickname(),
					                  source.email());
				}
			}
		}

		// 수정한 사용자 정보를 DB에 저장
		userRepo.saveAll(targetList);

		return true;
	}

	/**
	 * 비밀번호 수정 후 결과를 반환한다.
	 * @param updateList 수정할 비밀번호 정보 리스트
	 * @return true/false 수정 결과
	 */
	@Transactional
	public boolean updatePassword(List<UserMasterDTO> updateList) {
		// 정보를 수정할 사용자 아이디 저장
		List<String> idList = updateList.stream()
		                                .map(UserMasterDTO::userId)
		                                .toList();

		// 위에서 저장한 사용자 아이디로 DB에서 해당 사용자 검색
		List<UserMaster> targetList = userRepo.findAllById(idList);

		// DB에서 검색한 사용자 정보를 수정 데이터로 업데이트
		for (UserMaster target : targetList) {
			for (UserMasterDTO source : updateList) {
				if (target.getUserId().equals(source.userId())) {
					target.updatePassword(passwordEncoder,
					                      source.userPassword());
				}
			}
		}

		// 수정한 사용자 정보를 DB에 저장
		userRepo.saveAll(targetList);

		return true;
	}
}
