package com.hoo.aoo.admin.application.port.out.universe;

import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.admin.domain.universe.Universe;

import java.util.Optional;

public interface FindUniversePort {
    Optional<Universe> load(Long id);
    SearchUniverseResult search(SearchUniverseCommand command);
    SearchUniverseResult.UniverseInfo find(Long id);
}
