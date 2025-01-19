package com.hoo.aoo.admin.application.port.in.user;

import java.util.List;

public record QueryUserHomeListResult(
        List<Home> homes
) {
    public record Home(
            Long id,
            String title,
            String author,
            String description,
            String createdDate,
            String updatedDate
    ) {

    }
}
