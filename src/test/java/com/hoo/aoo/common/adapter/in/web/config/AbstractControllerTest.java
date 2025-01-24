package com.hoo.aoo.common.adapter.in.web.config;

import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@DocumentationTest
@Sql(value = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;

    @TempDir
    protected java.io.File tempDir;

    @Autowired
    protected FileAttribute fileAttribute;

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
        ReflectionTestUtils.setField(fileAttribute, "baseDir", tempDir.getPath());
    }

}
