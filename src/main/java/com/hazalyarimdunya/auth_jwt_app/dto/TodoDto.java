package com.hazalyarimdunya.auth_jwt_app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TodoDto {


    public static final Long serialVersionUID=1L;
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Desc cannot be empty")
    private String description;


}
