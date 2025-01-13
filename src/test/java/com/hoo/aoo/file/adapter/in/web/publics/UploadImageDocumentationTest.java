package com.hoo.aoo.file.adapter.in.web.publics;

import com.hoo.aoo.common.adapter.in.web.config.DocumentationTest;
import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DocumentationTest
public class UploadImageDocumentationTest {

    MockMvc mockMvc;

    @TempDir
    java.io.File tempDir;

    @Autowired
    FileAttribute fileAttribute;

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
        ReflectionTestUtils.setField(fileAttribute, "baseDir", tempDir.getPath());
    }

    @Test
    @DisplayName("이미지파일 업로드 API")
    void testFile() throws Exception {
        MockMultipartFile image = new MockMultipartFile("images", "image.png", "image/png", "<<png data 1>>".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("images", "image2.png", "image/png", "<<png data 2>>".getBytes());

        mockMvc.perform(multipart("/public/images")
                        .file(image).file(image2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(document("file-public-images-upload",
                        responseFields(
                                fieldWithPath("fileInfos[].id").description("등록된 파일의 아이디입니다."),
                                fieldWithPath("fileInfos[].name").description("등록된 파일명입니다."),
                                fieldWithPath("fileInfos[].size").description("등록된 파일의 용량입니다."),
                                fieldWithPath("fileInfos[].authority").description("등록된 파일의 접근 권한입니다. +" + "\n" + " - PUBLIC_FILE_ACCESS : 공개된 파일 +" + "\n" + " - PRIVATE_FILE_ACCESS : 비공개 파일(권한이 있는 사용자만 접근 가능)")
                        )));
    }
}
