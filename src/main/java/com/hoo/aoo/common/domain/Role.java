package com.hoo.aoo.common.domain;

import java.util.List;

public enum Role {
    SUPER_ADMIN, ADMIN, USER, TEMP_USER, VISITOR;

    public List<Authority> getAuthorities() {
        return switch (this) {
            case SUPER_ADMIN -> List.of(Authority.CREATE_ADMIN);
            case ADMIN -> List.of(Authority.PRIVATE_ALL_AUDIO_ACCESS);
            case USER -> List.of(Authority.PRIVATE_ALL_IMAGE_ACCESS, Authority.PRIVATE_MY_AUDIO_ACCESS);
            case TEMP_USER -> List.of(Authority.USER_REGISTER);
            case VISITOR -> List.of(Authority.PUBLIC_FILE_ACCESS);
        };
    }
}
