package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadImageUseCase;
import com.hoo.aoo.file.application.port.out.database.SaveFilePersistencePort;
import com.hoo.aoo.file.application.port.out.filesystem.StoreImageFileSystemPort;
import com.hoo.aoo.file.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadImageService implements UploadImageUseCase {

    private final SaveFilePersistencePort saveFilePersistencePort;
    private final StoreImageFileSystemPort storeImageFilesystemPort;

    @Override
    @Transactional
    public UploadImageResult upload(List<MultipartFile> images) {

        List<UploadImageResult.FileInfo> fileInfos = new ArrayList<>();

        for (MultipartFile multipartFile : images) {

            File storedFile = storeImageFilesystemPort.storePublicFile(multipartFile);
            Long savedId = saveFilePersistencePort.savePublicFile(storedFile);

            fileInfos.add(new UploadImageResult.FileInfo(storedFile, savedId));
        }

        return new UploadImageResult(fileInfos);
    }
}