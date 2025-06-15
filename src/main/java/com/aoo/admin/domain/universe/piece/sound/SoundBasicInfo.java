package com.aoo.admin.domain.universe.piece.sound;

import com.aoo.admin.domain.universe.BaseBasicInfo;
import lombok.Getter;

@Getter
public class SoundBasicInfo extends BaseBasicInfo {

    private final Long pieceId;
    private final Boolean hidden;

    public SoundBasicInfo(Long pieceId, String title, String description, Boolean hidden) {
        super(title, description);
        this.pieceId = pieceId;
        this.hidden = hidden;
    }
}
