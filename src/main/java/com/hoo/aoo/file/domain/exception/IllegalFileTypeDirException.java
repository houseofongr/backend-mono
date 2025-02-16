package com.hoo.aoo.file.domain.exception;

public class IllegalFileTypeDirException extends RuntimeException {
    public IllegalFileTypeDirException(String dir) {
        super("Unexpected File type path : " + dir);
    }
}
