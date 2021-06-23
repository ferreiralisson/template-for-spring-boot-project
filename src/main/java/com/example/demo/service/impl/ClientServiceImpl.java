package com.example.demo.service.impl;

import com.example.demo.domain.Client;
import com.example.demo.domain.Profile;
import com.example.demo.domain.UserSS;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.PasswordChangeDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.util.Constants.ACTIVE;
import static com.example.demo.util.Constants.INACTIVE;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClientServiceImpl implements ClientService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ClientRepository clientRepository;
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    private static final String USER_NOT_FOUND = "Usuário não encontrado";

    @Override
    public UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            log.info("{}", e);
            return null;
        }
    }

    @Override
    public void changePassword(PasswordChangeDTO passwordChangeDTO) {
        if (passwordChangeDTO.getOldPassword().equals(passwordChangeDTO.newPassword)) {
            throw new ValidationException("Senha Repetida");
        }

        Authentication currentClient = SecurityContextHolder.getContext().getAuthentication();

        if (currentClient != null) {
            UserSS user = (UserSS) currentClient.getPrincipal();
            if (!bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
                throw new ValidationException("Senha Invalida");
            }

            Optional<Client> clientOptional = clientRepository.findByEmail(currentClient.getName());
            if(clientOptional.isPresent()){
                clientOptional.get().setPassword(bCryptPasswordEncoder.encode(passwordChangeDTO.getNewPassword()));
                clientRepository.save(clientOptional.get());
            }
        }
    }

    @Override
    @Transactional
    public ClientDTO create(ClientDTO clientDTO) {
        Client clientMapper = modelMapper.map(clientDTO, Client.class);
        clientMapper.setPassword(bCryptPasswordEncoder.encode(clientDTO.getPassword()));
        clientMapper.setProfiles(getProfile(Long.valueOf(clientDTO.getIdProfile())));
        clientMapper.setStatus(ACTIVE);
        clientMapper.setCreated(now());
        Client clientSaved = clientRepository.save(clientMapper);
        return modelMapper.map(clientSaved, ClientDTO.class);
    }

    private List<Profile> getProfile(Long id) {
        Optional<Profile> profiles = profileRepository.findById(id);
        return profiles.stream().map(profile -> Profile.builder()
                .id(profile.getId())
                .name(profile.getName())
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(ClientDTO clientDTO) {
        Client clientFound = findByIdAndActiveClient(Long.valueOf(clientDTO.getId()));
        clientFound.setName(clientDTO.getName());
        clientFound.setTelephone(clientDTO.getTelephone());
        clientFound.setStatus(ACTIVE);
        clientFound.setProfiles(getProfile(Long.valueOf(clientDTO.getIdProfile())));
        clientFound.setUpdated(now());
        clientRepository.save(clientFound);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Client clientFound = findByIdAndActiveClient(id);
        clientFound.setStatus(INACTIVE);
        clientFound.setDeleted(now());
        clientRepository.save(clientFound);
    }

    @Override
    public ClientDTO findByEmailAndActiveClient(String email) {
        Client client = clientRepository.findByEmailAndActiveClient(email, ACTIVE)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        Page<Client> clientsPage = clientRepository.findAll(pageable);
        return new PageImpl<>(clientsPage.stream().filter(client -> client.getStatus().compareTo(ACTIVE) == 0)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public ClientDTO findByIdAndActiveClientDTO(long id) {
        return modelMapper.map(findByIdAndActiveClient(id), ClientDTO.class);
    }

    private Client findByIdAndActiveClient(long id) {
        return clientRepository.findByIdAndActiveClient(id, ACTIVE)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
    }
}
