package com.hazalyarimdunya.auth_jwt_app.dto;

import com.hazalyarimdunya.auth_jwt_app.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    //user dto
    //client’tan gelen veriyi DTO ile alıyoruz

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private UserEntity.Role role = UserEntity.Role.USER; // default USER
}