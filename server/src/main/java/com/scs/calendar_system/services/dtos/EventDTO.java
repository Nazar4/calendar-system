package com.scs.calendar_system.services.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventDTO {
    @NotBlank(message = "Title must not be blank")
    private String title;
    private String description;

    @NotNull(message = "Start time must be provided")
    private LocalDateTime startTime;

    @NotNull(message = "End time must be provided")
    private LocalDateTime endTime;

    private String location;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }
}
