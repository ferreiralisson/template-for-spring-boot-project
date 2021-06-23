package com.example.demo.controller;

import com.example.demo.domain.Client;
import com.example.demo.dto.ClientDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.service.ClientService;
import com.example.demo.util.ClientCreator;
import com.example.demo.util.PasswordChangeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for client controller")
class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Client> clientPage = new PageImpl<>(List.of(ClientCreator.createClientValid()));
        when(clientServiceMock.findAll(any())).thenReturn(clientPage);
        when(clientServiceMock.create(any(ClientDTO.class))).thenReturn(ClientCreator.createClientDtoValid());
        when(clientServiceMock.findByIdAndActiveClientDTO(anyLong())).thenReturn(ClientCreator.createClientDtoValid());
        when(clientServiceMock.findByEmailAndActiveClient(anyString())).thenReturn(ClientCreator.createClientDtoValid());
        doNothing().when(clientServiceMock).delete(anyLong());
        doNothing().when(clientServiceMock).changePassword(any());
        doNothing().when(clientServiceMock).update(any());
    }

    @Test
    @DisplayName("List return list of client inside page object when successful")
    void list_ReturnsListOfClientsInsidePageObject_WhenSuccessful() {
        String expectedName = ClientCreator.createClientValid().getName();
        Page<Client> clientPage = clientController.listAll(null).getBody();

        assertThat(clientPage).isNotEmpty().hasSize(1);
        assertThat(clientPage.toList()).isNotEmpty();
        assertThat(clientPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Create client when successful")
    void createClient_WhenSuccessful() {
        ClientDTO clientTobeSaved = ClientCreator.createClintDTOToBeSaved();
        String expectedName = clientTobeSaved.getName();
        String idCreatedClient = "1";
        ClientDTO clientDToSaved = clientController.create(clientTobeSaved).getBody();

        assertThat(clientDToSaved).isNotNull();
        assertThat(clientDToSaved.getName()).isEqualTo(expectedName);
        assertThat(clientDToSaved.getId()).isEqualTo(idCreatedClient);
    }

    @Test
    @DisplayName("Updated client when successful")
    void UpdateClient_WhenSuccessful() {
        ClientDTO clientDTO = ClientCreator.createClintDTOToBeSaved();
        assertThatCode(() -> clientController.update(clientDTO))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = clientController.update(clientDTO);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    @Test
    @DisplayName("Find by id client when successful")
    void findByIdClient_WhenSuccessful() {
        ClientDTO clientDtoValid = ClientCreator.createClientDtoValid();
        ClientDTO client = clientController.findById(Long.valueOf(clientDtoValid.getId())).getBody();

        assertThat(client).isNotNull();
        assertThat(client.getName()).isEqualTo(clientDtoValid.getName());
        assertThat(client.getId()).isEqualTo(clientDtoValid.getId());
    }

    @Test
    @DisplayName("Find by email client when successful")
    void findByEmailClient_WhenSuccessful() {
        ClientDTO clientDtoValid = ClientCreator.createClientDtoValid();
        ClientDTO clientDTO = clientController.findByEmail(clientDtoValid.getEmail()).getBody();

        assertThat(clientDTO).isNotNull();
        assertThat(clientDTO.getName()).isEqualTo(clientDtoValid.getName());
        assertThat(clientDTO.getEmail()).isEqualTo(clientDtoValid.getEmail());
        assertThat(clientDTO.getId()).isEqualTo(clientDtoValid.getId());
    }

    @Test
    @DisplayName("Delete client when successful")
    void deleteClient_WhenSuccessful() {
        assertThatCode(() -> clientController.delete(1l))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = clientController.delete(1l);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    @Test
    @DisplayName("Change password client when successful")
    void ChangePasswordClient_WhenSuccessful() {
        assertThatCode(() -> clientController.changePassword(PasswordChangeCreator.passwordChangeCreatorValid()))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = clientController.changePassword(PasswordChangeCreator.passwordChangeCreatorValid());
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    @Test
    @DisplayName("findAll returns an empty list client when client is not found")
    void findAll_ReturnsEmptyListOfClient_WhenClientIsNotFound() {
        when(clientServiceMock.findAll(any())).thenReturn(Page.empty());
        Page<Client> clientPageNotFound = clientController.listAll(any()).getBody();

        assertThat(clientPageNotFound).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns an empty client when client is not found")
    void findById_ReturnsEmptyClient_WhenClientIsNotFound() {
        when(clientServiceMock.findByIdAndActiveClientDTO(anyLong())).thenThrow(BadRequestException.class);
        Assertions.assertThrows(BadRequestException.class, () -> clientController.findById(anyLong()));
    }

    @Test
    @DisplayName("findByName returns an empty client when client is not found")
    void findByName_ReturnsEmptyClient_WhenClientIsNotFound() {
        when(clientServiceMock.findByEmailAndActiveClient(anyString())).thenThrow(BadRequestException.class);
        Assertions.assertThrows(BadRequestException.class, () -> clientController.findByEmail(anyString()));
    }

}