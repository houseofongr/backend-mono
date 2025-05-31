package com.aoo.admin.application.port.in.user;

import com.aoo.admin.domain.user.DeletedUser;
import com.aoo.admin.domain.user.User;

public interface CreateDeletedUserPort {
    DeletedUser createDeletedUser(User user, Boolean termsOfDeletionAgreement, Boolean personalInformationDeletionAgreement);
}
