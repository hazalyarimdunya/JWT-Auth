package com.hazalyarimdunya.auth_jwt_app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TodoDto {

    @NotEmpty
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;
}
