package com.hoo.aoo.admin.application.port.out.user;

import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;

public interface SearchUserPort {
    QueryUserInfoResult search(QueryUserInfoCommand command);
}
