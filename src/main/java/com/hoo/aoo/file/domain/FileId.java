package com.hoo.aoo.file.domain;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import lombok.Getter;

@Getter
public class FileId {
    private final String baseDir;
    private final Authority authority;
    private final FileType fileType;
    private final String realFileName;
    private final String fileSystemName;

    private FileId(String baseDir, Authority authority, FileType fileType, String realFileName, String fileSystemName) {
        this.baseDir = baseDir;
        this.authority = authority;
        this.fileType = fileType;
        this.realFileName = realFileName;
        this.fileSystemName = fileSystemName;
    }

    public static FileId create(String baseDir, Authority authority, FileType fileType, String realFileName, String fileSystemName)  {
        if (baseDir.charAt(baseDir.length() - 1) == '/')
            baseDir = baseDir.substring(0, baseDir.length() - 1);

        FileId fileId = new FileId(baseDir, authority, fileType, realFileName, fileSystemName);

        fileId.verifyExtension(fileType, realFileName);

        return fileId;
    }

    public static FileId load(String parentDir, String realFileName, String fileSystemName)  {
        String[] dirs = parentDir.split("/");

        String fileTypeDir = dirs[dirs.length - 1];
        FileType fileType = FileType.of(fileTypeDir);

        String authorityDir = dirs[dirs.length - 2];
        Authority authority = pathToAuthority(authorityDir);

        String baseDir = parentDir.split("/" + authorityDir)[0];

        return new FileId(baseDir, authority, fileType, realFileName, fileSystemName);
    }

    private static Authority pathToAuthority(String authorityDir) {
        switch (authorityDir) {
            case "public":
                return Authority.PUBLIC_FILE_ACCESS;
            case "private":
                return Authority.PRIVATE_ALL_IMAGE_ACCESS;
            default:
                throw new IllegalFileAuthorityDirException(authorityDir);
        }
    }

    @Override
    public String toString() {
        return "[" + getPath() + "] " +
               "authority=" + authority +
               ", fileType=" + fileType +
               ", realFileName='" + realFileName + '\'' +
               ", fileSystemName='" + fileSystemName;
    }

    public void verifyExtension(FileType fileType, String fileName) {
        switch (fileType) {
            case IMAGE -> {
                if (!fileName.matches(".*\\.(?:png|jpe?g|svg|gif)$"))
                    throw new FileExtensionMismatchException(fileType, fileName);
            }
            case AUDIO -> {
                if (!fileName.matches(".*\\.(?:mp3|wav)$"))
                    throw new FileExtensionMismatchException(fileType, fileName);
            }
            case VIDEO -> {
                throw new UnsupportedOperationException();
            }
        }
    }

    public String getPath() {
        return getDirectory() + "/" + fileSystemName;
    }

    public String getFilePath() {
        return "file:" + getPath();
    }

    private String getAuthorityPath() {
        switch (authority) {
            case PUBLIC_FILE_ACCESS -> {
                return "public";
            }
            case PRIVATE_ALL_IMAGE_ACCESS -> {
                return "private";
            }
            default -> throw new IllegalStateException("Unexpected value: " + authority);
        }
    }

    public String getDirectory() {
        return baseDir + "/" + getAuthorityPath() + "/" + fileType.getPath();
    }
}
