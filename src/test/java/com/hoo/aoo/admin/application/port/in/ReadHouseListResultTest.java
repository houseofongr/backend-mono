package com.hoo.aoo.admin.application.port.in;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class ReadHouseListResultTest {

    @Test
    @DisplayName("하우스 JPA 엔티티로부터 DTO 생성")
    void testCreateHouseDto() {
        // given
        String description = "this is new house. this is new house. this is new house. this is new house. this is new house. this is new house. this is new house. this is new house. this is new house.";
        HouseJpaEntity houseJpaEntity = new HouseJpaEntity(1L, "new house", "leaf", description, 5000, 5000, 1L, 2L, null);
        houseJpaEntity.prePersist();

        // when
        ReadHouseListResult.House houseDto = ReadHouseListResult.House.of(houseJpaEntity);

        // then
        assertThat(houseDto.id()).isEqualTo(houseJpaEntity.getId());
        assertThat(houseDto.title()).isEqualTo(houseJpaEntity.getTitle());
        assertThat(houseDto.author()).isEqualTo(houseJpaEntity.getAuthor());
        assertThat(houseDto.description()).isEqualTo(houseJpaEntity.getDescription().substring(0, 100) + "...");
        assertThat(houseDto.imageId()).isEqualTo(houseJpaEntity.getBasicImageFileId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM.dd. yyyy", Locale.ENGLISH);
        System.out.println("houseDto = " + houseDto.createdDate());
        assertThat(houseDto.createdDate()).isEqualTo(formatter.format(houseJpaEntity.getCreatedTime()));
        assertThat(houseDto.updatedDate()).isEqualTo(formatter.format(houseJpaEntity.getUpdatedTime()));
    }
}