package com.aoo.admin.application.service.user;

import com.aoo.admin.application.port.in.user.ConfirmBusinessUserResult;
import com.aoo.admin.application.port.in.user.ConfirmBusinessUserUseCase;
import com.aoo.admin.application.port.out.user.FindBusinessUserPort;
import com.aoo.admin.application.port.out.user.SaveUserPort;
import com.aoo.admin.domain.user.BusinessUser;
import com.aoo.admin.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfirmBusinessUserService implements ConfirmBusinessUserUseCase {

    private final FindBusinessUserPort findBusinessUserPort;
    private final SaveUserPort saveUserPort;

    @Override
    public ConfirmBusinessUserResult confirm(Long tempUserId) {
        BusinessUser businessUser = findBusinessUserPort.findBusinessUser(tempUserId);
        User user = User.createBusinessUser(businessUser);
        Long userId = saveUserPort.saveBusinessUser(user);

        return new ConfirmBusinessUserResult(
                String.format("비즈니스 사용자가 등록되어 [#%d]번 사용자가 생성되었습니다.", userId),
                userId,
                user.getUserInfo().getEmail(),
                user.getUserInfo().getNickname(),
                user.getAgreement().getTermsOfUseAgreement(),
                user.getAgreement().getPersonalInformationAgreement()
        );
    }
}
