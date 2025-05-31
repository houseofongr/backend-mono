package com.aoo.admin.application.port.in.space;

import com.aoo.common.application.port.in.MessageDto;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateSpaceUseCase {
    MessageDto update(Long spaceId, UpdateSpaceCommand command);
    MessageDto updateInnerImage(Long spaceId, MultipartFile innerImage);
}
