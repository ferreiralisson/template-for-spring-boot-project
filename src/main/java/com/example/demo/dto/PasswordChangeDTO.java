package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class PasswordChangeDTO {
    @NotEmpty(message = "Campo senha obrigatorio")
    @Length(min = 8, message = "A senha precisa ter no minimo 8 caracteres")
    public String oldPassword;

    @NotEmpty(message = "Campo nova senha obrigatorio")
    @Length(min = 8, message = "A nova senha precisa ter no minimo 8 caracteres")
    public String newPassword;
}
