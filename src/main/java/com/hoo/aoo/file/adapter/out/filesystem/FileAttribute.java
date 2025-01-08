package com.hoo.aoo.file.adapter.out.filesystem;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FileAttribute {

    private final String publicImagePath;
    private final String publicAudioPath;
    private final Long fileSizeLimit;

    public FileAttribute(@Value("${file.size-limit}") Long fileSizeLimit,
                         @Value("${file.public.image-path}") String publicImagePath,
                         @Value("${file.public.audio-path}") String publicAudioPath) {
        this.fileSizeLimit = fileSizeLimit;
        this.publicImagePath = publicImagePath;
        this.publicAudioPath = publicAudioPath;
    }
}
