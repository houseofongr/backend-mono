package com.hoo.aoo.file.domain.exception;

public class FileSizeLimitExceedException extends Exception {
    public FileSizeLimitExceedException(long actual, long permitted) {
        super("File Size Limit Exceeded. Permitted : " + permitted + " | Actual : " + actual);
    }
}
