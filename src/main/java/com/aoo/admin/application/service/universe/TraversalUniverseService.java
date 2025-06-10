package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.TraversalUniverseResult;
import com.aoo.admin.application.port.in.universe.TraversalUniverseUseCase;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.domain.universe.TraversalComponents;
import com.aoo.admin.domain.universe.TreeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TraversalUniverseService implements TraversalUniverseUseCase {

    private final FindUniversePort findUniversePort;

    @Override
    public TraversalUniverseResult traversal(Long universeId) {
        TraversalComponents components = findUniversePort.findTreeComponents(universeId);
        TreeInfo root = TreeInfo.create(components);

        return TraversalUniverseResult.of(root);
    }
}
