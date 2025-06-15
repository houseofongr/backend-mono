package com.aoo.admin.application.port.in.space;

import com.aoo.admin.domain.universe.TreeInfo;

public interface DeleteSpaceUseCase {
    DeleteSpaceResult delete(Long spaceId);
    DeleteSpaceResult deleteSubtree(Long spaceId, TreeInfo subtree);
}
