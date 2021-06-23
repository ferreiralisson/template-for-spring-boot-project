package com.example.demo.repository;

import com.example.demo.domain.Client;
import com.example.demo.util.ClientCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.example.demo.util.Constants.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@DisplayName("Tests for Client Repository")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Save persists client when successful")
    void save_PersistClient_WhenSuccessful(){
        Client clientTobeSaved = ClientCreator.createClintToBeSaved();
        Client clientSaved = this.clientRepository.save(clientTobeSaved);

        assertThat(clientSaved).isNotNull();
        assertThat(clientSaved.getId()).isNotNull();
        assertThat(clientSaved.getName()).isEqualTo(clientSaved.getName());
    }

    @Test
    @DisplayName("Update client when successful")
    void updatesClient_WhenSuccessful(){
        Client clientTobeSaved = ClientCreator.createClintToBeSaved();
        Client clientSaved = this.clientRepository.save(clientTobeSaved);

        clientSaved.setName("usuarioTeste1");

        Client clientUpdated = this.clientRepository.save(clientSaved);

        assertThat(clientUpdated).isNotNull();
        assertThat(clientUpdated.getId()).isNotNull();
        assertThat(clientUpdated.getName()).isEqualTo(clientSaved.getName());
    }

    @Test
    @DisplayName("Delete client when successful")
    void deleteClient_WhenSuccessful(){
        Client clientTobeSaved = ClientCreator.createClintToBeSaved();
        Client clientSaved = this.clientRepository.save(clientTobeSaved);
        this.clientRepository.delete(clientSaved);
        Optional<Client> clientOptional = this.clientRepository.findById(clientSaved.getId());

        assertThat(clientOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by email client when successful")
    void findByEmail_ReturnsClient_WhenSuccessful(){
        Client clientTobeSaved = ClientCreator.createClintToBeSaved();
        Client clientSaved = this.clientRepository.save(clientTobeSaved);
        String email = clientSaved.getEmail();
        Optional<Client> clientOptional = this.clientRepository.findByEmail(email);

        assertThat(clientOptional).isNotNull();
        assertThat(clientOptional.get().getEmail()).isEqualTo(email);
        assertThat(clientOptional.get().getName()).isEqualTo(clientSaved.getName());
    }

    @Test
    @DisplayName("Find by email and active client when successful")
    void findByEmail_ReturnsActiveClient_WhenSuccessful(){
        Client clientTobeSaved = ClientCreator.createClintToBeSaved();
        Client clientSaved = this.clientRepository.save(clientTobeSaved);
        String email = clientSaved.getEmail();
        Optional<Client> clientOptional = this.clientRepository.findByEmailAndActiveClient(email, ACTIVE);

        assertThat(clientOptional).isNotEmpty();
        assertThat(clientOptional.get().getEmail()).isEqualTo(email);
        assertThat(clientOptional.get().getName()).isEqualTo(clientSaved.getName());
    }

    @Test
    @DisplayName("Find by id and active client when successful")
    void findById_ReturnsActiveClient_WhenSuccessful(){
        Client clientTobeSaved = ClientCreator.createClintToBeSaved();
        Client clientSaved = this.clientRepository.save(clientTobeSaved);
        Optional<Client> clientOptional = this.clientRepository.findByIdAndActiveClient(clientSaved.getId(), ACTIVE);

        assertThat(clientOptional).isNotEmpty();
        assertThat(clientOptional.get().getId()).isEqualTo(clientSaved.getId());
        assertThat(clientOptional.get().getName()).isEqualTo(clientSaved.getName());
    }

    @Test
    @DisplayName("Find by email return client empty when no client is found")
    void findByEmail_ReturnsClientEmpty_WhenNoClientIsFound(){
        Optional<Client> clientOptional = this.clientRepository.findByEmail("not_user");
        assertThat(clientOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by email and active client return client empty when no client is found")
    void findByEmailActiveClient_ReturnsClientEmpty_WhenNoClientIsFound(){
        Optional<Client> client = this.clientRepository.findByEmailAndActiveClient("not_user", ACTIVE);
        assertThat(client).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when clientDTO is empty")
    void save_ThrowsConstraintViolationException_WhenClientIsEmpty() {
        Client client = new Client();
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.clientRepository.save(client))
                .withMessageContaining("Nome Invalido!")
                .withMessageContaining("Senha Invalida!")
                .withMessageContaining("Perfil obrigatorio!");
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when email is invalid")
    void save_ThrowsConstraintViolationException_WhenEmailIsInvalid() {
        Client client = ClientCreator.createClintToBeSaved();
        client.setEmail("email.com");
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.clientRepository.save(client))
                .withMessageContaining("Email precisa ser valido no seguinte formato: email@exemplo.com");
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name less than three characters")
    void save_ThrowsConstraintViolationException_WhenNamelessThanThreeCharacters() {
        Client client = ClientCreator.createClintToBeSaved();
        client.setName("al");
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.clientRepository.save(client))
                .withMessageContaining("Nome precisa ter no minimo 3 caracteres");
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when telephone is invalid")
    void save_ThrowsConstraintViolationException_WhenTelephoneIsInvalid() {
        Client client = ClientCreator.createClintToBeSaved();
        client.setTelephone("818181");
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.clientRepository.save(client))
                .withMessageContaining("Número invalido, precisar ter no minimo 10 digitos com o seguinte formato: (xx) xxxxx-xxxx");
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when password with less than eight characters")
    void save_ThrowsConstraintViolationException_WhenPasswordWithLessThanEightCharacters() {
        Client client = ClientCreator.createClintToBeSaved();
        client.setPassword("1234567");
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.clientRepository.save(client))
                .withMessageContaining("A Senha precisa ter no minimo 8 caracteres");
    }

}