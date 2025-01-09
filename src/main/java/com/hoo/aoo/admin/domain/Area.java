package com.hoo.aoo.admin.domain;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import lombok.Getter;

@Getter
public class Area {

    private final Short width;
    private final Short height;

    public Area(Integer width, Integer height) throws AreaLimitExceededException {
        validateLimit(height, width);
        this.height = height.shortValue();
        this.width = width.shortValue();
    }

    public Integer getHeight() {
        return height == null? null : Short.toUnsignedInt(height);
    }

    public Integer getWidth() {
        return width == null? null : Short.toUnsignedInt(width);
    }

    private void validateLimit(Integer... params) throws AreaLimitExceededException {
        for (Integer param : params) {
            if (param <= 0 || param > Short.MAX_VALUE) throw new AreaLimitExceededException();
        }
    }
}
