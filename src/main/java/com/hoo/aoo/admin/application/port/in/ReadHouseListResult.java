package com.hoo.aoo.admin.application.port.in;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import org.springframework.data.domain.Page;

public record ReadHouseListResult(
        Page<House> houses
) {
    public record House(
            Long id,
            String title,
            String author,
            String description,
            String createdDate,
            String updatedDate,
            Long imageId
    ) {

        public static House of(HouseJpaEntity houseJpaEntity) {
            return new House(
                    houseJpaEntity.getId(),
                    houseJpaEntity.getTitle(),
                    houseJpaEntity.getAuthor(),
                    houseJpaEntity.getDescription().length() > 100 ? houseJpaEntity.getDescription().substring(0, 100) + "..." : houseJpaEntity.getDescription(),
                    DateTimeFormatters.ENGLISH_DATE.getFormatter().format(houseJpaEntity.getCreatedTime()),
                    DateTimeFormatters.ENGLISH_DATE.getFormatter().format(houseJpaEntity.getUpdatedTime()),
                    houseJpaEntity.getBasicImageFileId());
        }
    }
}
