package com.hoo.aoo.admin.domain.universe.space;

import com.hoo.aoo.admin.domain.universe.BasicInfo;
import com.hoo.aoo.admin.domain.universe.DateInfo;

public class Space {
    private final Long id;
    private final Long imageId;
    private final BasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final PosInfo posInfo;
    private final TreeInfo treeInfo;

    private Space(Long id, Long imageId, BasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo) {
        this.id = id;
        this.imageId = imageId;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
        this.treeInfo = treeInfo;
    }
}
