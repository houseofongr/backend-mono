package com.hoo.aoo.file.domain.exception;

public class IllegalFileAuthorityDirException extends Exception {
    public IllegalFileAuthorityDirException(String authorityDir) {
        super("Illegal File Authority dir : " + authorityDir);
    }
}
