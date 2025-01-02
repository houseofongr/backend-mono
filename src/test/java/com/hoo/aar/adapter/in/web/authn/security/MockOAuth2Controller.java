package com.hoo.aar.adapter.in.web.authn.security;

import com.hoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aar.domain.SnsAccount;
import com.hoo.aar.domain.SnsAccountF;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MockOAuth2Controller {

    @Value("${security.frontend-redirect-uri}")
    private String redirectUri;

    private final JwtUtil jwtUtil;

    @GetMapping("/mock/authn/login")
    void mockLogin(HttpServletResponse response) throws IOException {

        SnsAccount snsAccount = SnsAccountF.KAKAO_NOT_REGISTERED.get();
        String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("nickname", URLEncoder.encode("남상엽", StandardCharsets.UTF_8))
                .queryParam("accessToken", jwtUtil.getAccessToken(snsAccount))
                .queryParam("provider", "kakao")
                .queryParam("isFirstLogin", "true")
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}
