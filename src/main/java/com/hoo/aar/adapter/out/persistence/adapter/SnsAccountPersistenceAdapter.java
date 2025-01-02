package com.hoo.aar.adapter.out.persistence.adapter;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aar.application.port.out.database.SaveSnsAccountPort;
import com.hoo.aar.common.enums.ErrorCode;
import com.hoo.aar.common.exception.AarException;
import com.hoo.aar.domain.SnsAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SnsAccountPersistenceAdapter implements LoadSnsAccountPort, SaveSnsAccountPort {

    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<SnsAccountJpaEntity> load(String snsId) {
        return snsAccountJpaRepository.findBySnsId(snsId);
    }

    @Override
    public SnsAccount load(Long id) {
        SnsAccountJpaEntity snsAccountJpaEntity = snsAccountJpaRepository.findById(id)
                .orElseThrow(() -> new AarException(ErrorCode.SNS_ACCOUNT_NOT_FOUND));

        return userMapper.snsAccountJpaEntityToSnsAccount(snsAccountJpaEntity);
    }

    @Override
    public SnsAccountJpaEntity save(SnsAccountJpaEntity snsAccount) {
        return snsAccountJpaRepository.save(snsAccount);
    }
}
