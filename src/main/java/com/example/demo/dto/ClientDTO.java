package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ClientDTO {

    private String id;
    @NotEmpty(message = "Nome Invalido!")
    @Size(min = 3, message = "Nome precisa ter no minimo 3 caracteres")
    private String name;
    @Email(message = "Email precisa ser valido no seguinte formato: email@exemplo.com")
    private String email;
    @Size(min = 10, message = "Número invalido, precisar ter no minimo 10 digitos com o seguinte formato: (xx) xxxxx-xxxx")
    private String telephone;
    @NotEmpty(message = "Senha Invalida!")
    @Size(min = 8, message = "A Senha precisa ter no minimo 8 caracteres")
    private String password;
    private boolean status;
    @NotEmpty(message = "Perfil obrigatorio!")
    private String idProfile;

    private String created;
    private String updated;
    private String deleted;
    private List<ProfileDTO> profiles;
}

