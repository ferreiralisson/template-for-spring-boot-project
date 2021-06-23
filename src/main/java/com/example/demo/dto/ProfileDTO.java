package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ProfileDTO {

    private Long id;
    @NotNull(message = "Nome Obrigatorio!")
    private String name;
}
