package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.RoomMapper;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({RoomPersistenceAdapter.class, RoomMapper.class, ItemMapper.class, SoundSourceMapper.class})
class RoomPersistenceAdapterTest {

    @Autowired
    RoomPersistenceAdapter sut;

    @Autowired
    RoomJpaRepository roomJpaRepository;

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 수정 테스트")
    void testUpdateRoomInfo() {
        // given
        UpdateRoomInfoCommand command = new UpdateRoomInfoCommand(List.of(
                new UpdateRoomInfoCommand.RoomInfo(1L, "욕실")
        ));

        // when
        sut.update(command);
        Optional<QueryRoomResult> query = sut.findResult(1L);

        // then
        assertThat(query).isNotEmpty();
        assertThat(query.get().room().name()).isEqualTo("욕실");
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 조회 테스트")
    void testFindResult() {
        // given
        Long id = 2L;

        // when
        Optional<QueryRoomResult> result = sut.findResult(id);
        Optional<QueryRoomResult> result2 = sut.findResult(id);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().room().name()).isEqualTo("주방");
        assertThat(result.get().room().width()).isEqualTo(5000);
        assertThat(result.get().room().height()).isEqualTo(1000);
        assertThat(result.get().room().imageId()).isEqualTo(6);

        // not found
        assertThat(result2.isEmpty());
    }


    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 삭제 테스트")
    void testDeleteHouseRoomRoom() {
        // given
        Long roomId = 1L;

        // when
        sut.deleteRoom(roomId);

        // then
        assertThat(roomJpaRepository.findById(roomId)).isEmpty();
    }
}