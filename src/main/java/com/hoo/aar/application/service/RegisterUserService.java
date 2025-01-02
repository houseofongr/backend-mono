package com.hoo.aar.application.service;

import com.hoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aar.adapter.out.persistence.adapter.SnsAccountPersistenceAdapter;
import com.hoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aar.application.port.in.RegisterUserUseCase;
import com.hoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aar.application.port.out.database.LoadUserPort;
import com.hoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aar.common.enums.ErrorCode;
import com.hoo.aar.common.exception.AarException;
import com.hoo.aar.domain.SnsAccount;
import com.hoo.aar.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadSnsAccountPort loadSnsAccountPort;
    private final SaveUserPort saveUserPort;
    private final JwtUtil jwtUtil;

    @Override
    public RegisterUserCommand.Out register(RegisterUserCommand.In command) {

        loadUserPort.assertNotExistNickname(command.nickname());

        SnsAccount snsAccount = loadSnsAccountPort.load(command.snsId());

        User newUser = new User(snsAccount,
                command.nickname(),
                command.recordAgreement(),
                command.personalInformationAgreement());

        newUser = saveUserPort.save(newUser);

        String accessToken = jwtUtil.getAccessToken(newUser);

        return new RegisterUserCommand.Out(newUser.getNickname(), accessToken);
    }
}
