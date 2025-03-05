package com.example.teatru.repository;

import com.example.teatru.model.SpectacolActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectacolActorRepository extends JpaRepository<SpectacolActor, Integer> {
}
