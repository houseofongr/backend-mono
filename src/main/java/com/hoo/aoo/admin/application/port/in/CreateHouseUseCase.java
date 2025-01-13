package com.hoo.aoo.admin.application.port.in;

import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface CreateHouseUseCase {
    CreateHouseResult create(Map<String, Object> formData) throws AdminException, AxisLimitExceededException, AreaLimitExceededException;
}
