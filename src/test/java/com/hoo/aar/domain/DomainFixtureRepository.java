package com.hoo.aar.domain;

import java.util.List;

public class DomainFixtureRepository {
    public static User getRegisteredUser() {
        SnsAccount snsAccount = SnsAccountF.KAKAO_NOT_REGISTERED.get();
        User user = new User(
                1L,
                "남상엽",
                "leaf",
                "NOT_SET",
                false,
                false,
                List.of(snsAccount)
        );
        snsAccount.setUser(user);
        return user;
    }
}
