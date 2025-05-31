package com.aoo.admin.application.port.in.user;

public interface SearchUserUseCase {
    SearchUserResult query(SearchUserCommand command);
}
