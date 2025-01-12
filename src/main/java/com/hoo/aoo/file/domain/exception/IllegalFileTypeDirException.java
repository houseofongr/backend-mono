package com.hoo.aoo.file.domain.exception;

public class IllegalFileTypeDirException extends Exception {
    public IllegalFileTypeDirException(String dir) {
        super("Unexpected File type path : " + dir);
    }
}
