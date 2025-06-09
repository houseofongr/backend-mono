package com.aoo.admin.application.port.in.space;

import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;

public record UpdateSpaceCommand() {

    public record Detail(
            String title,
            String description
    ) {
        public Detail {
            if ((title != null && (title.isBlank() || title.length() > 100)) ||
                (description != null && description.length() > 5000))
                throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }

    public record Position(
            Float sx,
            Float sy,
            Float ex,
            Float ey
    ) {
        public Position {
            if ((sx != null && (sx < 0 || sx > 1)) ||
                (sy != null && (sy < 0 || sy > 1)) ||
                (ex != null && (ex < 0 || ex > 1)) ||
                (ey != null && (ey < 0 || ey > 1))
            ) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }
}
