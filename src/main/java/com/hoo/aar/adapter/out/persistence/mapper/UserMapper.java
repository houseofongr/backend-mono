package com.hoo.aar.adapter.out.persistence.mapper;

import com.hoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aar.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userJpaEntityToUser(UserJpaEntity newUser);

}
