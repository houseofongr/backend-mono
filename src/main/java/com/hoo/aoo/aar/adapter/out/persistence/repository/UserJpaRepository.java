package com.hoo.aoo.aar.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.repository.UserQueryDslRepository;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long>, UserQueryDslRepository {
    Optional<UserJpaEntity> findByPhoneNumber(String phoneNumber);
}
