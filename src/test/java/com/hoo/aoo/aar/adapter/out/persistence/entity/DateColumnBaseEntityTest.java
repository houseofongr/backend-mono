package com.hoo.aoo.aar.adapter.out.persistence.entity;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.domain.SnsAccountF;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DateColumnBaseEntityTest {

    @Autowired
    SnsAccountJpaRepository repository;

    UserMapper mapper = new UserMapper();

    @Test
    @DisplayName("DB 시간과 동기화 확인")
    void testSyncTime() throws InvalidPhoneNumberException {
        SnsAccountJpaEntity entity = mapper.mapToNewJpaEntity(SnsAccountF.NOT_REGISTERED_KAKAO.get());
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        repository.save(entity);

        SnsAccountJpaEntity snsAccountJpaEntity = repository.findById(entity.getId()).get();

        assertThat(snsAccountJpaEntity.getCreatedTime()).isCloseTo(now, new TemporalUnitWithinOffset(1, ChronoUnit.SECONDS));
        assertThat(snsAccountJpaEntity.getUpdatedTime()).isCloseTo(now, new TemporalUnitWithinOffset(1, ChronoUnit.SECONDS));
    }
}