package com.example.teatru.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table
public class Loc {
    @Id
    @SequenceGenerator(
            name = "loc_sequence",
            sequenceName = "loc_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loc_sequence"
    )
    private int idLoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id", nullable = false)
    @JsonBackReference("sala-loc")
    private Sala sala;

    private String status;
    private int rand;

    public Loc() {
    }

    public Loc(Sala sala, String status, int rand) {
        this.sala = sala;
        this.status = status;
        this.rand = rand;
    }

    public int getIdLoc() {
        return idLoc;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRand() {
        return rand;
    }

    public void setRand(int rand) {
        this.rand = rand;
    }

    @Override
    public String toString() {
        return "Loc{" +
                "idLoc=" + idLoc +
                ", salaId=" + sala.getIdSala() +
                ", status='" + status + '\'' +
                ", rand=" + rand +
                '}';
    }
}
