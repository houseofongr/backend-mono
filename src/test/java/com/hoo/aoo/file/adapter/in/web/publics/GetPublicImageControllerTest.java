package com.hoo.aoo.file.adapter.in.web.publics;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.restdocs.RestDocumentationContext;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetPublicImageControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Override
    protected String getBaseUrl() {
        return "file.archiveofongr.site";
    }

    @Test
    @DisplayName("이미지파일 다운로드 API")
    void testFile() throws Exception {

        ClassPathResource resource = new ClassPathResource("public/images/logo.png");
        byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
        FileJpaEntity entity = new FileJpaEntity(null, "logo.png", "logo.png", resource.getFile().getParent(), false, (long) bytes.length, null);
        fileJpaRepository.save(entity);

        mockMvc.perform(get("/public/images/{imageId}", entity.getId()))
                .andExpect(status().is(200))
                .andDo(document("file-public-images-download",
                        pathParameters(parameterWithName("imageId").description("조회(다운로드)할 이미지 식별자입니다.")),
                        operation -> {
                            var context = (RestDocumentationContext) operation.getAttributes().get(RestDocumentationContext.class.getName());
                            var path = Paths.get(context.getOutputDirectory().getAbsolutePath(), operation.getName(), "response-file.adoc");
                            var outputStream = new ByteArrayOutputStream();
                            outputStream.write("++++\n".getBytes());
                            outputStream.write("<img src=\"data:image/png;base64,".getBytes());
                            outputStream.write(Base64.getEncoder().encode(operation.getResponse().getContent()));
                            outputStream.write("\"/>\n".getBytes());
                            outputStream.write("++++\n".getBytes());
                            Files.createDirectories(path.getParent());
                            Files.write(path, outputStream.toByteArray());
                        }));
    }

}
