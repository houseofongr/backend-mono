package com.hoo.aoo.file.application.port.out.filesystem;

import com.hoo.aoo.file.domain.File;

import java.io.IOException;

public interface EraseFilePort {
    void erase(File file) throws IOException;
}
