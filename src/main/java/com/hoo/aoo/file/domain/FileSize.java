package com.hoo.aoo.file.domain;

import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;

public class FileSize {
    public static final Long FILE_SIZE_LIMIT = 100 * 1024 * 1024L;
    private final Long fileByte;

    public FileSize(Long fileByte) throws FileSizeLimitExceedException {
        if (fileByte > FILE_SIZE_LIMIT)
            throw new FileSizeLimitExceedException();

        this.fileByte = fileByte;
    }

    public String getUnitSize() {
        String suffix = "";
        double unit = 1;

        if (fileByte < 1000) {
            return fileByte + "Byte";
        } else if (fileByte < 1000 * 1024) {
            suffix = "KB";
            unit = 1024.0;
        } else {
            suffix = "MB";
            unit = 1024 * 1024.0;
        }

        return String.format("%.2f", (double) fileByte / unit) + suffix;
    }
}
