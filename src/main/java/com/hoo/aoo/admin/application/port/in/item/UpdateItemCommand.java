package com.hoo.aoo.admin.application.port.in.item;

import jakarta.validation.constraints.NotNull;

public record UpdateItemCommand(
        @NotNull ItemData updateData
) {
}
