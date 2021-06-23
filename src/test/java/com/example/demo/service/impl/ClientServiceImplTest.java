package com.example.demo.service.impl;

import com.example.demo.domain.Client;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.PasswordChangeDTO;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.util.ClientCreator;
import com.example.demo.util.PasswordChangeCreator;
import com.example.demo.util.ProfileCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Tests for client service")
class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl clientServiceImpl;

    @MockBean
    private ClientRepository clientRepositoryMock;

    @MockBean
    private ProfileRepository profileRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Client> clientPage = new PageImpl<>(List.of(ClientCreator.createClientValid()));
        when(clientRepositoryMock.findAll(any(PageRequest.class))).thenReturn(clientPage);
        when(clientRepositoryMock.save(any(Client.class))).thenReturn(ClientCreator.createClientValid());
        when(clientRepositoryMock.findByIdAndActiveClient(anyLong(), anyLong())).thenReturn(Optional.of(ClientCreator.createClientValid()));
        when(clientRepositoryMock.findByEmailAndActiveClient(anyString(), anyLong())).thenReturn(Optional.of(ClientCreator.createClientValid()));
        doNothing().when(clientRepositoryMock).delete(any(Client.class));
        when(profileRepositoryMock.findById(anyLong())).thenReturn(Optional.of(ProfileCreator.createProfileValid()));
    }

    @Test
    @DisplayName("List return list of client inside page object when successful")
    void list_ReturnsListOfClientsInsidePageObject_WhenSuccessful() {
        String expectedName = ClientCreator.createClientDtoValid().getName();
        Page<Client> clientPage = clientServiceImpl.findAll(PageRequest.of(1,1));

        assertThat(clientPage).isNotEmpty().hasSize(1);
        assertThat(clientPage.toList()).isNotEmpty();
        assertThat(clientPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Create client when successful")
    void createClient_WhenSuccessful() {
        ClientDTO clientDTO = clientServiceImpl.create(ClientCreator.createClintDTOToBeSaved());
        assertThat(clientDTO.getEmail()).isNotNull().isEqualTo(ClientCreator.createClientValid().getEmail());
    }

    @Test
    @DisplayName("Updated client when successful")
    void UpdateClient_WhenSuccessful() {
        assertThatCode(() -> clientServiceImpl.update(ClientCreator.createClintDTOToBeUpdated()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Find by id client when successful")
    void findByIdClient_WhenSuccessful() {
        ClientDTO clientDtoValid = ClientCreator.createClientDtoValid();
        ClientDTO client = clientServiceImpl.findByIdAndActiveClientDTO(Long.valueOf(clientDtoValid.getId()));

        assertThat(client).isNotNull();
        assertThat(client.getName()).isEqualTo(clientDtoValid.getName());
        assertThat(client.getId()).isEqualTo(clientDtoValid.getId());
    }

    @Test
    @DisplayName("Find by email client when successful")
    void findByEmailClient_WhenSuccessful() {
        String expectedEmail = ClientCreator.createClientValid().getEmail();
        ClientDTO clientDTO = clientServiceImpl.findByEmailAndActiveClient(expectedEmail);

        assertThat(clientDTO.getEmail()).isNotNull().isEqualTo(expectedEmail);
    }

    @Test
    @DisplayName("Delete client when successful")
    void deleteClient_WhenSuccessful(){
        assertThatCode(() -> clientServiceImpl.delete(1l))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Change password client when successful")
    void ChangePasswordClient_WhenSuccessful(){
        assertThatCode(() -> clientServiceImpl.changePassword(PasswordChangeCreator.passwordChangeCreatorValid()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Change password client when password repeat")
    void ChangePasswordClint_WhenPasswordRepeat(){
        PasswordChangeDTO passwordChangeDTO = PasswordChangeCreator.passwordChangeCreatorValid();
        passwordChangeDTO.setNewPassword("12345678");

        Assertions.assertThrows(ValidationException.class,() -> clientServiceImpl.changePassword(passwordChangeDTO));
    }

}