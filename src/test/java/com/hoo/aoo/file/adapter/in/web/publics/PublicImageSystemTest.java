package com.hoo.aoo.file.adapter.in.web.publics;

import com.hoo.aoo.common.adapter.in.web.config.SystemTest;
import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.domain.FileSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SystemTest
public class PublicImageSystemTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    FileJpaRepository fileJpaRepository;

    @TempDir
    java.io.File tempDir;

    @Autowired
    FileAttribute fileAttribute;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(fileAttribute, "publicImagePath", tempDir.getPath());
    }

    @Test
    @DisplayName("tc : 정상 이미지 업로드")
    void testUpload() throws IOException {

        /* 1. 파일 이미지 업로드[/test/resources/logo.png] */

        Resource imageResource = new ClassPathResource("logo.png");
        MockMultipartFile imageFile = new MockMultipartFile("logo.png", "logo.png", "image/png", imageResource.getContentAsByteArray());

        ResponseEntity<?> responseEntity = whenUpload(List.of(imageFile));

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(201);
        assertThat(responseEntity.getBody()).isNotNull().isInstanceOf(UploadImageResult.class);

        /* 2. 응답에서 파일 정보 확인 */

        UploadImageResult result = (UploadImageResult) responseEntity.getBody();

        assertThat(result.fileInfos()).anySatisfy(fileInfo -> {
            assertThat(fileInfo.name()).isEqualTo("logo.png");
            assertThat(fileInfo.size()).isEqualTo(new FileSize(20682L).getUnitSize());
            assertThat(fileInfo.id()).isNotNull();
        });

        /* 3.파일 DB 저장여부 확인 */

        Long fileId = result.fileInfos().getFirst().id();

        assertThat(fileJpaRepository.findById(fileId)).isNotEmpty();

        /* 4. 동일 이미지 재업로드 시 409 오류 */

        ResponseEntity<?> responseEntity2 = whenUpload(List.of(imageFile));

        assertThat(responseEntity2.getStatusCode().value()).isEqualTo(409);
    }

    private ResponseEntity<?> whenUpload(List<MultipartFile> files) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        files.forEach(file -> body.add("images", file.getResource()));

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange("/public/images", HttpMethod.POST, entity, UploadImageResult.class);
    }
}
