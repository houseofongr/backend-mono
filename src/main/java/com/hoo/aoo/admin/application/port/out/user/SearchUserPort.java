package com.hoo.aoo.admin.application.port.out.user;

import com.hoo.aoo.admin.application.port.in.user.SearchUserCommand;
import com.hoo.aoo.admin.application.port.in.user.SearchUserResult;

public interface SearchUserPort {
    SearchUserResult search(SearchUserCommand command);
}
