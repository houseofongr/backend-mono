package com.aoo.common.adapter.in.web.config;

import com.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.aoo.file.application.service.FileProperties;
import com.aoo.file.domain.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.nio.file.Path;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@DocumentationTest
@Sql(value = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;

    @TempDir
    protected Path tempDir;

    @Autowired
    protected FileJpaRepository fileJpaRepository;

    @Autowired
    protected FileProperties fileProperties;

    protected MockMvcTester mockMvcTester;

    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    protected boolean useSpringSecurity() {
        return true;
    }

    @BeforeEach
    protected void init(WebApplicationContext wac, RestDocumentationContextProvider restDocumentation) {
        DefaultMockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .alwaysDo(log())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris().scheme("https").host(getBaseUrl()).removePort(), prettyPrint())
                        .withResponseDefaults(prettyPrint())
                );

        mockMvc = useSpringSecurity() ? mockMvcBuilder.apply(springSecurity()).build() : mockMvcBuilder.build();

        ReflectionTestUtils.setField(fileProperties, "baseDir", tempDir.toString());

        this.mockMvcTester = MockMvcTester.from(wac);
    }

    protected void saveFile(File file) throws IOException {
        java.io.File tempFile = new java.io.File(file.getFileId().getPath());
        tempFile.mkdirs();
        tempFile.createNewFile();
        fileJpaRepository.save(FileJpaEntity.create(file));
    }

}
