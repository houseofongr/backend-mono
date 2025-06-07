package com.aoo.admin.domain.universe;

import com.aoo.admin.domain.universe.space.TreeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class UniverseTreeComponent {

    private final Long id;

    @Setter
    private TreeInfo treeInfo;

    protected UniverseTreeComponent(Long id, TreeInfo treeInfo) {
        this.id = id;
        this.treeInfo = treeInfo;
    }

}
