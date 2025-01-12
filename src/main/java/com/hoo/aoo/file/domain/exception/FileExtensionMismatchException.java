package com.hoo.aoo.file.domain.exception;

import com.hoo.aoo.file.domain.FileType;

public class FileExtensionMismatchException extends Exception {
    public FileExtensionMismatchException(FileType fileType, String fileName) {
        super("File Type And Extension is mismatch. File Type : " + fileType + " | file Name : " + fileName);
    }
}
