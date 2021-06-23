package com.example.demo.repository;

import com.example.demo.domain.Profile;
import com.example.demo.util.ProfileCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@DisplayName("Tests for profile Repository")
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    @DisplayName("Save persists profile when successful")
    void save_PersistProfile_WhenSuccessful(){
        Profile profile = ProfileCreator.createProfileToBeSaved();
        Profile profileSaved = this.profileRepository.save(profile);

        assertThat(profileSaved).isNotNull();
        assertThat(profileSaved.getId()).isNotNull();
        assertThat(profileSaved.getName()).isEqualTo(profile.getName());
    }

    @Test
    @DisplayName("Update persists profile when successful")
    void updates_PersistProfile_WhenSuccessful(){
        Profile profile = ProfileCreator.createProfileToBeSaved();
        Profile profileSaved = this.profileRepository.save(profile);

        profileSaved.setName("ROLE_ADMIN");

        Profile profileUpdated = this.profileRepository.save(profileSaved);

        assertThat(profileUpdated).isNotNull();
        assertThat(profileUpdated.getId()).isNotNull();
        assertThat(profileUpdated.getName()).isEqualTo(profileSaved.getName());
    }

    @Test
    @DisplayName("Find by id profile when successful")
    void findById_ReturnsProfile_WhenSuccessful(){
        Profile profile = ProfileCreator.createProfileValid();
        Profile profileSaved = this.profileRepository.save(profile);
        Optional<Profile> profileOptional = this.profileRepository.findById(profileSaved.getId());

        assertThat(profileOptional).isNotEmpty();
        assertThat(profileOptional.get().getId()).isEqualTo(profileSaved.getId());
        assertThat(profileOptional.get().getName()).isEqualTo(profileSaved.getName());
    }

    @Test
    @DisplayName("Delete profile when successful")
    void deleteProfile_WhenSuccessful(){
        Profile profile = ProfileCreator.createProfileValid();
        Profile profileSaved = this.profileRepository.save(profile);
        this.profileRepository.delete(profileSaved);
        Optional<Profile> profileOptional = this.profileRepository.findById(profileSaved.getId());

        assertThat(profileOptional).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is invalid")
    void save_ThrowsConstraintViolationException_WhenNameIsInvalid() {
        Profile profile = ProfileCreator.createProfileValid();
        profile.setName(null);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.profileRepository.save(profile))
                .withMessageContaining("Nome Obrigatorio!");
    }
}