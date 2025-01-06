package com.hoo.aoo.aar.adapter.out.persistence.adapter;

import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.SaveSnsAccountPort;
import com.hoo.aoo.aar.application.exception.AarErrorCode;
import com.hoo.aoo.aar.application.exception.AarException;
import com.hoo.aoo.aar.domain.SnsAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SnsAccountPersistenceAdapter implements LoadSnsAccountPort, SaveSnsAccountPort {

    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<SnsAccount> load(String snsId) {
        return snsAccountJpaRepository.findBySnsIdWithUserEntity(snsId)
                .map(userMapper::mapToSnsAccountDomainEntity);

    }

    @Override
    public Optional<SnsAccount> load(Long id) {
        return snsAccountJpaRepository.findById(id)
                .map(userMapper::mapToSnsAccountDomainEntity);
    }

    @Override
    public SnsAccount save(SnsAccount snsAccount) {

        SnsAccountJpaEntity newSnsAccountJpaEntity = userMapper.mapToSnsAccountJpaEntity(snsAccount);

        snsAccountJpaRepository.save(newSnsAccountJpaEntity);

        return userMapper.mapToSnsAccountDomainEntity(newSnsAccountJpaEntity);
    }
}
