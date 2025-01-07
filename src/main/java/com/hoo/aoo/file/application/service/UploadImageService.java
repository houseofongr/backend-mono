package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.persistance.entity.FileJpaEntity;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadImageUseCase;
import com.hoo.aoo.file.application.port.out.database.SaveFileEntityPort;
import com.hoo.aoo.file.application.port.out.filesystem.SaveFilePort;
import com.hoo.aoo.file.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UploadImageService implements UploadImageUseCase {

    private final SaveFileEntityPort saveFileEntityPort;
    private final SaveFilePort saveFilePort;

    @Override
    @Transactional
    public UploadImageResult upload(List<MultipartFile> images) {

        List<UploadImageResult.FileInfo> fileInfos = new ArrayList<>();
        List<File> files = new ArrayList<>();

        for (MultipartFile image : images) {
            File file = File.createImageFile(image.getName(), image.getSize());
            Long savedId = saveFileEntityPort.save(file);

            files.add(file);
            fileInfos.add(new UploadImageResult.FileInfo(file, savedId));
        }

        for (File file : files) {
            saveFilePort.save(file);
        }

        return new UploadImageResult(fileInfos);
    }
}