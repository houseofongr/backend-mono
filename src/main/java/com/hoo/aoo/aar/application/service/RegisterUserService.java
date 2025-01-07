package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.application.exception.AarErrorCode;
import com.hoo.aoo.aar.application.exception.AarException;
import com.hoo.aoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aoo.aar.application.port.in.RegisterUserResult;
import com.hoo.aoo.aar.application.port.in.RegisterUserUseCase;
import com.hoo.aoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final LoadSnsAccountPort loadSnsAccountPort;
    private final SaveUserPort saveUserPort;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public RegisterUserResult register(RegisterUserCommand command) {

        try {
            SnsAccount snsAccount = loadSnsAccountPort.load(command.snsId())
                    .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND));

            if (snsAccount.getUserId() != null)
                throw new AarException(AarErrorCode.ALREADY_REGISTERED_SNS_ACCOUNT);


            User user = User.registerWithDefaultPhoneNumber(snsAccount, command.recordAgreement(), command.personalInformationAgreement());

            saveUserPort.save(user);

            String nickname = user.getName().getNickname();
            String accessToken = jwtUtil.getAccessToken(snsAccount);

            return new RegisterUserResult(nickname, accessToken);

        } catch (InvalidPhoneNumberException e) {
            throw new AarException(AarErrorCode.INVALID_PHONE_NUMBER_ERROR, e.getMessage());
        }
    }
}
