package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Nome Invalido!")
    @Size(min = 3, message = "Nome precisa ter no minimo 3 caracteres")
    private String name;

    @Column(name = "email", unique = true)
    @Email(message = "Email precisa ser valido no seguinte formato: email@exemplo.com")
    private String email;

    @Column(name = "telephone")
    @Size(min = 10, message = "Número invalido, precisar ter no minimo 10 digitos com o seguinte formato: (xx) xxxxx-xxxx")
    private String telephone;

    @Column(name = "password")
    @NotEmpty(message = "Senha Invalida!")
    @Size(min = 8, message = "A Senha precisa ter no minimo 8 caracteres")
    private String password;

    @Column(name = "image")
    @Lob
    private String image;

    @Column(name = "status")
    private Long status;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "deleted")
    private LocalDateTime deleted;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "client_profile", joinColumns = @JoinColumn(name = "fk_client", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_profile", referencedColumnName = "idprofile"))
    @NotEmpty(message = "Perfil obrigatorio!")
    private List<Profile> profiles;
}
