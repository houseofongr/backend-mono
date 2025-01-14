package com.hoo.aoo.admin.domain;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import lombok.Getter;

@Getter
public class Area {

    private final Float width;
    private final Float height;

    public Area(Float width, Float height) throws AreaLimitExceededException {
        validateLimit(height, width);
        this.height = height;
        this.width = width;
    }

    private void validateLimit(Float... params) throws AreaLimitExceededException {
        for (Float param : params) {
            if (param <= 0 || param > Short.MAX_VALUE) throw new AreaLimitExceededException();
        }
    }
}
