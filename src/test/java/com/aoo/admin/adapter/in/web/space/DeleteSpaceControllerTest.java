package com.aoo.admin.adapter.in.web.space;

import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.aoo.common.domain.Authority;
import com.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.aoo.file.domain.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/clear.sql")
@Sql("SpaceControllerTest.sql")
class DeleteSpaceControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Test
    @DisplayName("스페이스 삭제 API")
    void testDeleteUniverse() throws Exception {

        // Universe Thumb music(= 1)
        saveFile(FileF.AUDIO_FILE_1.get(tempDir.toString()));

        // [Universe Thumbnail + innerImage](= 2) + Space innerImage(= 5) + Piece innerImage(= 1)
        for (int i = 0; i < 8; i++) {
            saveFile(File.create(FileId.create(tempDir.toString(), Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE, String.format("test%d.png", i), String.format("test-123%d.png", i)), FileStatus.CREATED, null,new FileSize(1234L, 10000L)));
        }

        // Sound audio(= 11)
        for (int i = 10; i <= 20; i++) {
            saveFile(File.create(FileId.create(tempDir.toString(), Authority.PUBLIC_FILE_ACCESS, FileType.AUDIO, String.format("test%d.mp3", i), String.format("test-13%d.mp3", i)), FileStatus.CREATED, null,new FileSize(1234L, 10000L)));
        }

        mockMvc.perform(delete("/admin/spaces/{spaceId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-space-delete",
                        pathParameters(
                                parameterWithName("spaceId").description("삭제할 스페이스의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : '[#id]번 스페이스가 삭제되었습니다.'"),
                                fieldWithPath("deletedSpaceIds").description("삭제된 스페이스 식별자 리스트입니다."),
                                fieldWithPath("deletedPieceIds").description("삭제된 피스 식별자 리스트입니다."),
                                fieldWithPath("deletedSoundIds").description("삭제된 사운드 식별자 리스트입니다."),
                                fieldWithPath("deletedImageFileIds").description("삭제된 이미지 파일 식별자 리스트입니다."),
                                fieldWithPath("deletedAudioFileIds").description("삭제된 오디오 파일 식별자 리스트입니다.")
                        )
                ));

        // 파일개수 체크 : thumbMusic(audio : 1) | thumbnail + innerImage + spaces(1, 3)'s innerImage(image : 4)
        List<FileJpaEntity> files = fileJpaRepository.findAll();
        assertThat(files).hasSize(5);
        assertThat(files.stream().filter(fileJpaEntity -> fileJpaEntity.getAbsolutePath().contains("images"))).hasSize(4);
        assertThat(files.stream().filter(fileJpaEntity -> fileJpaEntity.getAbsolutePath().contains("audios"))).hasSize(1);

        // 스페이스 체크 : space1, space3
        List<SpaceJpaEntity> spaces = em.createQuery("select s from SpaceJpaEntity s where s.universeId = 1", SpaceJpaEntity.class).getResultList();
        assertThat(spaces).hasSize(2)
                .noneMatch(spaceJpaEntity -> spaceJpaEntity.getId().equals(2L))
                .noneMatch(spaceJpaEntity -> spaceJpaEntity.getId().equals(4L))
                .noneMatch(spaceJpaEntity -> spaceJpaEntity.getId().equals(5L));

        // 피스 체크 : piece1(-> universe), 2(-> space1), 3(-> space3)
        List<PieceJpaEntity> pieces = em.createQuery("select s from PieceJpaEntity s where s.universeId = 1", PieceJpaEntity.class).getResultList();
        assertThat(pieces).hasSize(3)
                .noneMatch(pieceJpaEntity -> pieceJpaEntity.getId().equals(4L))
                .noneMatch(pieceJpaEntity -> pieceJpaEntity.getId().equals(5L))
                .noneMatch(pieceJpaEntity -> pieceJpaEntity.getId().equals(6L))
                .noneMatch(pieceJpaEntity -> pieceJpaEntity.getId().equals(7L));

        // 사운드 체크 : all removed
        List<SoundJpaEntity> sounds = em.createQuery("select s from SoundJpaEntity s", SoundJpaEntity.class).getResultList();
        assertThat(sounds).isEmpty();
    }
}