package com.example.demo.service;

import com.example.demo.domain.Client;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.PasswordChangeDTO;
import com.example.demo.domain.UserSS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    UserSS authenticated();
    void changePassword(PasswordChangeDTO passwordChangeDTO);
    ClientDTO create(ClientDTO clientDTO);
    void update(ClientDTO clientDTO);
    void delete(long id);
    ClientDTO findByEmailAndActiveClient(String email);
    Page<Client> findAll(Pageable pageable);
    ClientDTO findByIdAndActiveClientDTO(long id);
}
