package com.hoo.aar.adapter.out.persistence.entity;

import com.hoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aar.domain.SnsAccount;
import com.hoo.aar.domain.SnsAccountF;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DateColumnBaseEntityTest {

    @Autowired
    SnsAccountJpaRepository repository;

    UserMapper mapper = new UserMapper();

    @Test
    @DisplayName("DB 시간과 동기화 확인")
    void testSyncTime() {
        SnsAccountJpaEntity entity = mapper.mapToSnsAccountJpaEntity(SnsAccountF.KAKAO_NOT_REGISTERED_WITH_NO_ID.get());
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        repository.save(entity);

        SnsAccountJpaEntity snsAccountJpaEntity = repository.findById(entity.getId()).get();

        assertThat(snsAccountJpaEntity.getCreatedDate()).isCloseTo(now, new TemporalUnitWithinOffset(1, ChronoUnit.SECONDS));
        assertThat(snsAccountJpaEntity.getUpdatedDate()).isCloseTo(now, new TemporalUnitWithinOffset(1, ChronoUnit.SECONDS));
    }
}