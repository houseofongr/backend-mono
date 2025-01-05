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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final LoadSnsAccountPort loadSnsAccountPort;
    private final SaveUserPort saveUserPort;
    private final JwtUtil jwtUtil;

    @Override
    public RegisterUserCommand.Out register(RegisterUserCommand.In command) {

        SnsAccount snsAccount = loadSnsAccountPort.load(command.snsId())
                .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND));

        User newUser = User.regist(snsAccount, command.recordAgreement(), command.personalInformationAgreement());

        newUser = saveUserPort.save(newUser);

        String accessToken = jwtUtil.getAccessToken(snsAccount);

        return new RegisterUserCommand.Out(newUser.getNickname(), accessToken);
    }
}
