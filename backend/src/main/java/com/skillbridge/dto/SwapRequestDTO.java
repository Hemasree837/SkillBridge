package com.skillbridge.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data received from the frontend when creating a new swap request.
 */
@Data
public class SwapRequestDTO {

    @NotNull(message = "Sender id is required")
    private Long senderId;

    @NotNull(message = "Receiver id is required")
    private Long receiverId;

    private String message;
}
