package com.aoo.file.application.service;

import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.out.database.SaveFilePort;
import com.aoo.file.application.port.out.filesystem.RandomFileNamePort;
import com.aoo.file.application.port.out.filesystem.WriteFilePort;
import com.aoo.file.domain.*;
import com.aoo.file.domain.exception.FileExtensionMismatchException;
import com.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class UploadService {

    private final FileProperties fileProperties;
    private final SaveFilePort saveImageFilePort;
    private final WriteFilePort writeFilePort;
    private final RandomFileNamePort randomFileNamePort;

    public UploadFileResult upload(List<MultipartFile> files, FileIdCreateStrategy fileIdCreateStrategy) {
        return upload(files, null, fileIdCreateStrategy);
    }

    public UploadFileResult.FileInfo upload(MultipartFile file, FileIdCreateStrategy fileIdCreateStrategy) {
        return upload(List.of(file), fileIdCreateStrategy).fileInfos().getFirst();
    }

    public UploadFileResult upload(List<MultipartFile> files, Long ownerId, FileIdCreateStrategy idCreateStrategy) {

        // int[0] : total file name count | int[1] : name suffix(increase)
        Map<String, int[]> fileNameMap = fileNameMap(files);

        List<UploadFileResult.FileInfo> fileInfos = new ArrayList<>();

        for (MultipartFile multipartFile : files) {

            try {
                String originalFilename = getOriginalFilename(multipartFile.getOriginalFilename(), fileNameMap);
                if (originalFilename == null) throw new FileException(FileErrorCode.FILE_NAME_EMPTY);

                String fileSystemName = randomFileNamePort.getName(originalFilename);

                FileId fileId = idCreateStrategy.create(originalFilename, fileSystemName);
                FileSize fileSize = new FileSize(multipartFile.getSize(), fileProperties.getFileSizeLimit());

                File file = File.create(fileId, FileStatus.CREATED, new OwnerId(ownerId), fileSize);

                writeFilePort.write(file, multipartFile);

                Long savedId = saveImageFilePort.save(file);

                log.info("파일 생성완료 : {}", file);

                fileInfos.add(UploadFileResult.FileInfo.from(file, savedId));

            } catch (IOException e) {
                throw new FileException(e, FileErrorCode.NEW_FILE_CREATION_FAILED);

            } catch (FileSizeLimitExceedException e) {
                throw new FileException(e, FileErrorCode.FILE_SIZE_LIMIT_EXCEED);

            } catch (FileExtensionMismatchException e) {
                throw new FileException(e, FileErrorCode.INVALID_FILE_EXTENSION);

            }
        }

        return new UploadFileResult(fileInfos);
    }

    private Map<String, int[]> fileNameMap(List<MultipartFile> files) {

        Map<String, int[]> map = new HashMap<>();

        for (MultipartFile file : files) {
            if (map.get(file.getOriginalFilename()) == null) map.put(file.getOriginalFilename(), new int[]{1, 1});
            else map.get(file.getOriginalFilename())[0]++;
        }

        return map;
    }

    private String getOriginalFilename(String originalFileName, Map<String, int[]> fileNameMap) {
        if (fileNameMap.get(originalFileName)[0] == 1) return originalFileName;
        else {
            String[] split = originalFileName.split("\\.");
            return split[0] + "-" + fileNameMap.get(originalFileName)[1]++ + "." + split[1];
        }
    }
}
