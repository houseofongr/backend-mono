package com.hoo.aoo.admin.domain;

import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;

public class Axis {

    private final Short x;
    private final Short y;
    private final Short z;

    public Axis(Integer x, Integer y, Integer z) throws AxisLimitExceededException {
        validateLimit(x, y, z);
        this.x = x.shortValue();
        this.y = y.shortValue();
        this.z = z.shortValue();
    }

    public Axis(Integer x, Integer y) throws AxisLimitExceededException {
        validateLimit(x, y);
        this.x = x.shortValue();
        this.y = y.shortValue();
        this.z = null;
    }

    public Integer getX() {
        return x == null? null : Short.toUnsignedInt(x);
    }

    public Integer getY() {
        return y == null? null : Short.toUnsignedInt(y);
    }

    public Integer getZ() {
        return z == null? null : Short.toUnsignedInt(z);
    }

    private void validateLimit(Integer... params) throws AxisLimitExceededException {
        for (Integer param : params) {
            if (param < 0 || param > Short.MAX_VALUE) throw new AxisLimitExceededException();
        }
    }
}
