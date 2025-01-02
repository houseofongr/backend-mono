package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.adapter.in.web.config.IntegrationTest;
import com.hoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aar.application.port.in.RegisterUserCommand;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.http.client.ClientHttpRequestFactorySettings.Redirects.*;

@IntegrationTest
public class RegisterIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    Gson gson = new Gson();

    @Autowired
    JwtDecoder jwtDecoder;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    SnsAccountJpaRepository snsAccountJpaRepository;

    @Autowired
    private ClientHttpRequestFactorySettings clientHttpRequestFactorySettings;


    @Test
    @Sql("RegisterIntegrationTest.sql")
    @DisplayName("회원가입 통합테스트")
    void testRegister() throws Exception {
        /* 1. 사용자 로그인 시도 */

        ResponseEntity<?> loginResponse = whenLogin();

        assertThat(loginResponse.getStatusCode().value()).isEqualTo(302);

        /* 2. SNS 계정 임시 저장, 사용자 정보, 임시 토큰 전송 */

        Map<String, String> queryParams = UriComponentsBuilder.fromUri(loginResponse.getHeaders().getLocation()).build().getQueryParams().toSingleValueMap();

        assertThat(snsAccountJpaRepository.findBySnsIdWithUserEntity("SNS_ID")).isNotEmpty();
        assertThat(queryParams).containsKey("nickname");
        assertThat(queryParams).containsKey("accessToken");

        String tempAccessToken = queryParams.get("accessToken");
        Jwt jwt = jwtDecoder.decode(tempAccessToken);

        assertThat((String)jwt.getClaim("role")).isEqualTo("TEMP_USER");

        /* 3. 사용자 회원가입 시도 */

        String body = "{\"recordAgreement\":true, \"personalInformationAgreement\":true}";

        ResponseEntity<?> registResponse = whenRegist(tempAccessToken, body);

        assertThat(registResponse.getStatusCode().value()).isEqualTo(200);

        /* 4. 사용자 회원가입, 사용자 정보, 토큰 전송 */

        RegisterUserCommand.Out responseBody = (RegisterUserCommand.Out) registResponse.getBody();

        assertThat(userJpaRepository.findByNickname("leaf")).isNotEmpty();
        assertThat(responseBody.nickname()).isNotEmpty();
        assertThat(responseBody.accessToken()).isNotEmpty();

        String userAccessToken = responseBody.accessToken();
        Jwt jwt2 = jwtDecoder.decode(userAccessToken);

        assertThat((String)jwt2.getClaim("role")).isEqualTo("USER");
    }

    private ResponseEntity<?> whenLogin() {
        HttpEntity<?> request = getHttpEntity(null, null);

        return restTemplate
                .withRequestFactorySettings(
                        clientHttpRequestFactorySettings.withRedirects(DONT_FOLLOW))
                .exchange(
                "/mock/authn/login",
                HttpMethod.GET,
                request,
                Void.class);
    }

    private ResponseEntity<?> whenRegist(String accessToken, String body) {
        HttpEntity<?> request = getHttpEntity(accessToken, body);

        return restTemplate.exchange(
                "/aar/authn/regist",
                HttpMethod.POST,
                request,
                RegisterUserCommand.Out.class);
    }

    private HttpEntity<?> getHttpEntity(String accessToken, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(body, headers);
    }
}
