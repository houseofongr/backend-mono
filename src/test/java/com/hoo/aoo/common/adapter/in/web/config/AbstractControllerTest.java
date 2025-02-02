package com.hoo.aoo.common.adapter.in.web.config;

import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.aoo.file.application.service.FileAttribute;
import com.hoo.aoo.file.domain.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@DocumentationTest
@Sql(value = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;

    @TempDir
    protected Path tempDir;

    @Autowired
    protected FileJpaRepository fileJpaRepository;

    @Autowired
    protected FileAttribute fileAttribute;

    protected MockMvcTester mockMvcTester;

    protected abstract String getBaseUrl();

    @BeforeEach
    protected void init(WebApplicationContext wac, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris().scheme("https").host(getBaseUrl()).removePort(), prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .build();
        ReflectionTestUtils.setField(fileAttribute, "baseDir", tempDir.toString());

        this.mockMvcTester = MockMvcTester.from(wac);
    }

    protected void saveFile(File file) throws IOException {
        java.io.File tempFile = new java.io.File(file.getFileId().getPath());
        tempFile.mkdirs();
        tempFile.createNewFile();
        fileJpaRepository.save(FileJpaEntity.create(file));
    }

}
