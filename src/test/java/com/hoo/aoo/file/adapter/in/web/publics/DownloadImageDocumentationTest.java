package com.hoo.aoo.file.adapter.in.web.publics;

import com.hoo.aoo.common.adapter.in.web.config.DocumentationTest;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.aoo.file.domain.FileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.restdocs.RestDocumentationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DocumentationTest
public class DownloadImageDocumentationTest {

    MockMvc mockMvc;

    @Autowired
    FileJpaRepository fileJpaRepository;

    @BeforeEach
    void init(WebApplicationContext wac, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris().scheme("https").host("file.archiveofongr.site").removePort(), prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .build();
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
