package com.example.demo.converters;

import com.example.demo.domain.Client;
import com.example.demo.domain.Profile;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ProfileDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.util.Constants.ACTIVE;
import static com.example.demo.util.Util.localDateTimeToString;

public class ClientToClientDtoConverter implements Converter<Client, ClientDTO> {

    @Override
    public ClientDTO convert(MappingContext<Client, ClientDTO> context) {
        Client client = context.getSource();

        return ClientDTO.builder()
                .id(client.getId().toString() != null ? client.getId().toString() : null)
                .name(client.getName())
                .email(client.getEmail())
                .telephone(client.getTelephone())
                .password(client.getPassword())
                .status(isStatus(client.getStatus()))
                .created(client.getCreated() != null ? localDateTimeToString(client.getCreated()) : null)
                .updated(client.getUpdated() != null ? localDateTimeToString(client.getUpdated()) : null)
                .deleted(client.getDeleted() != null ? localDateTimeToString(client.getUpdated()) : null)
                .profiles(getProfiles(client.getProfiles()))
                .build();

    }

    private List<ProfileDTO> getProfiles(List<Profile> profiles){
        List<ProfileDTO> listOfProfileDTO = new ArrayList<>();
        profiles.stream().forEach(profile ->
                listOfProfileDTO.add(ProfileDTO.builder()
                        .id(profile.getId())
                        .name(profile.getName())
                        .build())
        );

        return listOfProfileDTO;
    }

    private boolean isStatus(Long status){
        return (status.compareTo(ACTIVE) == 0);
    }
}
