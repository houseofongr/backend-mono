package com.hoo.aoo.admin.application.port.in.user;

import org.springframework.data.domain.Page;

public record QueryUserHomeListResult(
        Page<Home> homes
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
