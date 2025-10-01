package com.hazalyarimdunya.auth_jwt_app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)//Dto daki tum alanlar default olarak responseta doner. set edilemeyen alanlar null doner.Null alanlarin donmemesi icin bunu ekleyebiliriz.
public class TodoDto {


    public static final Long serialVersionUID=1L;

    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Desc cannot be empty")
    private String description;


}
