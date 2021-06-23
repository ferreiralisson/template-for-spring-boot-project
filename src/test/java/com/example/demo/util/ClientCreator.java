package com.example.demo.util;

import com.example.demo.domain.Client;
import com.example.demo.domain.Profile;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ProfileDTO;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.util.Constants.ACTIVE;
import static com.example.demo.util.Util.localDateTimeToString;
import static java.time.LocalDateTime.now;

public class ClientCreator {

    public static Client createClintToBeSaved(){
        return Client.builder()
                .name("usuarioTeste")
                .email("teste@email.com")
                .telephone("8199888899")
                .password("12345678")
                .profiles(createProfile())
                .status(ACTIVE)
                .created(now())
                .build();
    }

    public static Client createClientValid(){
        return Client.builder()
                .id(1L)
                .name("usuarioTeste")
                .email("teste@email.com")
                .telephone("8199888899")
                .password("12345678")
                .profiles(createProfile())
                .status(ACTIVE)
                .created(now())
                .build();
    }

    public static ClientDTO createClientDtoValid(){
        return ClientDTO.builder()
                .id("1")
                .name("usuarioTeste")
                .email("teste@email.com")
                .telephone("8199888899")
                .password("12345678")
                .profiles(createProfileDTO())
                .status(true)
                .created(localDateTimeToString(now()))
                .build();
    }

    public static ClientDTO createClintDTOToBeSaved(){
        return ClientDTO.builder()
                .name("usuarioTeste")
                .email("teste@email.com")
                .telephone("8199888899")
                .password("12345678")
                .idProfile("1")
                .build();
    }

    public static ClientDTO createClintDTOToBeUpdated(){
        return ClientDTO.builder()
                .id("1")
                .name("usuarioTeste")
                .email("teste@email.com")
                .telephone("8199888899")
                .password("12345678")
                .idProfile("1")
                .build();
    }

    public static List<Profile> createProfile() {
        List<Profile> listOfProfile = new ArrayList<>();
        Profile profile = Profile.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();
        listOfProfile.add(profile);
        return listOfProfile;
    }

    public static List<ProfileDTO> createProfileDTO(){
        return List.of((ProfileDTO.builder()
                .id(1l)
                .name("ROLE_USER")
                .build()));
    }

}
