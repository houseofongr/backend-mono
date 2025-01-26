package com.hoo.aoo.admin.domain.soundsource;

import com.hoo.aoo.admin.domain.file.File;
import com.hoo.aoo.admin.domain.file.FileId;
import com.hoo.aoo.admin.domain.file.FileType;
import com.hoo.aoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class SoundSource {
    private final SoundSourceId soundSourceId;
    private final File file;
    private final SoundSourceDetail soundSourceDetail;
    private final BaseTime baseTime;
    private final Active active;

    private SoundSource(SoundSourceId soundSourceId, File file, SoundSourceDetail soundSourceDetail, BaseTime baseTime, Active active) {
        this.soundSourceId = soundSourceId;
        this.file = file;
        this.soundSourceDetail = soundSourceDetail;
        this.baseTime = baseTime;
        this.active = active;
    }

    public static SoundSource create(Long id, Long audioFileId, String name, String description, Boolean isActive) {

        File file = new File(new FileId(audioFileId), FileType.AUDIO);

        return new SoundSource(new SoundSourceId(id), file, new SoundSourceDetail(name, description), null, new Active(isActive));
    }

    public static SoundSource load(Long id, Long audioFileId, String name, String description, ZonedDateTime createdTime, ZonedDateTime updatedTime, Boolean isActive) {

        File file = new File(new FileId(audioFileId), FileType.AUDIO);

        return new SoundSource(new SoundSourceId(id), file, new SoundSourceDetail(name, description), new BaseTime(createdTime, updatedTime), new Active(isActive));
    }
}
