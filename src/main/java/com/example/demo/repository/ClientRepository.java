package com.example.demo.repository;

import com.example.demo.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    @Query("SELECT u FROM Client u WHERE u.id = ?1 AND u.status = ?2")
    Optional<Client> findByIdAndActiveClient(long id, Long status);

    @Query("SELECT u FROM Client u WHERE u.email = ?1 AND u.status = ?2")
    Optional<Client> findByEmailAndActiveClient(String email, Long status);

}
