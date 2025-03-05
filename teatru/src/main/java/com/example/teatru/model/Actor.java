package com.example.teatru.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Actor {
    @Id
    @SequenceGenerator(
            name="actor_sequence",
            sequenceName="actor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator="actor_sequence"
    )
    private int idActor;
    private String nume;
    private String prenume;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SpectacolActor> spectacolActors = new ArrayList<>();

    public Actor() {
    }

    public Actor(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }

    // Getters și setters

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public List<SpectacolActor> getSpectacolActors() {
        return spectacolActors;
    }

    public void setSpectacolActors(List<SpectacolActor> spectacolActors) {
        this.spectacolActors = spectacolActors;
    }
}
