package com.aoo.admin.application.port.out.user;

import com.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.aoo.admin.application.port.in.user.SearchUserCommand;
import com.aoo.admin.application.port.in.user.SearchUserResult;

public interface SearchUserPort {
    QueryUserInfoResult query(QueryUserInfoCommand command);

    SearchUserResult search(SearchUserCommand command);
}
