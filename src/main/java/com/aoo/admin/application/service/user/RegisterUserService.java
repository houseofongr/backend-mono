package com.aoo.admin.application.service.user;

import com.aoo.aar.application.port.out.jwt.IssueAccessTokenPort;
import com.aoo.aar.application.service.AarErrorCode;
import com.aoo.aar.application.service.AarException;
import com.aoo.admin.application.port.in.user.RegisterUserCommand;
import com.aoo.admin.application.port.in.user.RegisterUserResult;
import com.aoo.admin.application.port.in.user.RegisterUserUseCase;
import com.aoo.admin.application.port.out.snsaccount.FindSnsAccountPort;
import com.aoo.admin.application.port.out.user.CreateUserPort;
import com.aoo.admin.application.port.out.user.SaveUserPort;
import com.aoo.admin.domain.user.User;
import com.aoo.admin.domain.user.snsaccount.SnsAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final FindSnsAccountPort findSnsAccountPort;
    private final CreateUserPort createUserPort;
    private final SaveUserPort saveUserPort;
    private final IssueAccessTokenPort issueAccessTokenPort;

    @Override
    @Transactional
    public RegisterUserResult register(RegisterUserCommand command) {

        SnsAccount snsAccount = findSnsAccountPort.loadSnsAccount(command.snsId())
                .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND));

        if (snsAccount.getUserId() != null && snsAccount.getUserId().getId() != null)
            throw new AarException(AarErrorCode.ALREADY_REGISTERED_SNS_ACCOUNT);

        User user = createUserPort.createUser(snsAccount, command.termsOfUseAgreement(), command.personalInformationAgreement());
        Long userId = saveUserPort.save(user);

        snsAccount.link(userId);

        return new RegisterUserResult(userId, user.getUserInfo().getNickname(), issueAccessTokenPort.issueAccessToken(snsAccount));
    }
}
