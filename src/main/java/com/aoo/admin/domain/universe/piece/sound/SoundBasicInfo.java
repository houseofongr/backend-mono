package com.aoo.admin.domain.universe.piece.sound;

import com.aoo.admin.domain.universe.BaseBasicInfo;
import lombok.Getter;

@Getter
public class SoundBasicInfo extends BaseBasicInfo {

    private final Long pieceId;

    public SoundBasicInfo(Long pieceId, String title, String description) {
        super(title, description);
        this.pieceId = pieceId;
    }
}
