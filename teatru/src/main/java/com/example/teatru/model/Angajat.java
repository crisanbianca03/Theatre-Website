package com.example.teatru.model;

import jakarta.persistence.*;

@Entity
@Table
public class Angajat {
    @Id
    @SequenceGenerator(
            name = "angajat_sequence",
            sequenceName = "angajat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "angajat_sequence"
    )
    private int idAngajat;

    private String nume;
    private String prenume;




    public Angajat() {
    }

    public Angajat(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }

    public int getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(int idAngajat) {
        this.idAngajat = idAngajat;
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



    @Override
    public String toString() {
        return "Angajat{" +
                "idAngajat=" + idAngajat +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }
}
