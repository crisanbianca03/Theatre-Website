package com.example.teatru.repository;

import com.example.teatru.model.Rezervare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezervareRepository extends JpaRepository<Rezervare, Integer> {
    List<Rezervare> findByUtilizatorIdUtilizator(int idUtilizator);
}
