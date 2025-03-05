package com.example.teatru.repository;

import com.example.teatru.model.Loc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocRepository extends JpaRepository<Loc, Integer> {
    int countBySala_IdSala(int idSala);
}
