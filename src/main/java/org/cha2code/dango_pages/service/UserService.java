package org.cha2code.dango_pages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cha2code.dango_pages.dto.UserMasterDto;
import org.cha2code.dango_pages.entity.UserMaster;
import org.cha2code.dango_pages.repository.UserMasterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 정보를 CRUD처리 하기 위한 service class
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserMasterRepository repo;
	private final PasswordEncoder passwordEncoder;

	// 사용자를 검색하기 위한 메소드
	public UserMasterDto getUserInfo(String username) {
		return repo.findById(username)
		           .map(UserMaster::toDTO)
		           .orElse(null);
	}
}
