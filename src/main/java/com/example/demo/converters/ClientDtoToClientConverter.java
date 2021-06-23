package com.example.demo.converters;

import com.example.demo.domain.Client;
import com.example.demo.dto.ClientDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ClientDtoToClientConverter implements Converter<ClientDTO, Client> {

    @Override
    public Client convert(MappingContext<ClientDTO, Client> context) {
        ClientDTO clientDTO = context.getSource();
        return Client.builder()
                .name(clientDTO.getName())
                .email(clientDTO.getEmail())
                .telephone(clientDTO.getTelephone())
                .build();
    }
}
