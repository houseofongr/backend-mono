package com.aoo.admin.adapter.in.web.universe;

import com.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.aoo.common.adapter.out.persistence.entity.HashtagJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseHashtagJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.HashtagJpaRepository;
import com.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.aoo.common.util.GsonUtil.gson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "classpath:sql/clear.sql")
@Sql("UniverseControllerTest.sql")
class PatchUniverseControllerTest extends AbstractControllerTest {

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    HashtagJpaRepository hashtagJpaRepository;

    @Autowired
    EntityManager em;

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("특정 유니버스 정보 수정 API")
    void testUniverseUpdateAPI() throws Exception {
        //language=JSON
        String request = """
                {
                  "title": "오르트구름",
                  "description": "오르트구름은 태양계 최외곽에 위치하고 있습니다.",
                  "authorId": 2,
                  "category": "LIFE",
                  "publicStatus": "PRIVATE",
                  "hashtags": [
                    "오르트구름", "태양계", "윤하", "별"
                  ]
                }
                """;

        mockMvc.perform(patch("/admin/universes/{universeId}", 1)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-patch",
                        pathParameters(
                                parameterWithName("universeId").description("수정할 유니버스의 식별자입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다."),
                                fieldWithPath("description").description("수정할 상세정보입니다."),
                                fieldWithPath("authorId").description("수정할 작성자입니다."),
                                fieldWithPath("category").description("수정할 카테고리입니다."),
                                fieldWithPath("publicStatus").description("공개 여부입니다."),
                                fieldWithPath("hashtags").description("수정할 태그 정보입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : '[#id]번 유니버스의 상세정보가 수정되었습니다.'"),
                                fieldWithPath("authorId").description("수정된 작성자입니다."),
                                fieldWithPath("updatedTime").description("유닉스 타임스탬프 형식의 수정일자입니다."),
                                fieldWithPath("title").description("수정된 제목입니다."),
                                fieldWithPath("description").description("수정된 상세정보입니다."),
                                fieldWithPath("author").description("수정된 작성자의 닉네임입니다."),
                                fieldWithPath("category").description("수정된 카테고리입니다."),
                                fieldWithPath("publicStatus").description("수정된 공개 여부입니다."),
                                fieldWithPath("hashtags").description("수정된 태그 정보입니다.")
                        )
                ));

        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findById(1L).orElseThrow();
        UpdateUniverseCommand command = gson.fromJson(request, UpdateUniverseCommand.class);
        assertThat(universeJpaEntity.getTitle()).isEqualTo(command.title());
        assertThat(universeJpaEntity.getDescription()).isEqualTo(command.description());
        assertThat(universeJpaEntity.getCategory()).isEqualTo(command.category());
        assertThat(universeJpaEntity.getPublicStatus()).isEqualTo(command.publicStatus());
        assertThat(universeJpaEntity.getAuthor().getId()).isEqualTo(command.authorId());

        List<UniverseHashtagJpaEntity> universeHashtags = em.createQuery("select u from UniverseHashtagJpaEntity u", UniverseHashtagJpaEntity.class).getResultList();
        assertThat(universeHashtags).anyMatch(universeHashtagJpaEntity -> universeHashtagJpaEntity.getId().equals(5L))
                .anyMatch(universeHashtagJpaEntity -> universeHashtagJpaEntity.getId().equals(6L))
                .anyMatch(universeHashtagJpaEntity -> universeHashtagJpaEntity.getId().equals(7L));

        List<HashtagJpaEntity> hashtagsInDB = hashtagJpaRepository.findAll();
        String tags = "오르트구름, 태양계, 윤하, 별";
        assertThat(hashtagsInDB).size().isGreaterThan(4);
        assertThat(hashtagsInDB).anyMatch(hashtagJpaEntity -> tags.contains(hashtagJpaEntity.getTag()));

    }
}