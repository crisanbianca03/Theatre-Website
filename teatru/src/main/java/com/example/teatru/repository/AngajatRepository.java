package com.example.teatru.repository;

import com.example.teatru.model.Angajat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AngajatRepository extends JpaRepository<Angajat,Integer> {
}
