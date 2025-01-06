package com.hoo.aoo.aar.adapter.in.web.authn.security;

import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.SnsAccountF;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

@RestController
@RequiredArgsConstructor
public class MockOAuth2Controller {

    @Value("${security.frontend-redirect-uri}")
    private String redirectUri;

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @GetMapping("/mock/authn/login/{snsAccountId}")
    void mockLogin(HttpServletResponse response, @PathVariable Long snsAccountId) throws IOException, InvalidPhoneNumberException {

        if (snsAccountId == 1L) {
            SnsAccountJpaEntity snsAccount = mock(SnsAccountJpaEntity.class);

            when(snsAccount.getId()).thenReturn(1L);
            when(snsAccount.getNickname()).thenReturn("leaf");
            when(snsAccount.getUserEntity()).thenReturn(null);

            String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("nickname", URLEncoder.encode("남상엽", StandardCharsets.UTF_8))
                    .queryParam("accessToken", jwtUtil.getAccessToken(snsAccount))
                    .queryParam("provider", "kakao")
                    .queryParam("isFirstLogin", "true")
                    .build().toUriString();

            response.sendRedirect(redirectUrl);
        }
        else if (snsAccountId == 2L) {
            SnsAccountJpaEntity snsAccount = mock(SnsAccountJpaEntity.class);
            UserJpaEntity entity = mock(UserJpaEntity.class);

            when(snsAccount.getId()).thenReturn(2L);
            when(snsAccount.getNickname()).thenReturn("leaf");
            when(snsAccount.getUserEntity()).thenReturn(entity);
            when(entity.getId()).thenReturn(1L);

            String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("nickname", URLEncoder.encode("남엽돌", StandardCharsets.UTF_8))
                    .queryParam("accessToken", jwtUtil.getAccessToken(snsAccount))
                    .queryParam("provider", "kakao")
                    .queryParam("isFirstLogin", "true")
                    .build().toUriString();

            response.sendRedirect(redirectUrl);

        } else if (snsAccountId == 3L) {
            SnsAccountJpaEntity snsAccount = mock(SnsAccountJpaEntity.class);

            when(snsAccount.getId()).thenReturn(2L);
            when(snsAccount.getNickname()).thenReturn("spearoad");
            when(snsAccount.getUserEntity()).thenReturn(null);

            String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("nickname", URLEncoder.encode("남엽돌", StandardCharsets.UTF_8))
                    .queryParam("accessToken", jwtUtil.getAccessToken(snsAccount))
                    .queryParam("provider", "kakao")
                    .queryParam("isFirstLogin", "true")
                    .build().toUriString();

            response.sendRedirect(redirectUrl);
        } else if (snsAccountId == 4L) {
            SnsAccountJpaEntity snsAccount = mock(SnsAccountJpaEntity.class);
            UserJpaEntity entity = mock(UserJpaEntity.class);

            when(snsAccount.getId()).thenReturn(4L);
            when(snsAccount.getNickname()).thenReturn("spearoad");
            when(snsAccount.getUserEntity()).thenReturn(entity);
            when(entity.getId()).thenReturn(2L);

            String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("nickname", URLEncoder.encode("남엽돌", StandardCharsets.UTF_8))
                    .queryParam("accessToken", jwtUtil.getAccessToken(snsAccount))
                    .queryParam("provider", "kakao")
                    .queryParam("isFirstLogin", "true")
                    .build().toUriString();

            response.sendRedirect(redirectUrl);
        }
        else throw new UnsupportedOperationException("SNS Account Id 허용범위 초과");
    }
}
