package com.example.teatru.repository;

import com.example.teatru.model.Spectacol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpectacolRepository extends JpaRepository<Spectacol, Integer> {
    @Query("SELECT s FROM Spectacol s JOIN FETCH s.sala")
    List<Spectacol> findAllWithSala();
}
