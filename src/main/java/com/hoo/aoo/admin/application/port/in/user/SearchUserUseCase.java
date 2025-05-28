package com.hoo.aoo.admin.application.port.in.user;

public interface SearchUserUseCase {
    SearchUserResult query(SearchUserCommand command);
}
