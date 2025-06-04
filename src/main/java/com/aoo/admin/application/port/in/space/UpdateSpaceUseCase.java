package com.aoo.admin.application.port.in.space;

import com.aoo.common.application.port.in.MessageDto;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateSpaceUseCase {
    UpdateSpaceResult.Detail updateDetail(Long spaceId, UpdateSpaceCommand command);
    UpdateSpaceResult.InnerImage updateInnerImage(Long spaceId, MultipartFile innerImage);
}
