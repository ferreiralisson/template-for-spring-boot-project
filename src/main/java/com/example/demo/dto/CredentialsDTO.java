package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsDTO {

    @Email(message = "Email precisa ser valido no seguinte formato: email@exemplo.com")
    private String email;

    @NotEmpty(message = "Senha Invalida!")
    @Min(value = 8, message = "A Senha precisa ter no minimo 8 caracteres")
    private String password;
}
