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
    private final Detail detail;
    private final BaseTime baseTime;
    private final Active active;

    private SoundSource(SoundSourceId soundSourceId, File file, Detail detail, BaseTime baseTime, Active active) {
        this.soundSourceId = soundSourceId;
        this.file = file;
        this.detail = detail;
        this.baseTime = baseTime;
        this.active = active;
    }

    public static SoundSource create(Long id, Long fileId, String name, String description, Boolean isActive) {

        SoundSourceId soundSourceId = new SoundSourceId(id);
        File file = new File(new FileId(fileId), FileType.AUDIO);
        Detail detail = new Detail(name, description);
        Active active = new Active(isActive);

        return new SoundSource(soundSourceId, file, detail, null, active);
    }
}
