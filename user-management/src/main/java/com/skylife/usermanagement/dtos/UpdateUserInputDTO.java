package com.skylife.usermanagement.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record 
UpdateUserInputDTO(@NotNull Long id, @NotBlank String name, 
        @Email String email) {


}
