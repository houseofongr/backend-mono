package com.hoo.aoo.aar.application.port.in.home;

import jakarta.validation.constraints.NotEmpty;

public record UpdateHomeNameCommand(
        @NotEmpty String newName
) {
}
