package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.application.port.in.user.CreateDeletedUserPort;
import com.hoo.aoo.admin.application.port.in.user.DeleteUserPort;
import com.hoo.aoo.admin.application.port.in.user.DeleteUserUseCase;
import com.hoo.aoo.admin.application.port.in.user.SaveDeletedUserPort;
import com.hoo.aoo.admin.application.port.out.user.FindUserPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.user.DeletedUser;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final FindUserPort findUserPort;
    private final CreateDeletedUserPort createDeletedUserPort;
    private final SaveDeletedUserPort saveDeletedUserPort;
    private final DeleteUserPort deleteUserPort;

    @Override
    public MessageDto deleteUser(Long userId, Boolean termsOfDeletionAgreement, Boolean personalInformationDeletionAgreement) {
        User user = findUserPort.loadUser(userId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

        DeletedUser deletedUser = createDeletedUserPort.createDeletedUser(user, termsOfDeletionAgreement, personalInformationDeletionAgreement);

        saveDeletedUserPort.saveDeletedUser(deletedUser);

        deleteUserPort.deleteUser(user);

        return new MessageDto("회원탈퇴가 완료되었습니다.");
    }
}
