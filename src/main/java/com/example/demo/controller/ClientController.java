package com.example.demo.controller;

import com.example.demo.domain.Client;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.PasswordChangeDTO;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.demo.util.Constants.*;
import static com.example.demo.util.Util.localDateTimeToString;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = API_URL_PREFIX + "/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private static final String REPONSE_CLIENT = "Response do cliente, finish -  {}";

    @PostMapping(path = "/change-password")
    @PreAuthorize(SECURITY_ROLE_USER)
    public ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeDTO passwordChangerDTO) {
        log.info("Alterar Senha cliente em clientService.changePassword, start -  {}",
                localDateTimeToString(now()));
        clientService.changePassword(passwordChangerDTO);
        log.info("Alterar Senha cliente em clientService.changePassword, finish -  {}",
                localDateTimeToString(now()));
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody @Valid ClientDTO clientDTO) {
        log.info("Criacao do cliente em clientService.create, start -  {}", localDateTimeToString(now()));
        ClientDTO client = clientService.create(clientDTO);
        log.info(REPONSE_CLIENT, localDateTimeToString(now()));
        return new ResponseEntity<>(client, CREATED);
    }

    @PutMapping
    @PreAuthorize(SECURITY_ROLE_USER)
    public ResponseEntity<Void> update(@RequestBody ClientDTO clientDTO){
        log.info("Atualização do cliente em clientService.update, start -  {}", localDateTimeToString(now()));
        clientService.update(clientDTO);
        log.info("Atualização do cliente em clientService.update, finish -  {}", localDateTimeToString(now()));
        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize(SECURITY_ROLE_ADMIN)
    public ResponseEntity<Void> delete(@PathVariable long id){
        log.info("delete cliente em clientService.delete, start -  {}", localDateTimeToString(now()));
        clientService.delete(id);
        log.info("delete do cliente, finish -  {}", localDateTimeToString(now()));
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping(path = "/by-email")
    @PreAuthorize(SECURITY_ROLE_USER)
    public ResponseEntity<ClientDTO> findByEmail(@RequestParam String email){
        log.info("listagem de cliente por email em clientService.findByEmailAndActiveClientOrElseThrowException, start -  {}", localDateTimeToString(now()));
        ClientDTO client = clientService.findByEmailAndActiveClient(email);
        log.info(REPONSE_CLIENT, localDateTimeToString(now()));
        return new ResponseEntity<>(client, OK);
    }

    @GetMapping
    @PreAuthorize(SECURITY_ROLE_ADMIN)
    public ResponseEntity<Page<Client>> listAll(Pageable pageable){
        log.info("listagem completa de clientes em clientService.listAll, start -  {}", localDateTimeToString(now()));
        Page<Client> client = clientService.findAll(pageable);
        log.info(REPONSE_CLIENT, localDateTimeToString(now()));
        return new ResponseEntity<>(client, OK);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize(SECURITY_ROLE_USER)
    public ResponseEntity<ClientDTO> findById(@PathVariable long id){
        log.info("listagem de cliente por id em clientService.findById, start -  {}", localDateTimeToString(now()));
        ClientDTO clientDTO = clientService.findByIdAndActiveClientDTO(id);
        log.info(REPONSE_CLIENT, localDateTimeToString(now()));
        return new ResponseEntity<>(clientDTO, OK);
    }
}
