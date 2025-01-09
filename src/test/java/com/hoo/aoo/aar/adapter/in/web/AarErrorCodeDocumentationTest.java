package com.hoo.aoo.aar.adapter.in.web;

import com.hoo.aoo.aar.application.service.AarErrorCode;
import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.hoo.aoo.common.adapter.in.web.config.ErrorCodeResponseFieldsSnippet.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AarErrorCodeDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("에러코드 문서화")
    void testErrorCodeDocumentation() throws Exception {
        mockMvc.perform(get("/aar/error-codes"))
                .andExpect(status().is(200))
                .andDo(document("aar-error-code",
                        responseFields(
                                fieldWithPath("*.code").description("에러코드 이름입니다."),
                                fieldWithPath("*.message").description("에러코드에 대한 설명입니다."),
                                fieldWithPath("*.httpStatusCode").description("HTTP 상태 코드입니다."),
                                fieldWithPath("*.httpStatusReason").description("상태 코드의 발생 원인입니다.")
                        )
                        , errorCodeResponseFields("error-code-response",
                                errorCodeFieldDescriptors(AarErrorCode.values())
                        )
                ));
    }
}