package com.hoo.aoo.admin.domain.universe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniverseTest {

    @Test
    @DisplayName("유니버스 생성")
    void testCreateUniverse() {
        // given
        Long id = 1L;
        String title = "우주";
        String description = "유니버스는 우주입니다.";
        List<String> tag = List.of("우주", "행성", "지구", "별");
        Category category = Category.GOVERNMENT_AND_PUBLIC_INSTITUTION;
        PublicStatus publicStatus = PublicStatus.PUBLIC;
        Long thumbnailId = 11L;
        Long thumbMusicId = 100L;

        // when
        Universe universe = Universe.create(id, title, description, tag, category, publicStatus, thumbnailId, thumbMusicId);
        UniverseBasicInfo basicInfo = universe.getBasicInfo();
        SocialInfo socialInfo = universe.getSocialInfo();

        // then
        assertThat(universe.getId()).isEqualTo(id);
        assertThat(universe.getThumbnailId()).isEqualTo(thumbnailId);
        assertThat(universe.getThumbMusicId()).isEqualTo(thumbMusicId);
        assertThat(universe.getDateInfo()).isNull();
        assertThat(universe.getTreeInfo()).isNull();
        assertThat(basicInfo.getPublicStatus()).isEqualTo(publicStatus);
        assertThat(basicInfo.getTitle()).isEqualTo(title);
        assertThat(basicInfo.getDescription()).isEqualTo(description);
        assertThat(basicInfo.getCategory()).isEqualTo(category);
        assertThat(socialInfo.getHashtags()).hasSize(4);
        assertThat(socialInfo.getHashtags()).contains(tag.toArray(String[]::new));
        assertThat(socialInfo.getLikeCount()).isEqualTo(0L);
        assertThat(socialInfo.getViewCount()).isEqualTo(0L);
    }

}