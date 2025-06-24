package com.aoo.aar.application.service.authn;

import com.aoo.aar.application.port.in.authn.OAuth2Dto;
import com.aoo.aar.application.port.in.authn.SNSLoginResult;
import com.aoo.aar.application.port.out.jwt.IssueAccessTokenPort;
import com.aoo.aar.application.port.out.persistence.user.SearchUserPort;
import com.aoo.admin.application.port.in.snsaccount.CreateSnsAccountUseCase;
import com.aoo.admin.application.port.in.snsaccount.LoadSnsAccountUseCase;
import com.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.aoo.admin.domain.user.snsaccount.SnsDomain;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.aoo.common.util.GsonUtil.gson;

@Component
@Transactional
@AllArgsConstructor
public class LoadKakaoSnsAccountService implements LoadSnsAccountService {

    private final LoadSnsAccountUseCase loadSnsAccountUseCase;
    private final SearchUserPort searchUserPort;
    private final CreateSnsAccountUseCase createSnsAccountUseCase;
    private final IssueAccessTokenPort issueAccessTokenPort;

    @Override
    public OAuth2User load(OAuth2User user) {

        OAuth2Dto.KakaoUserInfo userInfo = gson.fromJson(gson.toJsonTree(user.getAttributes()), OAuth2Dto.KakaoUserInfo.class);
        SnsAccount snsAccount = loadSnsAccountUseCase.loadSnsAccount(SnsDomain.KAKAO, userInfo.id());

        SNSLoginResult response = snsAccount != null ?
                getResponseByAccountInDB(snsAccount) :
                getResponseByNewAccount(userInfo);

        return new DefaultOAuth2User(user.getAuthorities(), response.getAttributes(), "nickname");
    }

    private SNSLoginResult getResponseByAccountInDB(SnsAccount snsAccount) {

        // account with user
        if (snsAccount.isRegistered())
            return new SNSLoginResult(
                    searchUserPort.queryMyInfo(snsAccount.getUserId().getId()).nickname(),
                    issueAccessTokenPort.issueAccessToken(snsAccount),
                    SnsDomain.KAKAO.name(),
                    false
            );

            // temp account
        else return new SNSLoginResult(
                snsAccount.getSnsAccountInfo().getNickname(),
                issueAccessTokenPort.issueAccessToken(snsAccount),
                SnsDomain.KAKAO.name(),
                true);
    }

    private SNSLoginResult getResponseByNewAccount(OAuth2Dto.KakaoUserInfo userInfo) {

        SnsAccount newSnsAccount = createSnsAccountUseCase.createSnsAccount(
                SnsDomain.KAKAO,
                userInfo.id(),
                userInfo.kakao_account().profile().nickname(),
                userInfo.kakao_account().profile().nickname(),
                userInfo.kakao_account().email());

        return new SNSLoginResult(
                newSnsAccount.getSnsAccountInfo().getNickname(),
                issueAccessTokenPort.issueAccessToken(newSnsAccount),
                SnsDomain.KAKAO.name(),
                true);
    }

}
