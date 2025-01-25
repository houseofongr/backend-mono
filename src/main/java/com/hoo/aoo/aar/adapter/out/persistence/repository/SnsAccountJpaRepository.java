package com.hoo.aoo.aar.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface SnsAccountJpaRepository extends JpaRepository<SnsAccountJpaEntity, Long> {

    @Query("select s from SnsAccountJpaEntity s left join fetch s.userEntity " +
            "where s.snsDomain = :domain and s.snsId = :snsId")
    Optional<SnsAccountJpaEntity> findWithUserEntity(SnsDomain domain, String snsId);

    @Query("select s from SnsAccountJpaEntity s inner join s.userEntity on s.userEntity.id = :userId")
    List<SnsAccountJpaEntity> findAllByUserId(Long userId);

}
