package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.hoo.aoo.aar.application.port.out.user.FindUserPort;
import com.hoo.aoo.aar.application.port.out.user.SaveUserPort;
import com.hoo.aoo.aar.application.service.AarErrorCode;
import com.hoo.aoo.aar.application.service.AarException;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("AARUserPersistenceAdapter")
@AllArgsConstructor
public class UserPersistenceAdapter implements FindUserPort, SaveUserPort {

    private final UserJpaRepository userJpaRepository;
    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final UserMapper userMapper;

    @Override
    public QueryMyInfoResult queryMyInfo(Long userId) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow();
        Integer homeCount = userJpaRepository.countHomeCountById(userId);
        Integer soundSourceCount = userJpaRepository.countActiveSoundSourceCountById(userId);
        return userMapper.mapToQueryMyInfoResult(userJpaEntity, homeCount, soundSourceCount);
    }

    @SneakyThrows(InvalidPhoneNumberException.class)
    @Override
    public Optional<User> load(Long id) {
        Optional<UserJpaEntity> optional = userJpaRepository.findById(id);

        if (optional.isEmpty())
            return Optional.empty();
        else
            return Optional.of(userMapper.mapToDomainEntity(optional.get()));
    }

    @Override
    public Long save(User user) {

        List<SnsAccountJpaEntity> snsAccountJpaEntities = user.getSnsAccounts().stream().map(snsAccount ->
                snsAccountJpaRepository.findWithUserEntity(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId())
                        .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND))
        ).toList();

        UserJpaEntity entity = userMapper.mapToNewJpaEntity(user, snsAccountJpaEntities);

        userJpaRepository.save(entity);

        return entity.getId();
    }
}
