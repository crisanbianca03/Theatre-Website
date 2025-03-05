package com.example.teatru.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idSpectacol")
@Entity
@Table
public class Spectacol {
    @Id
    @SequenceGenerator(
            name = "spectacol_sequence",
            sequenceName = "spectacol_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "spectacol_sequence"
    )
    private int idSpectacol;

    private String titlu;
    private LocalDateTime dataSpectacol;
    private String ora;
    private String descriere;
    private String gen;
    private int nrBilete;
    private String durata;
    private String imagine;

    @ManyToOne
    @JoinColumn(name = "idSala", referencedColumnName = "idSala", nullable = false)
    @JsonBackReference("sala-spectacol")
    private Sala sala;

    @OneToMany(mappedBy = "spectacol", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Rezervare> rezervari = new ArrayList<>();

    @OneToMany(mappedBy = "spectacol", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonBackReference("spectacol-actor")
    private List<SpectacolActor> spectacolActors = new ArrayList<>();

    public List<SpectacolActor> getSpectacolActors() {
        return spectacolActors;
    }

    public void setSpectacolActors(List<SpectacolActor> spectacolActors) {
        this.spectacolActors = spectacolActors;
    }

    public Spectacol() {
    }

    public Spectacol(String titlu, LocalDateTime dataSpectacol, String descriere, String gen, Sala sala, int nrBilete, String durata,String imagine) {
        this.titlu = titlu;
        this.dataSpectacol = dataSpectacol;
        this.descriere = descriere;
        this.gen = gen;
        this.sala = sala;
        this.nrBilete = nrBilete;
        this.durata = durata;
        this.imagine=imagine;
    }
    public String getImagine() {
        return imagine;
    }

    public void setImagine(String imagine) {
        this.imagine = imagine;
    }

    public Spectacol(String titlu, LocalDateTime dataSpectacol, String ora, String descriere, String gen, int nrBilete, String durata, Sala sala,String imagine) {
        this.titlu = titlu;
        this.dataSpectacol = dataSpectacol;
        this.ora = ora;
        this.descriere = descriere;
        this.gen = gen;
        this.nrBilete = nrBilete;
        this.durata = durata;
        this.sala = sala;
        this.imagine=imagine;
    }

    public int getIdSpectacol() {
        return idSpectacol;
    }

    public void setIdSpectacol(int idSpectacol) {
        this.idSpectacol = idSpectacol;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public LocalDateTime getDataSpectacol() {
        return dataSpectacol;
    }

    public void setDataSpectacol(LocalDateTime dataSpectacol) {
        this.dataSpectacol = dataSpectacol;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public int getNrBilete() {
        return nrBilete;
    }

    public void setNrBilete(int nrBilete) {
        this.nrBilete = nrBilete;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    public void setRezervari(List<Rezervare> rezervari) {
        this.rezervari = rezervari;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "idSpectacol=" + idSpectacol +
                ", titlu='" + titlu + '\'' +
                ", dataSpectacol=" + dataSpectacol +
                ", ora='" + ora + '\'' +
                ", descriere='" + descriere + '\'' +
                ", gen='" + gen + '\'' +
                ", nrBilete=" + nrBilete +
                ", durata='" + durata + '\'' +
                ", imagine='" + imagine + '\'' +
                ", sala=" + (sala != null ? sala.getIdSala() : null) +
                '}';
    }
}
