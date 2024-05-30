package org.cha2code.dango_pages.repository;

import org.cha2code.dango_pages.entity.ItemUser;
import org.cha2code.dango_pages.entity.ItemUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemUserRepository extends JpaRepository<ItemUser, ItemUserId> {
}
