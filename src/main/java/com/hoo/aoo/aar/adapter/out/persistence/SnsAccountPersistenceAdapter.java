package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.application.port.out.persistence.snsaccount.FindSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.persistence.snsaccount.SaveSnsAccountPort;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsDomain;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SnsAccountPersistenceAdapter implements FindSnsAccountPort, SaveSnsAccountPort {

    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final SnsAccountMapper snsAccountMapper;

    @Override
    public Optional<SnsAccount> load(SnsDomain domain, String snsId) {
        return snsAccountJpaRepository.findWithUserEntity(domain, snsId)
                .map(snsAccountMapper::mapToDomainEntity);
    }

    @Override
    public Optional<SnsAccount> load(Long id) {
        return snsAccountJpaRepository.findById(id)
                .map(snsAccountMapper::mapToDomainEntity);
    }

    @Override
    public void save(SnsAccount snsAccount) {
        SnsAccountJpaEntity newSnsAccountJpaEntity = snsAccountMapper.mapToNewJpaEntity(snsAccount);
        snsAccountJpaRepository.save(newSnsAccountJpaEntity);
    }

}
