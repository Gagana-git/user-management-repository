package com.skylife.usermanagement.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record 
CreateUserInputDTO(Long id, @NotBlank String name, 
		@NotBlank  @Email String email,@NotBlank  String password) {


}
