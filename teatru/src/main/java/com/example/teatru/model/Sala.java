package com.example.teatru.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Sala {
    @Id
    @SequenceGenerator(
            name = "sala_sequence",
            sequenceName = "sala_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sala_sequence"
    )
    private int idSala;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("sala-loc")
    private List<Loc> locuri = new ArrayList<>();

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("sala-spectacol")
    private List<Spectacol> spectacole = new ArrayList<>();

    public Sala() {
    }

    public Sala(List<Loc> locuri) {
        this.locuri = locuri;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public List<Loc> getLocuri() {
        return locuri;
    }

    public void setLocuri(List<Loc> locuri) {
        this.locuri = locuri;
    }

    public List<Spectacol> getSpectacole() {
        return spectacole;
    }

    public void setSpectacole(List<Spectacol> spectacole) {
        this.spectacole = spectacole;
    }

    @JsonProperty("nrLocuri")
    public int getNrLocuri() {
        return locuri.size();
    }

    @Override
    public String toString() {
        return "Sala{" +
                "idSala=" + idSala +
                ", nrLocuri=" + getNrLocuri() +
                ", spectacole=" + spectacole +
                '}';
    }
}
