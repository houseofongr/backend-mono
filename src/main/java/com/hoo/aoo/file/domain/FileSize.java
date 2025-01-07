package com.hoo.aoo.file.domain;

public class FileSize {
    private final Long fileByte;

    public FileSize(Long fileByte) {
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
