package com.hoo.main.application.port.out.persistence.user;

public interface FindBusinessUserPort {
    BusinessUserInfo findBusinessUserInfo(String email);
}
