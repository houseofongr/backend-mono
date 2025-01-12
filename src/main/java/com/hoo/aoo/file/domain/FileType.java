package com.hoo.aoo.file.domain;

import com.hoo.aoo.file.domain.exception.IllegalFileTypeDirException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
    IMAGE("images"), AUDIO("audios"), VIDEO("videos");

    private final String path;

    public static FileType of(String dir) throws IllegalFileTypeDirException {
        for (FileType type : FileType.values()) {
            if (type.path.equals(dir)) return type;
        }
        throw new IllegalFileTypeDirException(dir);
    }
}
