package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.aoo.admin.application.port.in.universe.SearchUniverseUseCase;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchUniverseService implements SearchUniverseUseCase {

    private final FindUniversePort findUniversePort;

    @Override
    public SearchUniverseResult search(SearchUniverseCommand command) {
        return findUniversePort.search(command);
    }

    @Override
    public SearchUniverseResult.UniverseDetailInfo search(Long universeId) {
        return findUniversePort.find(universeId);
    }

}
