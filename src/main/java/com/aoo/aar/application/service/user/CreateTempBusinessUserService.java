package com.aoo.aar.application.service.user;

import com.aoo.aar.application.port.in.user.CreateTempBusinessUserCommand;
import com.aoo.aar.application.port.in.user.CreateTempBusinessUserResult;
import com.aoo.aar.application.port.in.user.CreateTempBusinessUserUseCase;
import com.aoo.aar.application.port.out.cache.LoadEmailAuthnStatePort;
import com.aoo.aar.application.port.out.persistence.user.SaveTempUserPort;
import com.aoo.aar.application.service.AarErrorCode;
import com.aoo.aar.application.service.AarException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateTempBusinessUserService implements CreateTempBusinessUserUseCase {

    private final LoadEmailAuthnStatePort loadEmailAuthnStatePort;
    private final PasswordEncoder passwordEncoder;
    private final SaveTempUserPort saveTempUserPort;

    @Override
    public CreateTempBusinessUserResult create(CreateTempBusinessUserCommand command) {
        if (!loadEmailAuthnStatePort.loadAuthenticated(command.email())) throw new AarException(AarErrorCode.NOT_VERIFIED_EMAIL);

        String encodedPassword = passwordEncoder.encode(command.password());
        Long tempUserId = saveTempUserPort.save(command.email(), encodedPassword, command.nickname());

        return new CreateTempBusinessUserResult(
                String.format("[#%d]번 임시 사용자가 생성되었습니다. 관리자 승인 후 계정이 등록됩니다.", tempUserId),
                tempUserId,
                command.email(),
                command.nickname()
        );
    }
}
