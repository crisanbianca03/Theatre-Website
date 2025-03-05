package com.example.teatru.repository;

import com.example.teatru.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Integer> {
    Optional<Actor> findByNumeAndPrenume(String nume, String prenume);
}
