package com.hoo.aoo.admin.domain;

import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import lombok.Getter;

@Getter
public class Axis {

    private final Float x;
    private final Float y;
    private final Float z;

    public Axis(Float x, Float y, Float z) throws AxisLimitExceededException {
        validateLimit(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Axis from(Float x, Float y) throws AxisLimitExceededException {
        return new Axis(x,y,0f);
    }

    private void validateLimit(Float... params) throws AxisLimitExceededException {
        for (Float param : params) {
            if (param < 0 || param > Short.MAX_VALUE) throw new AxisLimitExceededException();
        }
    }
}
