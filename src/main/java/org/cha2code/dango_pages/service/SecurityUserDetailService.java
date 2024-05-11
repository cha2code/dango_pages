package org.cha2code.dango_pages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.entity.UserMaster;
import org.cha2code.dango_pages.entity.UserRole;
import org.cha2code.dango_pages.repository.UserMasterRepository;
import org.cha2code.dango_pages.repository.UserRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 정보와 권한을 조회하기 위한 class
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserDetailService implements UserDetailsService {

	private final UserMasterRepository userRepo;
	private final UserRoleRepository userRoleRepo;

	/**
	 * 사용자 ID로 해당 사용자를 찾아 결과를 반환한다.
	 * @param username 사용자 ID
	 * @return User, 사용자 ID/비밀번호/권한
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// repository를 통해 DB에서 해당 ID와 일치하는 사용자 검색
		UserMaster userMaster = userRepo.findByUsername(username)
		                                  .orElseThrow(() ->
											  new UsernameNotFoundException("Cannot found user"));

		// return할 권한을 저장 하는 list 생성
		List<GrantedAuthority> authorities = new ArrayList<>();
		// 사용자 ID와 일치하는 권한 검색 후 list에 저장
		List<UserRole> roleList = userRoleRepo.findAllByRoleCodeIs(username);

		// 권한이 있는 사용자일 경우
		if (!CollectionUtils.isEmpty(roleList)) {
			roleList.stream()
			        .map(UserRole:: getRoleCode)
			        .map(roleCode -> String.format("ROLE_%s", roleCode))
			        .forEach(codeStr -> authorities.add(new SimpleGrantedAuthority(codeStr)));
		}

		log.info("User found: {}", username);
		return new User(userMaster.getUserId(), userMaster.getUserPassword(), authorities);
	}
}