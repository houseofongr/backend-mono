package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.SNSLoginResponse;
import com.hoo.aoo.aar.adapter.in.web.authn.security.dto.OAuth2Dto;
import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.application.port.out.database.snsaccount.CreateSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.snsaccount.FindSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.snsaccount.SaveSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.user.FindUserPort;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.hoo.aoo.common.util.GsonUtil.gson;

@Component
@AllArgsConstructor
public class LoadKakaoSnsAccountService implements LoadSnsAccountService {

    private final FindSnsAccountPort findSnsAccountPort;
    private final FindUserPort findUserPort;
    private final CreateSnsAccountPort createSnsAccountPort;
    private final SaveSnsAccountPort saveSnsAccountPort;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public OAuth2User load(OAuth2User user) {

        try {
            OAuth2Dto.KakaoUserInfo userInfo = gson.fromJson(gson.toJsonTree(user.getAttributes()), OAuth2Dto.KakaoUserInfo.class);
            Optional<SnsAccount> snsAccountOptional = findSnsAccountPort.load(SnsDomain.KAKAO, userInfo.id());

            // 등록된 SNS 계정
            if (snsAccountOptional.isPresent()) {
                SnsAccount snsAccountInDB = snsAccountOptional.get();
                Optional<User> userOptional = findUserPort.load(snsAccountInDB.getUserId().getId());

                // 사용자와 연동된 계정
                if (userOptional.isPresent()) {
                    SNSLoginResponse response = SNSLoginResponse.from(userOptional.get(), jwtUtil.getAccessToken(snsAccountInDB), SnsDomain.KAKAO);
                    return new DefaultOAuth2User(user.getAuthorities(), response.getAttributes(), "nickname");
                }

                // 사용자외 연동되지 않은 계정
                else {
                    SNSLoginResponse response = SNSLoginResponse.from(snsAccountInDB, jwtUtil.getAccessToken(snsAccountInDB));
                    return new DefaultOAuth2User(user.getAuthorities(), response.getAttributes(), "nickname");
                }


            // 등록되지 않은 SNS 계정
            } else {
                SnsAccount newSnsAccount = saveNewAccount(userInfo);
                SNSLoginResponse response = SNSLoginResponse.from(newSnsAccount, jwtUtil.getAccessToken(newSnsAccount));
                return new DefaultOAuth2User(user.getAuthorities(), response.getAttributes(), "nickname");
            }

        } catch (InvalidPhoneNumberException e) {
            throw new RuntimeException(e);
        }

    }

    private SnsAccount saveNewAccount(OAuth2Dto.KakaoUserInfo userInfo) {
        String name = userInfo.kakao_account().profile().nickname();
        String email = userInfo.kakao_account().email();
        String snsId = userInfo.id();

        SnsAccount newAccount = createSnsAccountPort.createSnsAccount(SnsDomain.KAKAO, snsId, name, name, email);
        saveSnsAccountPort.save(newAccount);

        return newAccount;
    }
}
