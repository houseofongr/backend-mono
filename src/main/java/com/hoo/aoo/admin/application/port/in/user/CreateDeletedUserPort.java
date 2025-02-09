package com.hoo.aoo.admin.application.port.in.user;

import com.hoo.aoo.admin.domain.user.DeletedUser;
import com.hoo.aoo.admin.domain.user.User;

public interface CreateDeletedUserPort {
    DeletedUser createDeletedUser(User user, Boolean termsOfDeletionAgreement, Boolean personalInformationDeletionAgreement);
}
