package com.hoo.file.adapter.in.web.publics;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
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

class GetPublicFileControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Override
    protected String getBaseUrl() {
        return "file.archiveofongr.site";
    }

    @Test
    @DisplayName("파일 다운로드 API")
    void downloadFile() throws Exception {

        ClassPathResource resource = new ClassPathResource("public/audios/sample.mp3");
        byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
        FileJpaEntity entity = new FileJpaEntity(null, "sample.mp3", "sample.mp3", resource.getFile().getParent(), false, (long) bytes.length, null);
        fileJpaRepository.save(entity);

        mockMvc.perform(get("/public/{fileId}", entity.getId())
                        .param("attachment", "false"))
                .andExpect(status().is(200))
                .andDo(document("file-public-files-download",
                        pathParameters(
                                parameterWithName("fileId").description("다운로드할 파일 ID입니다."),
                                parameterWithName("attachment").description("첨부파일 여부입니다. +" + "\n" +
                                                                            "* 첨부파일로 설정하면 바로 다운로드를 진행합니다.").optional()
                        ),
                        operation -> {
                            var context = (RestDocumentationContext) operation.getAttributes().get(RestDocumentationContext.class.getName());
                            var path = Paths.get(context.getOutputDirectory().getAbsolutePath(), operation.getName(), "response-file.adoc");
                            var outputStream = new ByteArrayOutputStream();
                            outputStream.write("++++\n".getBytes());
                            outputStream.write("<audio controls type=\"audio/mp3\" src=\"data:audio/mp3;base64,".getBytes());
                            outputStream.write(Base64.getEncoder().encode(operation.getResponse().getContent()));
                            outputStream.write("\"/>\n".getBytes());
                            outputStream.write("++++\n".getBytes());
                            Files.createDirectories(path.getParent());
                            Files.write(path, outputStream.toByteArray());
                        }));
    }

}