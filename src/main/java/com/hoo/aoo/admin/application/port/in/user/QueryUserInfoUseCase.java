package com.hoo.aoo.admin.application.port.in.user;

public interface QueryUserInfoUseCase {
    QueryUserInfoResult query(QueryUserInfoCommand command);
}
