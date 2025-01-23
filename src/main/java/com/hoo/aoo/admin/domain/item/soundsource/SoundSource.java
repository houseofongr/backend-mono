package com.hoo.aoo.admin.domain.item.soundsource;

import com.hoo.aoo.admin.domain.file.File;
import com.hoo.aoo.admin.domain.file.FileId;
import com.hoo.aoo.admin.domain.file.FileType;
import com.hoo.aoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class SoundSource {
    private final File file;
    private final Detail detail;
    private final BaseTime baseTime;

    private SoundSource(File file, Detail detail, BaseTime baseTime) {
        this.file = file;
        this.detail = detail;
        this.baseTime = baseTime;
    }

    public static SoundSource create(Long fileId, String name, String description, ZonedDateTime createdTime, ZonedDateTime updatedTime) {

        File file = new File(new FileId(fileId), FileType.AUDIO);
        Detail detail = new Detail(name, description);
        BaseTime baseTime = new BaseTime(createdTime, updatedTime);

        return new SoundSource(file, detail,baseTime);
    }
}
