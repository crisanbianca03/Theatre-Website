package com.example.teatru.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table
public class Rezervare {
    @Id
    @SequenceGenerator(
            name = "rezervare_sequence",
            sequenceName = "rezervare_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rezervare_sequence"
    )
    private int idRezervare;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUtilizator", referencedColumnName = "idUtilizator", nullable = false)
    private Utilizator utilizator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSpectacol", referencedColumnName = "idSpectacol", nullable = false)
    private Spectacol spectacol;


    private int nrBilete;
    private LocalDateTime dataRezervarii;

    public Rezervare() {
    }

    public Rezervare(Utilizator utilizator, Spectacol spectacol, int nrBilete, LocalDateTime dataRezervarii) {
        this.utilizator = utilizator;
        this.spectacol = spectacol;
        this.nrBilete = nrBilete;
        this.dataRezervarii = dataRezervarii;
    }

    public int getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(int idRezervare) {
        this.idRezervare = idRezervare;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public Spectacol getSpectacol() {
        return spectacol;
    }

    public void setSpectacol(Spectacol spectacol) {
        this.spectacol = spectacol;
    }

    public int getNrBilete() {
        return nrBilete;
    }

    public void setNrBilete(int nrBilete) {
        this.nrBilete = nrBilete;
    }

    public LocalDateTime getDataRezervarii() {
        return dataRezervarii;
    }

    public void setDataRezervarii(LocalDateTime dataRezervarii) {
        this.dataRezervarii = dataRezervarii;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "idRezervare=" + idRezervare +
                ", utilizator=" + utilizator +
                ", spectacol=" + spectacol +
                ", nrBilete=" + nrBilete +
                ", dataRezervarii=" + dataRezervarii +
                '}';
    }
}
