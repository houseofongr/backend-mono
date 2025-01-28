package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aoo.aar.application.port.in.RegisterUserResult;
import com.hoo.aoo.aar.application.port.in.RegisterUserUseCase;
import com.hoo.aoo.aar.application.port.out.database.snsaccount.FindSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.user.CreateUserPort;
import com.hoo.aoo.aar.application.port.out.database.user.SaveUserPort;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final FindSnsAccountPort findSnsAccountPort;
    private final CreateUserPort createUserPort;
    private final SaveUserPort saveUserPort;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public RegisterUserResult register(RegisterUserCommand command) {

        SnsAccount snsAccount = findSnsAccountPort.load(command.snsId())
                .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND));

        if (snsAccount.getUserId().getId() != null)
            throw new AarException(AarErrorCode.ALREADY_REGISTERED_SNS_ACCOUNT);

        User user = createUserPort.createUser(snsAccount, command.termsOfUseAgreement(), command.personalInformationAgreement());
        Long userId = saveUserPort.save(user);

        return new RegisterUserResult(userId, user.getUserInfo().getNickname(), jwtUtil.getAccessToken(snsAccount));
    }
}
