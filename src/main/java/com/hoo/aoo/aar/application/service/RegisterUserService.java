package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.application.exception.AarErrorCode;
import com.hoo.aoo.aar.application.exception.AarException;
import com.hoo.aoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aoo.aar.application.port.in.RegisterUserUseCase;
import com.hoo.aoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aoo.aar.domain.SnsAccount;
import com.hoo.aoo.aar.domain.User;
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
    public RegisterUserCommand.Out register(RegisterUserCommand.In command) {

        SnsAccount snsAccount = loadSnsAccountPort.load(command.snsId())
                .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND));

        if (snsAccount.getUser() != null) throw new AarException(AarErrorCode.ALREADY_REGISTERED_SNS_ACCOUNT);

        User newUser = User.register(snsAccount, command.recordAgreement(), command.personalInformationAgreement());

        newUser = saveUserPort.save(newUser);

        String accessToken = jwtUtil.getAccessToken(snsAccount);

        return new RegisterUserCommand.Out(newUser.getNickname(), accessToken);
    }
}
