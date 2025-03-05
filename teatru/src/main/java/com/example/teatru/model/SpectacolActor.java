package com.example.teatru.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table
public class SpectacolActor {
    @Id
    @SequenceGenerator(
            name = "spectacolactor_sequence",
            sequenceName = "spectacolactor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "spectacolactor_sequence"
    )
    private int idSpectacolActor;

    @ManyToOne
    @JoinColumn(name = "idSpectacol", referencedColumnName = "idSpectacol", nullable = false)
    @JsonBackReference("spectacol-actor")
    private Spectacol spectacol;

    @ManyToOne
    @JoinColumn(name = "idActor", referencedColumnName = "idActor", nullable = false)
    @JsonBackReference
    private Actor actor;

    public SpectacolActor() {
    }

    public SpectacolActor(Spectacol spectacol, Actor actor) {
        this.spectacol = spectacol;
        this.actor = actor;
    }

    public int getIdSpectacolActor() {
        return idSpectacolActor;
    }

    public void setIdSpectacolActor(int idSpectacolActor) {
        this.idSpectacolActor = idSpectacolActor;
    }

    public Spectacol getSpectacol() {
        return spectacol;
    }

    public void setSpectacol(Spectacol spectacol) {
        this.spectacol = spectacol;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "SpectacolActor{" +
                "idSpectacolActor=" + idSpectacolActor +
                ", spectacol=" + (spectacol != null ? spectacol.getTitlu() : "null") +
                ", actor=" + (actor != null ? actor.getNume() + " " + actor.getPrenume() : "null") +
                '}';
    }
}
